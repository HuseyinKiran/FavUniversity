package com.huseyinkiran.favuniversities.presentation.favorites

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.huseyinkiran.favuniversities.presentation.adapter.UniversityAdapter
import com.huseyinkiran.favuniversities.databinding.FragmentFavoritesBinding
import com.huseyinkiran.favuniversities.domain.model.toUI
import com.huseyinkiran.favuniversities.domain.model.toUniversity
import com.huseyinkiran.favuniversities.utils.CallPermissionDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private lateinit var adapter: UniversityAdapter
    private val viewModel: FavoritesViewModel by viewModels()
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exitOnBackPressed()
        setupAdapter()
        observeViewModel()
    }

    private fun setupAdapter() {

        adapter = UniversityAdapter(
            fragmentType = "Favorites",
            onFavoriteClick = {
                viewModel.toggleFavorite(it.toUniversity())
            },
            onWebsiteClick = { websiteUrl, uniName ->
                val action = FavoritesFragmentDirections
                    .actionFavoritesFragmentToWebsiteFragment(websiteUrl, uniName)
                findNavController().navigate(action)
            },
            onPhoneClick = {
                viewModel.requestCall(it)
            }
        )

        binding.favoritesRv.adapter = adapter

    }

    private fun observeViewModel() {

        viewModel.favoriteUniversities.observe(viewLifecycleOwner) { favorites ->
            adapter.updateUniversities(favorites.map { it.toUI() }.map {  university ->
                university.copy(isFavorite = true)
            })
        }

        viewModel.callPhoneEvent.observe(viewLifecycleOwner) { phoneNumber ->
            phoneNumber?.let {

                when {

                    ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.CALL_PHONE
                    ) == PackageManager.PERMISSION_GRANTED -> {
                        callPhoneNumber(it)
                        viewModel.clearCallEvent()
                    }

                    ActivityCompat.shouldShowRequestPermissionRationale(
                        requireActivity(), Manifest.permission.CALL_PHONE) -> {
                            viewModel.increaseDeniedCount()
                        if (viewModel.shouldRedirectToSettings()) {
                            CallPermissionDialog.showGoToSettingsDialog(requireContext())
                            viewModel.clearCallEvent()
                        } else {
                            CallPermissionDialog.showPermissionRationaleDialog(requireContext()) {
                                requestCallPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
                            }
                        }
                    }

                    else -> {
                        CallPermissionDialog.showGoToSettingsDialog(requireContext())
                        viewModel.clearCallEvent()
                    }

                }
            }
        }

    }

    private val requestCallPermissionLauncher =
        registerForActivityResult(RequestPermission()) { isGranted ->
            if (isGranted) {
                viewModel.callPhoneEvent.value?.let { callPhoneNumber(it) }
            }
        }

    private fun callPhoneNumber(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phoneNumber")
        startActivity(intent)
    }

    private fun exitOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }
    }

}
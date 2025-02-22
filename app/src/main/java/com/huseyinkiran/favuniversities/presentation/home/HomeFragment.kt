package com.huseyinkiran.favuniversities.presentation.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.huseyinkiran.favuniversities.adapter.ProvinceAdapter
import com.huseyinkiran.favuniversities.databinding.FragmentHomeBinding
import com.huseyinkiran.favuniversities.model.toUI
import com.huseyinkiran.favuniversities.model.toUniversity
import com.huseyinkiran.favuniversities.util.CallPermissionDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var provinceAdapter: ProvinceAdapter
    private val viewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exitOnBackPressed()
        setupAdapter()
        observeViewModel()
    }

    private fun setupAdapter() {

        provinceAdapter = ProvinceAdapter(
            onFavoriteClick = {
                viewModel.toggleFavorite(it.toUniversity())
            },
            onWebsiteClick = { websiteUrl, uniName ->
                val action =
                    HomeFragmentDirections.actionHomeFragmentToWebsiteFragment(websiteUrl, uniName)
                findNavController().navigate(action)
            },
            onPhoneClick = {
                viewModel.requestCall(it)
            }
        )

        binding.provinceRv.adapter = provinceAdapter

        binding.provinceRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.loadProvinces()
                }
            }
        })

    }

    private fun observeViewModel() {

        viewModel.provinceList.observe(viewLifecycleOwner) { provinces ->
            binding.progressBar.isGone = true
            if (provinces.isNullOrEmpty()) {
                binding.txtError.isVisible = true
            } else {
                binding.progressBar.isGone = true
                provinceAdapter.updateProvinces(provinces.map { it.toUI() })
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { isError ->
            if (isError) {
                binding.txtError.isVisible = true
                binding.progressBar.isGone = true
            } else {
                binding.txtError.isGone = true
            }
        }

        viewModel.favoriteUniversities.observe(viewLifecycleOwner) { favorites ->
            provinceAdapter.updateFavoriteUniversities(favorites.map { it.toUI() })
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
                        requireActivity(), Manifest.permission.CALL_PHONE
                    ) -> {
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

        binding.progressBar.isVisible = true
        viewModel.loadProvinces()

    }

    private val requestCallPermissionLauncher =
        registerForActivityResult(RequestPermission()) { isGranted ->
            if (isGranted) {
                viewModel.callPhoneEvent.value?.let {
                    callPhoneNumber(it)
                    viewModel.clearCallEvent()
                }
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
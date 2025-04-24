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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huseyinkiran.favuniversities.presentation.adapter.city.CityAdapter
import com.huseyinkiran.favuniversities.databinding.FragmentHomeBinding
import com.huseyinkiran.favuniversities.domain.model.UniversityUIModel
import com.huseyinkiran.favuniversities.presentation.adapter.AdapterFragmentType
import com.huseyinkiran.favuniversities.presentation.adapter.university.UniversityAdapter
import com.huseyinkiran.favuniversities.utils.CallPermissionDialog
import com.huseyinkiran.favuniversities.common.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var adapter: CityAdapter
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

    private fun setupAdapter() = with(binding) {

        adapter = CityAdapter(fragmentType = AdapterFragmentType.HOME,
            callbacks = object : UniversityAdapter.UniversityClickListener {
                override fun onFavoriteClick(university: UniversityUIModel) {
                    viewModel.toggleFavorite(university)
                }

                override fun onWebsiteClick(websiteUrl: String, universityName: String) {
                    val action = HomeFragmentDirections.actionHomeFragmentToWebsiteFragment(
                        websiteUrl,
                        universityName
                    )
                    findNavController().navigate(action)
                }

                override fun onPhoneClick(phoneNumber: String) {
                    viewModel.requestCall(phoneNumber)
                }

            })

        rvCity.adapter = adapter
        rvCity.itemAnimator = null

    }

    private fun observeViewModel() = with(binding) {

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.cityList.combine(viewModel.favorites) { resource, favorites ->
                    when (resource) {

                        is Resource.Loading -> resource

                        is Resource.Success -> {
                            val updatedCities = resource.data?.map { city ->
                                city.copy(
                                    universities = city.universities.map { university ->
                                        university.copy(isFavorite = favorites.any { it.name == university.name })
                                    }
                                )
                            } ?: emptyList()
                            Resource.Success(updatedCities)
                        }

                        is Resource.Error -> Resource.Error(
                            resource.message ?: "Hata",
                            resource.data
                        )
                    }
                }.collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            progressBar.isVisible = true
                            txtError.isGone = true
                        }

                        is Resource.Success -> {
                            progressBar.isGone = true
                            txtError.isGone = true
                            rvCity.isVisible = true
                            result.data?.let { adapter.submitList(it) }
                        }

                        is Resource.Error -> {
                            progressBar.isGone = true
                            txtError.isVisible = true
                            rvCity.isGone = true
                        }
                    }
                }
            }
        }

        rvCity.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                val isAtBottom = lastVisibleItemPosition == totalItemCount - 1

                if (isAtBottom) {
                    viewModel.loadCities()
                }
            }
        })

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
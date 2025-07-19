package com.huseyinkiran.favuniversities.presentation.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.map
import com.huseyinkiran.favuniversities.R
import com.huseyinkiran.favuniversities.databinding.FragmentHomeBinding
import com.huseyinkiran.favuniversities.domain.model.UniversityUIModel
import com.huseyinkiran.favuniversities.presentation.adapter.AdapterFragmentType
import com.huseyinkiran.favuniversities.presentation.adapter.city.CityPagingAdapter
import com.huseyinkiran.favuniversities.presentation.adapter.university.UniversityAdapter
import com.huseyinkiran.favuniversities.utils.CallPermissionDialog
import com.huseyinkiran.favuniversities.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()
    private  val binding by viewBinding(FragmentHomeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exitOnBackPressed()
        setupAdapter()
        observeViewModel()
        viewModel.getCities()
    }

    private val adapter: CityPagingAdapter by lazy {
        CityPagingAdapter(
            fragmentType = AdapterFragmentType.HOME,
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

            }
        )
    }

    private fun setupAdapter() = with(binding) {
        rvCity.adapter = adapter
        rvCity.itemAnimator = null
    }

    private fun observeViewModel() = with(binding) {

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.cityPagingFlow.combine(viewModel.favorites) { pagingData, favorites ->
                    pagingData.map { city ->
                        city.copy(
                            universities = city.universities.map { university ->
                                university.copy(
                                    isFavorite = favorites.any { it.name == university.name }
                                )
                            }
                        )
                    }
                }.collectLatest { transformedData ->
                    adapter.submitData(transformedData)
                }
            }
        }

        adapter.addLoadStateListener { loadState ->
            progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            txtError.isVisible = loadState.source.refresh is LoadState.Error
            rvCity.isVisible = loadState.source.refresh is LoadState.NotLoading
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

}

private val requestCallPermissionLauncher =
    registerForActivityResult(RequestPermission()) { isGranted ->
        if (isGranted) {
            viewModel.callPhoneEvent.value?.let { callPhoneNumber(it) }
        }
    }

private fun callPhoneNumber(phoneNumber: String) {
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = "tel:$phoneNumber".toUri()
    startActivity(intent)
}

private fun exitOnBackPressed() {
    requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
        requireActivity().finish()
    }
}

}
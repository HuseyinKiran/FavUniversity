package com.huseyinkiran.favuniversities.presentation.home

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.huseyinkiran.favuniversities.R
import com.huseyinkiran.favuniversities.core.ui.callPhoneNumber
import com.huseyinkiran.favuniversities.core.ui.viewBinding
import com.huseyinkiran.favuniversities.databinding.FragmentHomeBinding
import com.huseyinkiran.favuniversities.presentation.adapter.AdapterFragmentType
import com.huseyinkiran.favuniversities.presentation.adapter.city.CityPagingAdapter
import com.huseyinkiran.favuniversities.presentation.adapter.university.UniversityAdapter
import com.huseyinkiran.favuniversities.presentation.model.UniversityUIModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()
    private val binding by viewBinding(FragmentHomeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exitOnBackPressed()
        setupAdapter()
        observeViewModel()
    }

    private val adapter: CityPagingAdapter by lazy {
        CityPagingAdapter(
            fragmentType = AdapterFragmentType.HOME,
            universityCallbacks = object : UniversityAdapter.UniversityClickListener {
                override fun onFavoriteClick(university: UniversityUIModel) {
                    viewModel.toggleFavorite(university)
                }

                override fun onWebsiteClick(websiteUrl: String, universityName: String) {
                    val action = HomeFragmentDirections
                        .actionHomeFragmentToWebsiteFragment(websiteUrl, universityName)
                    findNavController().navigate(action)
                }

                override fun onPhoneClick(phoneNumber: String) {
                    callPhoneNumber(phoneNumber)
                }

                override fun onUniversityExpanded(universityName: String) {
                    viewModel.onUniversityExpanded(universityName)
                }
            },
            cityCallback = object : CityPagingAdapter.CityClickListener {
                override fun onCityExpanded(cityName: String) {
                    viewModel.onCityExpanded(cityName)
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
                viewModel.uiPagingFlow.collectLatest(adapter::submitData)
            }
        }

        adapter.addLoadStateListener { loadState ->
            progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            txtError.isVisible = loadState.source.refresh is LoadState.Error
            rvCity.isVisible = loadState.source.refresh is LoadState.NotLoading
        }
    }

    private fun exitOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }
    }
}
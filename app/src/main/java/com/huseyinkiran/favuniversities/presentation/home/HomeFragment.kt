package com.huseyinkiran.favuniversities.presentation.home

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.huseyinkiran.favuniversities.R
import com.huseyinkiran.favuniversities.core.ui.callPhoneNumber
import com.huseyinkiran.favuniversities.core.ui.dpToPx
import com.huseyinkiran.favuniversities.core.ui.exitOnBackPressed
import com.huseyinkiran.favuniversities.core.ui.openAddressLocation
import com.huseyinkiran.favuniversities.core.ui.openEmail
import com.huseyinkiran.favuniversities.core.ui.viewBinding
import com.huseyinkiran.favuniversities.databinding.FragmentHomeBinding
import com.huseyinkiran.favuniversities.presentation.adapter.AdapterFragmentType
import com.huseyinkiran.favuniversities.presentation.adapter.home.HomeAdapter
import com.huseyinkiran.favuniversities.presentation.adapter.home.HomeItem
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
        exitOnBackPressed(requireActivity(), viewLifecycleOwner)
        setupAdapter()
        observeViewModel()
    }

    private val adapter: HomeAdapter by lazy {
        HomeAdapter(
            fragmentType = AdapterFragmentType.HOME,
            universityCallbacks = object : UniversityAdapter.UniversityClickListener {
                override fun onFavoriteClick(university: UniversityUIModel) {
                    viewModel.toggleFavorite(university = university)
                }

                override fun onWebsiteClick(websiteUrl: String, universityName: String) {
                    val action = HomeFragmentDirections
                        .actionHomeFragmentToWebsiteFragment(websiteUrl, universityName)
                    findNavController().navigate(action)
                }

                override fun onPhoneClick(phoneNumber: String) {
                    callPhoneNumber(phoneNumber = phoneNumber)
                }

                override fun onEmailClick(email: String) {
                    openEmail(email = email)
                }

                override fun onAddressClick(address: String) {
                    openAddressLocation(address = address, view = binding.root)
                }

                override fun onUniversityExpanded(universityName: String) {
                    viewModel.onUniversityExpanded(universityName = universityName)
                }
            },
            cityClickListener = object : HomeAdapter.CityClickListener {
                override fun onCityExpanded(cityName: String) {
                    viewModel.onCityExpanded(cityName)
                }
            }
        )
    }


    private fun setupAdapter() = with(binding) {
        rvCity.adapter = adapter
        rvCity.itemAnimator = null

        rvCity.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                val position = parent.getChildAdapterPosition(view)
                val adapter = parent.adapter as? HomeAdapter ?: return

                val item = adapter.peek(position)
                if (item is HomeItem.UniversityRow) {
                    outRect.right = 8.dpToPx(parent.context)
                    outRect.left = 8.dpToPx(parent.context)
                }
            }
        })

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

}
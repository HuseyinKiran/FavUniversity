package com.huseyinkiran.favuniversities.presentation.search

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.huseyinkiran.favuniversities.R
import com.huseyinkiran.favuniversities.core.ui.callPhoneNumber
import com.huseyinkiran.favuniversities.core.ui.exitOnBackPressed
import com.huseyinkiran.favuniversities.core.ui.hideKeyboard
import com.huseyinkiran.favuniversities.core.ui.openAddressLocation
import com.huseyinkiran.favuniversities.core.ui.openEmail
import com.huseyinkiran.favuniversities.core.ui.viewBinding
import com.huseyinkiran.favuniversities.databinding.FragmentSearchBinding
import com.huseyinkiran.favuniversities.presentation.adapter.AdapterFragmentType
import com.huseyinkiran.favuniversities.presentation.adapter.university.UniversityAdapter
import com.huseyinkiran.favuniversities.presentation.model.UniversityUIModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val viewModel: SearchViewModel by viewModels()
    private val binding by viewBinding(FragmentSearchBinding::bind)
    private lateinit var adapter: UniversityAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exitOnBackPressed(requireActivity(), viewLifecycleOwner)
        setupAdapter()
        setupSearchListener()
        setupKeyboardDismissOnScroll()
        observeResults()
    }

    private fun setupKeyboardDismissOnScroll() = with(binding) {
        rvSearchResults.setOnTouchListener { _, _ ->
            if (etSearch.isFocused) {
                etSearch.clearFocus()
                root.hideKeyboard()
            }
            false
        }
    }

    private fun setupAdapter() = with(binding) {
        adapter = UniversityAdapter().apply {
            fragmentType = AdapterFragmentType.SEARCH

            callbacks = object : UniversityAdapter.UniversityClickListener {
                override fun onFavoriteClick(university: UniversityUIModel) {
                    viewModel.toggleFavorite(university = university)
                }

                override fun onWebsiteClick(
                    websiteUrl: String,
                    universityName: String
                ) {
                    val action = SearchFragmentDirections.actionSearchFragmentToWebsiteFragment(
                        websiteUrl,
                        universityName
                    )
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
                    viewModel.onUniversityExpanded(universityName)
                }
            }
        }
        rvSearchResults.adapter = adapter
        rvSearchResults.itemAnimator = null
    }

    private fun setupSearchListener() = with(binding) {
        etSearch.addTextChangedListener {
            viewModel.searchUniversities(it.toString())
        }
    }

    private fun observeResults() = with(binding) {

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    progressBar.isVisible = state.isLoading
                    txtError.isVisible = state.errorMessage != null
                    rvSearchResults.isVisible = state.results.isNotEmpty()

                    if (txtError.isVisible) {
                        tilSearch.isEnabled = false
                    }

                    txtError.text = state.errorMessage
                    adapter.submitList(state.results)
                }
            }
        }

    }

}
package com.huseyinkiran.favuniversities.presentation.favorites

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.huseyinkiran.favuniversities.R
import com.huseyinkiran.favuniversities.databinding.FragmentFavoritesBinding
import com.huseyinkiran.favuniversities.presentation.adapter.AdapterFragmentType
import com.huseyinkiran.favuniversities.presentation.adapter.university.UniversityAdapter
import com.huseyinkiran.favuniversities.presentation.model.UniversityUIModel
import com.huseyinkiran.favuniversities.core.ui.callPhoneNumber
import com.huseyinkiran.favuniversities.core.ui.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private val viewModel: FavoritesViewModel by viewModels()
    private val binding by viewBinding(FragmentFavoritesBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exitOnBackPressed()
        setupAdapter()
        observeViewModel()
    }

    private val universityAdapterCallbacks = object : UniversityAdapter.UniversityClickListener {
        override fun onFavoriteClick(university: UniversityUIModel) {
            viewModel.toggleFavorite(university)
        }

        override fun onWebsiteClick(websiteUrl: String, universityName: String) {
            val action = FavoritesFragmentDirections
                .actionFavoritesFragmentToWebsiteFragment(websiteUrl, universityName)
            findNavController().navigate(action)
        }

        override fun onPhoneClick(phoneNumber: String) {
            callPhoneNumber(phoneNumber)
        }

        override fun onUniversityExpanded(universityName: String) {
            viewModel.onFavUniversityExpanded(universityName)
        }
    }

    private val adapter: UniversityAdapter by lazy {
        UniversityAdapter()
    }

    private fun setupAdapter() = with(binding) {
        adapter.fragmentType = AdapterFragmentType.FAVORITES
        adapter.callbacks = universityAdapterCallbacks

        rvFavorites.adapter = adapter
        rvFavorites.itemAnimator = null
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favorites.collect { favorites ->
                    adapter.submitList(favorites)
                }
            }
        }
    }

    private fun exitOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }
    }
}
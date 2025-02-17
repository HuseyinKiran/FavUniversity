package com.huseyinkiran.favuniversities.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.huseyinkiran.favuniversities.adapter.UniversityAdapter
import com.huseyinkiran.favuniversities.databinding.FragmentFavoritesBinding
import com.huseyinkiran.favuniversities.viewmodel.UniversityViewModel


class FavoritesFragment : Fragment() {

    private lateinit var universityAdapter: UniversityAdapter
    private lateinit var favoriteViewModel: UniversityViewModel
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

        favoriteViewModel = ViewModelProvider(requireActivity())[UniversityViewModel::class.java]

        binding.goToMain.setOnClickListener {
            findNavController().popBackStack()
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            })

        universityAdapter = UniversityAdapter(
            onFavoriteClick = {
                favoriteViewModel.toggleFavorite(it)
            },
            onWebsiteClick = { websiteUrl, uniName ->
                val action = FavoritesFragmentDirections
                    .actionFavoritesFragmentToWebsiteFragment(websiteUrl, uniName)
                findNavController().navigate(action)
            },
            onUniversityExpand = {
                favoriteViewModel.checkUniversityExpansion(it)
            },
            isUniversityExpandable = favoriteViewModel::isUniversityExpandable
        )

        binding.favoritesRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = universityAdapter
        }

        favoriteViewModel.favoriteUniversities.observe(viewLifecycleOwner) { favorites ->
            universityAdapter.updateUniversities(favorites)
            universityAdapter.updateFavoriteUniversities(favorites.map { it.name })
        }

        favoriteViewModel.expandedUniversities.observe(viewLifecycleOwner) { expandedUniversities ->
            universityAdapter.updateExpandedUniversities(expandedUniversities.map { it })
        }

    }

}
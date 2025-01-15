package com.huseyinkiran.favuniversities.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.huseyinkiran.favuniversities.R
import com.huseyinkiran.favuniversities.adapter.UniversityAdapter
import com.huseyinkiran.favuniversities.databinding.FragmentFavoritesBinding
import com.huseyinkiran.favuniversities.viewmodel.FavoriteViewModel


class FavoritesFragment : Fragment() {

    private lateinit var universityAdapter: UniversityAdapter
    private val favoriteViewModel: FavoriteViewModel by hiltNavGraphViewModels(R.id.nav_graph)
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.goToMain.setOnClickListener {
            findNavController().popBackStack()
        }

        universityAdapter = UniversityAdapter(onFavoriteClick = {
            favoriteViewModel.updateFavorites(it)
            Log.d("FavoritesFragment", "${it.name} was removed from favorites")
        }, onWebsiteClick = { websiteUrl, uniName ->
            val action =
                FavoritesFragmentDirections.actionFavoritesFragmentToWebsiteFragment(websiteUrl, uniName)
            findNavController().navigate(action)
        })

        binding.favoritesRv.layoutManager = LinearLayoutManager(requireContext())
        binding.favoritesRv.adapter = universityAdapter

        favoriteViewModel.favoriteUniversities.observe(viewLifecycleOwner) { universityList ->
            universityAdapter.updateUniversities(universityList)
            Log.d("FavoritesFragment", "${universityList.size} ")
        }

    }

}
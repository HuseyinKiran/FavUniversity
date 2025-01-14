package com.huseyinkiran.favuniversities.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.huseyinkiran.favuniversities.R
import com.huseyinkiran.favuniversities.adapter.UniversityAdapter
import com.huseyinkiran.favuniversities.databinding.FragmentFavoritesBinding
import com.huseyinkiran.favuniversities.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoritesFragment @Inject constructor() : Fragment() {

    @Inject
    lateinit var universityAdapter: UniversityAdapter

    private val favoriteViewModel: FavoriteViewModel by hiltNavGraphViewModels(R.id.nav_graph)
    private var _binding : FragmentFavoritesBinding? = null
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

        universityAdapter = UniversityAdapter {
            favoriteViewModel.updateFavorites(it)
            Log.d("FavoritesFragment", "${it.name} was removed from favorites")
        }
        binding.favoritesRv.layoutManager = LinearLayoutManager(requireContext())
        binding.favoritesRv.adapter = universityAdapter

        favoriteViewModel.favoriteUniversities.observe(viewLifecycleOwner, Observer { universityList ->
            universityAdapter.updateUniversities(universityList)
            Log.d("FavoritesFragment", "${universityList.size} ")
        })

    }

}


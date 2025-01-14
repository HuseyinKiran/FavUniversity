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
import androidx.recyclerview.widget.RecyclerView
import com.huseyinkiran.favuniversities.R
import com.huseyinkiran.favuniversities.adapter.CityAdapter
import com.huseyinkiran.favuniversities.adapter.UniversityAdapter
import com.huseyinkiran.favuniversities.databinding.FragmentHomeBinding
import com.huseyinkiran.favuniversities.viewmodel.CityViewModel
import com.huseyinkiran.favuniversities.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment @Inject constructor(

) : Fragment() {

    @Inject
    lateinit var cityAdapter: CityAdapter

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val cityViewModel: CityViewModel by hiltNavGraphViewModels(R.id.nav_graph)
    private val favoriteViewModel: FavoriteViewModel by hiltNavGraphViewModels(R.id.nav_graph)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.goToFavorite.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToFavoritesFragment())
        }

        setupRecyclerView()
        setupViewModel()

    }

    private fun setupRecyclerView() {

        cityAdapter = CityAdapter(onFavoriteClick = {
            favoriteViewModel.updateFavorites(it)
            Log.d("HomeFragment", "favorite: ${it.name}")
            //favoriteViewModel.isUniversityFavorite(it.name)
        })

        binding.cityRv.adapter = cityAdapter
        binding.cityRv.layoutManager = LinearLayoutManager(requireContext())

        binding.cityRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(3)) {
                    cityViewModel.loadProvinces()
                }
            }
        })

    }

    private fun setupViewModel() {

        cityViewModel.provinceList.observe(viewLifecycleOwner, Observer { provinces ->
            binding.progressBar.visibility = View.GONE
            if (provinces.isNullOrEmpty()) {
                binding.txtError.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
                cityAdapter.updateCities(provinces)
            }
        })

        cityViewModel.errorMessage.observe(viewLifecycleOwner, Observer { isError ->
            if (isError) {
                binding.txtError.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            } else {
                binding.txtError.visibility = View.GONE
            }
        })

        favoriteViewModel.favoriteUniversities.observe(viewLifecycleOwner) { universities ->
            Log.d("HomeFragment", "Favorites: ${universities.map { university -> 
                university.name
            }}")

        }

        binding.progressBar.visibility = View.VISIBLE
        cityViewModel.loadProvinces()

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}


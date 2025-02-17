package com.huseyinkiran.favuniversities.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huseyinkiran.favuniversities.R
import com.huseyinkiran.favuniversities.adapter.ProvinceAdapter
import com.huseyinkiran.favuniversities.databinding.FragmentHomeBinding
import com.huseyinkiran.favuniversities.viewmodel.ProvinceViewModel
import com.huseyinkiran.favuniversities.viewmodel.UniversityViewModel
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private lateinit var provinceAdapter: ProvinceAdapter
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var provinceViewModel: ProvinceViewModel
    private lateinit var favoriteViewModel: UniversityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        provinceViewModel = ViewModelProvider(requireActivity())[ProvinceViewModel::class.java]
        favoriteViewModel = ViewModelProvider(requireActivity())[UniversityViewModel::class.java]

        binding.goToFavorite.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToFavoritesFragment()
            findNavController().navigate(action)
        }

        setupRecyclerView()
        setupViewModel()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (findNavController().currentDestination?.id == R.id.homeFragment) {
                requireActivity().finish()
            }
        }

    }

    private fun setupRecyclerView() {

        provinceAdapter = ProvinceAdapter(
            onFavoriteClick = {
                favoriteViewModel.toggleFavorite(it)
            },
            onWebsiteClick = { websiteUrl, uniName ->
                val action =
                    HomeFragmentDirections.actionHomeFragmentToWebsiteFragment(websiteUrl, uniName)
                findNavController().navigate(action)
            },
            onProvinceExpand = {
                provinceViewModel.checkProvinceExpansion(it)
            },
            onUniversityExpand = {
                provinceViewModel.checkUniversityExpansion(it)
            },
            isUniversityExpandable = favoriteViewModel::isUniversityExpandable
        )

        binding.provinceRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = provinceAdapter
        }

        binding.provinceRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    provinceViewModel.loadProvinces()
                }
            }
        })

    }

    private fun setupViewModel() {

        provinceViewModel.provinceList.observe(viewLifecycleOwner) { provinces ->
            binding.progressBar.visibility = View.GONE
            if (provinces.isNullOrEmpty()) {
                binding.txtError.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
                provinceAdapter.updateProvinces(provinces)
            }
        }

        provinceViewModel.errorMessage.observe(viewLifecycleOwner) { isError ->
            if (isError) {
                binding.txtError.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            } else {
                binding.txtError.visibility = View.GONE
            }
        }

        provinceViewModel.expandedProvinces.observe(viewLifecycleOwner) { expandedCities ->
            provinceAdapter.updateExpandedProvinces(expandedCities.map { it })
        }

        provinceViewModel.expandedUniversities.observe(viewLifecycleOwner) { expandedUniversities ->
            provinceAdapter.updateExpandedUniversities(expandedUniversities.map { it })
        }

        favoriteViewModel.favoriteUniversities.observe(viewLifecycleOwner) { favorites ->
            provinceAdapter.updateFavoriteUniversities(favorites.map { it.name })
        }

        binding.progressBar.visibility = View.VISIBLE
        provinceViewModel.loadProvinces()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
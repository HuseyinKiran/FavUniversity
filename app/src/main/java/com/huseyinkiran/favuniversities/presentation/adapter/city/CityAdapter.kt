package com.huseyinkiran.favuniversities.presentation.adapter.city

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.huseyinkiran.favuniversities.databinding.CellCityBinding
import com.huseyinkiran.favuniversities.domain.model.CityUIModel
import com.huseyinkiran.favuniversities.presentation.adapter.AdapterFragmentType
import com.huseyinkiran.favuniversities.presentation.adapter.university.UniversityAdapter
import com.huseyinkiran.favuniversities.utils.ExpandStateManager

class CityAdapter(
    private val fragmentType: AdapterFragmentType,
    private val callbacks: UniversityAdapter.UniversityClickListener
) : ListAdapter<CityUIModel, CityViewHolder>(CityDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val view = CellCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityViewHolder(view)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {

        val city = getItem(position)
        holder.bind(
            city = city,
            fragmentType = fragmentType,
            callbacks = callbacks,
            onExpandToggle = { clickedCity ->
                val newExpanded = !(ExpandStateManager.expandedCities[clickedCity.name] ?: false)
                ExpandStateManager.expandedCities[clickedCity.name] = newExpanded

                val updatedList = currentList.toMutableList()
                updatedList[position] = clickedCity.copy(isExpanded = newExpanded)
                submitList(updatedList)
            })

    }

}
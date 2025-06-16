package com.huseyinkiran.favuniversities.presentation.adapter.city

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.huseyinkiran.favuniversities.databinding.CellCityBinding
import com.huseyinkiran.favuniversities.domain.model.CityUIModel
import com.huseyinkiran.favuniversities.presentation.adapter.AdapterFragmentType
import com.huseyinkiran.favuniversities.presentation.adapter.university.UniversityAdapter
import com.huseyinkiran.favuniversities.utils.ExpandStateManager

class CityPagingAdapter(
    private val fragmentType: AdapterFragmentType,
    private val callbacks: UniversityAdapter.UniversityClickListener
) : PagingDataAdapter<CityUIModel, CityViewHolder>(CityDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val view = CellCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityViewHolder(view)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city = getItem(position) ?: return
        holder.bind(
            city = city,
            fragmentType = fragmentType,
            callbacks = callbacks,
            onExpandToggle = { updatedCity ->
                val current = snapshot().items.toMutableList()
                val index = current.indexOfFirst { it.name == updatedCity.name }
                if (index != -1) {
                    val newExpanded = !(ExpandStateManager.expandedCities[updatedCity.name] ?: false)
                    ExpandStateManager.expandedCities[updatedCity.name] = newExpanded
                    notifyItemChanged(index)
                }
            }
        )
    }
}

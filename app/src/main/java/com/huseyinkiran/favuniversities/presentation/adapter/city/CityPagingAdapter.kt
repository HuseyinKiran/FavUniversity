package com.huseyinkiran.favuniversities.presentation.adapter.city

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.huseyinkiran.favuniversities.databinding.CellCityBinding
import com.huseyinkiran.favuniversities.presentation.adapter.AdapterFragmentType
import com.huseyinkiran.favuniversities.presentation.adapter.university.UniversityAdapter
import com.huseyinkiran.favuniversities.presentation.model.CityUIModel

class CityPagingAdapter(
    private val fragmentType: AdapterFragmentType,
    private val universityCallbacks: UniversityAdapter.UniversityClickListener,
    private val cityCallback: CityClickListener
) : PagingDataAdapter<CityUIModel, CityViewHolder>(CityDiffCallback()) {

    interface CityClickListener{
        fun onCityExpanded(cityName: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val view = CellCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityViewHolder(view)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city = getItem(position) ?: return
        holder.bind(
            city = city,
            isExpanded = city.isExpanded,
            fragmentType = fragmentType,
            callbacks = universityCallbacks,
            cityCallback = cityCallback
        )
    }
}

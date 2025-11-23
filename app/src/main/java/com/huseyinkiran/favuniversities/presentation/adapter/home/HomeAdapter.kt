package com.huseyinkiran.favuniversities.presentation.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.huseyinkiran.favuniversities.databinding.CellCityHeaderBinding
import com.huseyinkiran.favuniversities.databinding.CellUniversityBinding
import com.huseyinkiran.favuniversities.presentation.adapter.AdapterFragmentType
import com.huseyinkiran.favuniversities.presentation.adapter.university.UniversityAdapter
import com.huseyinkiran.favuniversities.presentation.adapter.university.UniversityViewHolder
import com.huseyinkiran.favuniversities.presentation.model.UniversityUIModel

class HomeAdapter(
    private val fragmentType: AdapterFragmentType,
    private val universityCallbacks: UniversityAdapter.UniversityClickListener,
    private val cityClickListener: CityClickListener
) : PagingDataAdapter<HomeItem, RecyclerView.ViewHolder>(HomeDiffCallback()) {

    interface CityClickListener {
        fun onCityExpanded(cityName: String)
    }

    companion object {
        private const val VIEW_TYPE_CITY = 1
        private const val VIEW_TYPE_UNIVERSITY = 2
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is HomeItem.CityHeader -> VIEW_TYPE_CITY
            is HomeItem.UniversityRow -> VIEW_TYPE_UNIVERSITY
            null -> throw IllegalStateException("Item is null at position $position")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_CITY -> {
                val binding = CellCityHeaderBinding.inflate(inflater, parent, false)
                CityHeaderViewHolder(binding)
            }
            VIEW_TYPE_UNIVERSITY -> {
                val binding = CellUniversityBinding.inflate(inflater, parent, false)
                UniversityViewHolder(binding)
            }
            else -> error("Unknown viewType: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is HomeItem.CityHeader -> {
                (holder as CityHeaderViewHolder).bind(item, cityClickListener)
            }
            is HomeItem.UniversityRow -> {
                val university: UniversityUIModel = item.university
                (holder as UniversityViewHolder).bind(
                    university = university,
                    fragmentType = fragmentType,
                    callbacks = universityCallbacks
                )
            }
            null -> Unit
        }
    }
}
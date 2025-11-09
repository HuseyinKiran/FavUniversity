package com.huseyinkiran.favuniversities.presentation.adapter.city

import androidx.recyclerview.widget.DiffUtil
import com.huseyinkiran.favuniversities.presentation.model.CityUIModel

class CityDiffCallback : DiffUtil.ItemCallback<CityUIModel>() {
    override fun areItemsTheSame(oldItem: CityUIModel, newItem: CityUIModel): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: CityUIModel, newItem: CityUIModel): Boolean {
        return oldItem == newItem
    }
}
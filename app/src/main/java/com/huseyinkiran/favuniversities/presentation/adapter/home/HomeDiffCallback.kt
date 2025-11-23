package com.huseyinkiran.favuniversities.presentation.adapter.home

import androidx.recyclerview.widget.DiffUtil


class HomeDiffCallback : DiffUtil.ItemCallback<HomeItem>() {
    override fun areItemsTheSame(oldItem: HomeItem, newItem: HomeItem): Boolean {
        return when {
            oldItem is HomeItem.CityHeader && newItem is HomeItem.CityHeader ->
                oldItem.cityId == newItem.cityId

            oldItem is HomeItem.UniversityRow && newItem is HomeItem.UniversityRow ->
                oldItem.university.id == newItem.university.id

            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: HomeItem, newItem: HomeItem): Boolean {
        return oldItem == newItem
    }
}
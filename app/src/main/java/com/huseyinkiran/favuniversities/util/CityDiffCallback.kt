package com.huseyinkiran.favuniversities.util

import androidx.recyclerview.widget.DiffUtil
import com.huseyinkiran.favuniversities.model.Province

class CityDiffCallback : DiffUtil.ItemCallback<Province>() {

    override fun areItemsTheSame(oldItem: Province, newItem: Province): Boolean {
        return oldItem.province == newItem.province
    }

    override fun areContentsTheSame(oldItem: Province, newItem: Province): Boolean {
        return oldItem == newItem
    }
}



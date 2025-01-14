package com.huseyinkiran.favuniversities.util


import androidx.recyclerview.widget.DiffUtil
import com.huseyinkiran.favuniversities.model.University

class UniversityDiffCallback : DiffUtil.ItemCallback<University>() {
    override fun areItemsTheSame(oldItem: University, newItem: University): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: University, newItem: University): Boolean {
        return oldItem == newItem
    }
}


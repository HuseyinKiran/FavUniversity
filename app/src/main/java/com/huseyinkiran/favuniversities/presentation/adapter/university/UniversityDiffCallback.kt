package com.huseyinkiran.favuniversities.presentation.adapter.university

import androidx.recyclerview.widget.DiffUtil
import com.huseyinkiran.favuniversities.presentation.model.UniversityUIModel

class UniversityDiffCallback : DiffUtil.ItemCallback<UniversityUIModel>() {

    override fun areItemsTheSame(oldItem: UniversityUIModel, newItem: UniversityUIModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UniversityUIModel, newItem: UniversityUIModel): Boolean {
        return oldItem == newItem
    }

}
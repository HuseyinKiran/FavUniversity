package com.huseyinkiran.favuniversities.presentation.adapter.university

import androidx.recyclerview.widget.DiffUtil
import com.huseyinkiran.favuniversities.domain.model.UniversityUIModel

class UniversityDiffCallback : DiffUtil.ItemCallback<UniversityUIModel>() {

    override fun areItemsTheSame(oldItem: UniversityUIModel, newItem: UniversityUIModel): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: UniversityUIModel, newItem: UniversityUIModel): Boolean {
        return oldItem == newItem
    }

}
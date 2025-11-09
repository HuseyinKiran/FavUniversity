package com.huseyinkiran.favuniversities.presentation.adapter.university

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.huseyinkiran.favuniversities.databinding.CellUniversityBinding
import com.huseyinkiran.favuniversities.presentation.adapter.AdapterFragmentType
import com.huseyinkiran.favuniversities.presentation.model.UniversityUIModel

class UniversityAdapter :
    ListAdapter<UniversityUIModel, UniversityViewHolder>(UniversityDiffCallback()) {

    lateinit var fragmentType: AdapterFragmentType
    lateinit var callbacks: UniversityClickListener

    interface UniversityClickListener {
        fun onFavoriteClick(university: UniversityUIModel)
        fun onWebsiteClick(websiteUrl: String, universityName: String)
        fun onPhoneClick(phoneNumber: String)
        fun onUniversityExpanded(universityName: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UniversityViewHolder {
        val view = CellUniversityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UniversityViewHolder(view)
    }

    override fun onBindViewHolder(holder: UniversityViewHolder, position: Int) {
        val university = getItem(position)
        holder.bind(
            university = university,
            fragmentType = fragmentType,
            callbacks = callbacks,
        )
    }

}
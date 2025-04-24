package com.huseyinkiran.favuniversities.presentation.adapter.university

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.huseyinkiran.favuniversities.databinding.CellUniversityBinding
import com.huseyinkiran.favuniversities.domain.model.UniversityUIModel
import com.huseyinkiran.favuniversities.presentation.adapter.AdapterFragmentType
import com.huseyinkiran.favuniversities.utils.ExpandStateManager

class UniversityAdapter(
    private val fragmentType: AdapterFragmentType,
    private val callbacks: UniversityClickListener
) : ListAdapter<UniversityUIModel, UniversityViewHolder>(UniversityDiffCallback()) {

    interface UniversityClickListener {
        fun onFavoriteClick(university: UniversityUIModel)
        fun onWebsiteClick(websiteUrl: String, universityName: String)
        fun onPhoneClick(phoneNumber: String)
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
            onExpandToggle = { adapterPosition ->
                if (adapterPosition == RecyclerView.NO_POSITION || adapterPosition >= currentList.size) return@bind
                val clickedUniversity = getItem(adapterPosition)
                val expandedList =
                    when (fragmentType) {
                        AdapterFragmentType.HOME -> ExpandStateManager.homeExpandedUniversities
                        else -> ExpandStateManager.favoritesExpandedUniversities
                    }
                val newExpanded = !(expandedList[clickedUniversity.name] ?: false)
                expandedList[clickedUniversity.name] = newExpanded

                val updatedList = currentList.toMutableList()
                updatedList[adapterPosition] = clickedUniversity.copy(isExpanded = newExpanded)
                submitList(updatedList)
            })
    }

}
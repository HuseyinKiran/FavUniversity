package com.huseyinkiran.favuniversities.presentation.adapter.city

import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huseyinkiran.favuniversities.R
import com.huseyinkiran.favuniversities.databinding.CellCityBinding
import com.huseyinkiran.favuniversities.domain.model.CityUIModel
import com.huseyinkiran.favuniversities.presentation.adapter.AdapterFragmentType
import com.huseyinkiran.favuniversities.presentation.adapter.university.UniversityAdapter
import com.huseyinkiran.favuniversities.utils.ExpandStateManager

class CityViewHolder(private val binding: CellCityBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        city: CityUIModel,
        fragmentType: AdapterFragmentType,
        callbacks: UniversityAdapter.UniversityClickListener,
        onExpandToggle: (CityUIModel) -> Unit
    ) = with(binding) {

        txtCityName.text = city.name

        val universityAdapter = UniversityAdapter(
            fragmentType = fragmentType,
            callbacks = callbacks
        )

        rvUniversity.layoutManager = LinearLayoutManager(root.context)
        rvUniversity.adapter = universityAdapter
        rvUniversity.itemAnimator = null

        universityAdapter.submitList(city.universities)

        val expandedList = ExpandStateManager.expandedCities
        city.isExpanded = expandedList[city.name] ?: false

        imgBtn.isInvisible = !city.isExpandable
        rvUniversity.isGone = !city.isExpanded

        imgBtn.setImageResource(
            if (city.isExpanded) R.drawable.baseline_keyboard_arrow_up_24
            else R.drawable.baseline_keyboard_arrow_down_24
        )

        cardCity.setOnClickListener {
            if (city.isExpandable) {
                onExpandToggle(city)
            }
        }

    }

}

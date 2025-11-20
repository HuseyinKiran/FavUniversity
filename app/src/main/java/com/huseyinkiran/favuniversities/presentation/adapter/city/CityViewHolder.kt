package com.huseyinkiran.favuniversities.presentation.adapter.city

import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huseyinkiran.favuniversities.R
import com.huseyinkiran.favuniversities.databinding.CellCityBinding
import com.huseyinkiran.favuniversities.presentation.adapter.AdapterFragmentType
import com.huseyinkiran.favuniversities.presentation.adapter.university.UniversityAdapter
import com.huseyinkiran.favuniversities.presentation.model.CityUIModel

class CityViewHolder(private val binding: CellCityBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private val universityAdapter = UniversityAdapter()

    init {
        binding.rvUniversity.apply {
            layoutManager = LinearLayoutManager(itemView.context)
            adapter = universityAdapter
            itemAnimator = null
        }
    }

    fun setRecycledViewPool(viewPool: RecyclerView.RecycledViewPool) {
        binding.rvUniversity.setRecycledViewPool(viewPool)
    }

    fun bind(
        city: CityUIModel,
        isExpanded: Boolean,
        fragmentType: AdapterFragmentType,
        callbacks: UniversityAdapter.UniversityClickListener,
        cityCallback: CityPagingAdapter.CityClickListener
    ) = with(binding) {

        txtCityName.text = city.name
        rvUniversity.isGone = !isExpanded
        imgBtn.setImageResource(
            if (isExpanded) R.drawable.baseline_keyboard_arrow_up_24
            else R.drawable.baseline_keyboard_arrow_down_24
        )

        universityAdapter.fragmentType = fragmentType
        universityAdapter.callbacks = callbacks
        universityAdapter.submitList(city.universities)

        cardCity.setOnClickListener {
            cityCallback.onCityExpanded(city.name)
        }
    }
}

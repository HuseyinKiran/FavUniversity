package com.huseyinkiran.favuniversities.presentation.adapter.home

import androidx.recyclerview.widget.RecyclerView
import com.huseyinkiran.favuniversities.R
import com.huseyinkiran.favuniversities.databinding.CellCityHeaderBinding

class CityHeaderViewHolder(
    private val binding: CellCityHeaderBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: HomeItem.CityHeader,
        cityClickListener: HomeAdapter.CityClickListener
    ) = with(binding) {
        txtCityName.text = item.cityName

        imgBtn.setImageResource(
            if (item.isExpanded) R.drawable.baseline_keyboard_arrow_up_24
            else R.drawable.baseline_keyboard_arrow_down_24
        )

        cardCity.setOnClickListener {
            cityClickListener.onCityExpanded(item.cityName)
        }
    }
}

package com.huseyinkiran.favuniversities.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.huseyinkiran.favuniversities.R
import com.huseyinkiran.favuniversities.databinding.CellCityBinding
import com.huseyinkiran.favuniversities.model.Province
import com.huseyinkiran.favuniversities.model.University

class CityAdapter(
    private val onFavoriteClick: (University) -> Unit,
    private val onWebsiteClick: (String, String) -> Unit
) :
    RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    class CityViewHolder(val binding: CellCityBinding) : ViewHolder(binding.root)

    private var cityList: List<Province> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val view = CellCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityViewHolder(view)
    }

    override fun getItemCount(): Int = cityList.size

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {

        val city = cityList[position]

        holder.binding.apply {
            txtCityName.text = city.province

            Log.d("Cities", "${city.province} ")

            val universityAdapter = UniversityAdapter(onFavoriteClick, onWebsiteClick)
            universityAdapter.updateUniversities(city.universities)
            uniRv.layoutManager = LinearLayoutManager(root.context)
            uniRv.adapter = universityAdapter

            uniRv.visibility = View.GONE

            cardCity.setOnClickListener {
                if (uniRv.visibility == View.GONE) {
                    uniRv.visibility = View.VISIBLE
                    imgBtn.setImageResource(R.drawable.baseline_remove_24)
                } else {
                    uniRv.visibility = View.GONE
                    imgBtn.setImageResource(R.drawable.baseline_add_24)
                }
            }

        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateCities(newCities: List<Province>) {
        cityList = newCities
        notifyDataSetChanged()
    }

}
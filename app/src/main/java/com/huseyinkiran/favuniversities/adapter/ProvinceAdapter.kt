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
import com.huseyinkiran.favuniversities.databinding.CellProvinceBinding
import com.huseyinkiran.favuniversities.model.Province
import com.huseyinkiran.favuniversities.model.University

class ProvinceAdapter(
    private val onFavoriteClick: (University) -> Unit,
    private val onWebsiteClick: (String, String) -> Unit,
    private val onProvinceExpand: (String) -> Unit,
    private val onUniversityExpand: (String) -> Unit,
) :
    RecyclerView.Adapter<ProvinceAdapter.ProvinceViewHolder>() {

    class ProvinceViewHolder(val binding: CellProvinceBinding) : ViewHolder(binding.root)

    private var provinceList: List<Province> = listOf()
    private var favoriteUniversities: List<University> = listOf()
    private var expandedProvinces: Set<String> = setOf()
    private var expandedUniversities: Set<String> = setOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProvinceViewHolder {
        val view = CellProvinceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProvinceViewHolder(view)
    }

    override fun getItemCount(): Int = provinceList.size

    override fun onBindViewHolder(holder: ProvinceViewHolder, position: Int) {

        val province = provinceList[position]

        holder.binding.apply {
            txtCityName.text = province.province

            Log.d("Cities", "${province.province} ")

            val universityAdapter = UniversityAdapter(
                onFavoriteClick = onFavoriteClick,
                onWebsiteClick = onWebsiteClick,
                onUniversityExpand = onUniversityExpand
            )

            universityAdapter.updateUniversities(province.universities)
            universityAdapter.updateExpandedUniversities(expandedUniversities)
           // universityAdapter.updateFavoriteUniversities(favoriteUniversities)

            uniRv.layoutManager = LinearLayoutManager(root.context)
            uniRv.adapter = universityAdapter

            val isExpanded = expandedProvinces.contains(province.province)
            uniRv.visibility =
                if (isExpanded) View.VISIBLE else View.GONE
            imgBtn.setImageResource(
                if (isExpanded) R.drawable.baseline_remove_24
                else R.drawable.baseline_add_24
            )

            cardCity.setOnClickListener {
                onProvinceExpand(province.province)
            }

        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateProvinces(newProvinces: List<Province>) {
        provinceList = newProvinces
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateFavoriteUniversities(newFavorites: List<University>) {
        favoriteUniversities = newFavorites
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateExpandedUniversities(newExpandedUniversities: Set<String>) {
        expandedUniversities = newExpandedUniversities
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateExpandedCities(newExpandedProvinces: Set<String>) {
        expandedProvinces = newExpandedProvinces
        notifyDataSetChanged()
    }

}
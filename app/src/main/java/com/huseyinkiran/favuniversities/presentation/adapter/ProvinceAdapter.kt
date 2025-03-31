package com.huseyinkiran.favuniversities.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.huseyinkiran.favuniversities.R
import com.huseyinkiran.favuniversities.databinding.CellProvinceBinding
import com.huseyinkiran.favuniversities.domain.model.ProvinceUIModel
import com.huseyinkiran.favuniversities.domain.model.UniversityUIModel
import com.huseyinkiran.favuniversities.utils.ExpandStateManager

class ProvinceAdapter(
    private val onFavoriteClick: (UniversityUIModel) -> Unit,
    private val onWebsiteClick: (String, String) -> Unit,
    private val onPhoneClick: (String) -> Unit
) :
    RecyclerView.Adapter<ProvinceAdapter.ProvinceViewHolder>() {

    class ProvinceViewHolder(val binding: CellProvinceBinding) : ViewHolder(binding.root)

    private var provinceList: List<ProvinceUIModel> = listOf()
    private var favoriteUniversities: List<UniversityUIModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProvinceViewHolder {
        val view = CellProvinceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProvinceViewHolder(view)
    }

    override fun getItemCount(): Int = provinceList.size

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ProvinceViewHolder, position: Int) {

        val province = provinceList[position]

        holder.binding.apply {

            txtCityName.text = province.name

            val universityAdapter = UniversityAdapter(
                fragmentType = "Home",
                onFavoriteClick = onFavoriteClick,
                onWebsiteClick = onWebsiteClick,
                onPhoneClick = onPhoneClick
            )

            universityAdapter.updateUniversities(province.universities.map { university ->
                university.copy(isFavorite = favoriteUniversities.any { it.name == university.name })
            })

            uniRv.layoutManager = LinearLayoutManager(root.context)
            uniRv.adapter = universityAdapter

            val expandedList = ExpandStateManager.expandedProvinces
            province.isExpanded = expandedList[province.name] ?: false

            imgBtn.isInvisible = !province.isExpandable
            uniRv.isGone = !province.isExpanded

            imgBtn.setImageResource(
                if (province.isExpanded) R.drawable.baseline_keyboard_arrow_up_24
                else R.drawable.baseline_keyboard_arrow_down_24
            )

            cardCity.setOnClickListener {
                if (province.isExpandable) {
                    province.isExpanded = !province.isExpanded
                    expandedList[province.name] = province.isExpanded
                    notifyDataSetChanged()
                }
            }

        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateProvinces(newProvinces: List<ProvinceUIModel>) {
        provinceList = newProvinces
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateFavoriteUniversities(newFavorites: List<UniversityUIModel>) {
        favoriteUniversities = newFavorites
        notifyDataSetChanged()
    }

}
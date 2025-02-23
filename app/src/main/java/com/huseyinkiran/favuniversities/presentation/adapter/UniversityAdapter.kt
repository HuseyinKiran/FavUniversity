package com.huseyinkiran.favuniversities.presentation.adapter

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.huseyinkiran.favuniversities.R
import com.huseyinkiran.favuniversities.databinding.CellUniversityBinding
import com.huseyinkiran.favuniversities.domain.model.University
import com.huseyinkiran.favuniversities.utils.ExpandStateManager


class UniversityAdapter(
    private val fragmentType: String,
    private val onFavoriteClick: (University) -> Unit,
    private val onWebsiteClick: (String, String) -> Unit,
    private val onPhoneClick: (String) -> Unit
) :
    RecyclerView.Adapter<UniversityAdapter.UniversityViewHolder>() {

    class UniversityViewHolder(val binding: CellUniversityBinding) : ViewHolder(binding.root)

    private var universityList: List<University> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UniversityViewHolder {
        val view = CellUniversityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UniversityViewHolder(view)
    }

    override fun getItemCount(): Int = universityList.size

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: UniversityViewHolder, position: Int) {

        val university = universityList[position]

        holder.binding.apply {

            txtUniName.text = university.name
            txtAddress.text = university.address
            txtFax.text = university.fax
            txtPhone.text = university.phone
            txtRector.text = university.rector
            txtWebsite.text = university.website

            txtPhone.paintFlags = txtPhone.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            txtWebsite.paintFlags = txtWebsite.paintFlags or Paint.UNDERLINE_TEXT_FLAG

            btnFavorite.setImageResource(
                if (university.isFavorite) R.drawable.baseline_favorite_24
                else R.drawable.baseline_favorite_border_24
            )

            val expandedList =
                when (fragmentType) {
                    "Home" -> ExpandStateManager.homeExpandedUniversities
                    else -> ExpandStateManager.favoritesExpandedUniversities
                }

            university.isExpanded = expandedList[university.name] ?: false

            btnExpand.isInvisible = !university.isExpandable
            cardUnivInfo.isGone = !university.isExpanded

            btnExpand.setImageResource(
                if (university.isExpanded) R.drawable.baseline_keyboard_arrow_up_24
                else R.drawable.baseline_keyboard_arrow_down_24
            )

            universityLayout.setOnClickListener {
                if (university.isExpandable) {
                    university.isExpanded = !university.isExpanded
                    expandedList[university.name] = university.isExpanded
                    notifyDataSetChanged()
                }
            }

            btnFavorite.setOnClickListener {
                onFavoriteClick(university)
            }

            txtPhone.setOnClickListener {
                onPhoneClick(university.phone)
            }

            txtWebsite.setOnClickListener {
                onWebsiteClick(university.website, university.name)
            }

        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateUniversities(newUniversities: List<University>) {
        universityList = newUniversities
        notifyDataSetChanged()
    }

}
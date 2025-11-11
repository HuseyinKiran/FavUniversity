package com.huseyinkiran.favuniversities.presentation.adapter.university

import android.graphics.Paint
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.huseyinkiran.favuniversities.R
import com.huseyinkiran.favuniversities.databinding.CellUniversityBinding
import com.huseyinkiran.favuniversities.presentation.adapter.AdapterFragmentType
import com.huseyinkiran.favuniversities.presentation.model.UniversityUIModel

class UniversityViewHolder(private val binding: CellUniversityBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        university: UniversityUIModel,
        fragmentType: AdapterFragmentType,
        callbacks: UniversityAdapter.UniversityClickListener,
    ) = with(binding) {

        txtUniType.text = university.universityType
        txtUniName.text = university.name
        txtAddress.text = university.address
        txtFax.text = university.fax
        txtPhone.text = university.phone
        txtRector.text = university.rector
        txtWebsite.text = university.website
        txtEmail.text = university.email

        txtPhone.paintFlags = txtPhone.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        txtWebsite.paintFlags = txtWebsite.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        btnFavorite.setImageResource(
            if (university.isFavorite) R.drawable.baseline_favorite_24
            else R.drawable.baseline_favorite_border_24
        )

        btnExpand.isInvisible = !university.isExpandable
        cardUnivInfo.isGone = !university.isExpanded
        btnExpand.setImageResource(
            if (university.isExpanded) R.drawable.baseline_keyboard_arrow_up_24
            else R.drawable.baseline_keyboard_arrow_down_24
        )

        universityLayout.setOnClickListener {
            if (university.isExpandable) callbacks.onUniversityExpanded(university.name)
        }

        btnFavorite.setOnClickListener { callbacks.onFavoriteClick(university) }
        txtPhone.setOnClickListener { callbacks.onPhoneClick(university.phone) }
        txtWebsite.setOnClickListener { callbacks.onWebsiteClick(university.website, university.name) }
    }
}
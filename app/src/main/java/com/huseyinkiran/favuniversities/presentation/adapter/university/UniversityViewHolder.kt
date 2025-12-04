package com.huseyinkiran.favuniversities.presentation.adapter.university

import android.graphics.Paint
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.huseyinkiran.favuniversities.R
import com.huseyinkiran.favuniversities.core.ui.dpToPx
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

        val endPaddingDp = when (fragmentType) {
            AdapterFragmentType.HOME -> 8
            AdapterFragmentType.FAVORITES -> 16
            AdapterFragmentType.SEARCH -> 16
        }

        val endPaddingPx = endPaddingDp.dpToPx(root.context)
        txtUniName.setPadding(0, 0, endPaddingPx, 0)

        txtUniType.text = university.universityType
        txtUniName.text = university.name
        txtAddress.text = university.address
        txtFax.text = university.fax
        txtPhone.text = university.phone
        txtRector.text = university.rector
        txtWebsite.text = university.website
        txtEmail.text = university.email

        if (university.phone != "-" && university.phone.isNotBlank()) {
            txtPhone.paintFlags = txtPhone.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            txtPhone.setOnClickListener { callbacks.onPhoneClick(university.phone) }
        } else {
            txtPhone.paintFlags = txtPhone.paintFlags and Paint.UNDERLINE_TEXT_FLAG.inv()
            txtPhone.setOnClickListener(null)
        }

        if (university.website != "-" && university.website.isNotBlank()) {
            txtWebsite.paintFlags = txtWebsite.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            txtWebsite.setOnClickListener {
                callbacks.onWebsiteClick(university.website, university.name)
            }
        } else {
            txtWebsite.paintFlags = txtWebsite.paintFlags and Paint.UNDERLINE_TEXT_FLAG.inv()
            txtWebsite.setOnClickListener(null)
        }

        if (university.email != "-" && university.email.isNotBlank()) {
            txtEmail.paintFlags = txtEmail.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            txtEmail.setOnClickListener { callbacks.onEmailClick(university.email) }
        } else {
            txtEmail.paintFlags = txtEmail.paintFlags and Paint.UNDERLINE_TEXT_FLAG.inv()
            txtEmail.setOnClickListener(null)
        }

        if (university.address != "-" && university.address.isNotBlank()) {
            txtAddress.paintFlags = txtAddress.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            txtAddress.setOnClickListener { callbacks.onAddressClick(university.address) }
        } else {
            txtAddress.paintFlags = txtAddress.paintFlags and Paint.UNDERLINE_TEXT_FLAG.inv()
            txtAddress.setOnClickListener(null)
        }

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

    }
}
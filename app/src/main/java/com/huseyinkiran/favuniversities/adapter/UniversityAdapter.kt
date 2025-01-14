package com.huseyinkiran.favuniversities.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.huseyinkiran.favuniversities.R
import com.huseyinkiran.favuniversities.databinding.CellUniversityBinding
import com.huseyinkiran.favuniversities.model.University
import com.huseyinkiran.favuniversities.view.HomeFragment
import com.huseyinkiran.favuniversities.view.WebsiteFragment
import javax.inject.Inject

class UniversityAdapter @Inject constructor(
    private val onFavoriteClick: (University) -> Unit
) :
    RecyclerView.Adapter<UniversityAdapter.UniversityViewHolder>() {

    class UniversityViewHolder(val binding: CellUniversityBinding) : ViewHolder(binding.root)

    private var universityList: List<University> = listOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UniversityViewHolder {
        val view = CellUniversityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UniversityViewHolder(view)
    }

    override fun getItemCount(): Int = universityList.size

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

            if (university.rector == "-" && university.phone == "-" &&
                university.fax == "-" && university.address == "-" && university.email == "-"
            ) {
                btnExpand.visibility = View.INVISIBLE
            } else {
                universityLayout.setOnClickListener {
                    if (cardUnivInfo.visibility == View.GONE) {
                        cardUnivInfo.visibility = View.VISIBLE
                        btnExpand.setImageResource(R.drawable.baseline_remove_24)
                    } else {
                        cardUnivInfo.visibility = View.GONE
                        btnExpand.setImageResource(R.drawable.baseline_add_24)
                    }
                }
            }

            btnFavorite.setOnClickListener {
                university.isFavorite = !university.isFavorite
                btnFavorite.setImageResource(
                    if (university.isFavorite) R.drawable.baseline_favorite_24
                    else R.drawable.baseline_favorite_border_24
                )
                onFavoriteClick(university)
                Log.d("UNIVERSITY_ADAPTER", "Favorite clicked for ${university.name}")
                val message =
                    if (university.isFavorite) "Added to favorites" else "Removed from favorites"
                Toast.makeText(holder.itemView.context, message, Toast.LENGTH_SHORT).show()
            }

            txtPhone.setOnClickListener {
                val context = root.context
                Log.d("UniversityAdapter", "tel:${university.phone}")
                callPhoneNumber(university, context)
            }

            txtWebsite.setOnClickListener {
                Log.d("UniversityAdapter", "website:${university.website}")
                  //navigateToWebsite(university.website, fragment)
            }

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateUniversities(newUniversities: List<University>) {
        universityList = newUniversities
        notifyDataSetChanged()
    }

    private fun callPhoneNumber(university: University, context: Context) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:${university.phone}")
        }

        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            context.startActivity(intent)
        } else {
            if (context is Activity) {
                ActivityCompat.requestPermissions(
                    context,
                    arrayOf(android.Manifest.permission.CALL_PHONE),
                    1
                )
            }
        }
    }

    private fun navigateToWebsite(websiteUrl: String, fragment: FragmentActivity) {
        val bundle = Bundle().apply {
            putString("WEBSITE_URL", websiteUrl)
        }
        val websiteFragment = WebsiteFragment().apply {
            arguments = bundle
        }
        fragment.supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, websiteFragment)
            .addToBackStack(null)
            .commit()
    }


}


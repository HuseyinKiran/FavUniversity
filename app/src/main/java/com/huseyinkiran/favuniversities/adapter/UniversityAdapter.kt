package com.huseyinkiran.favuniversities.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.huseyinkiran.favuniversities.R
import com.huseyinkiran.favuniversities.databinding.CellUniversityBinding
import com.huseyinkiran.favuniversities.model.University

class UniversityAdapter(
    private val onFavoriteClick: (University) -> Unit,
    private val onWebsiteClick: (String, String) -> Unit,
    private val onUniversityExpand: (String) -> Unit
) :
    RecyclerView.Adapter<UniversityAdapter.UniversityViewHolder>() {

    class UniversityViewHolder(val binding: CellUniversityBinding) : ViewHolder(binding.root)

    private var universityList: List<University> = listOf()
    private var favoriteUniversities: List<University> = listOf()
    private var expandedUniversities: Set<String> = setOf()

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


            val isFavorite = university.isFavorite
            btnFavorite.setImageResource(
                if (isFavorite) R.drawable.baseline_favorite_24
                else R.drawable.baseline_favorite_border_24
            )

    /*        btnFavorite.setOnClickListener {
                var isNowFavorite = university.isFavorite
                isNowFavorite = !isNowFavorite
                onFavoriteClick(university)
                Log.d("UNIVERSITY_ADAPTER", "Favorite clicked for ${university.name}")
                val message =
                    if (isNowFavorite) "Added to favorites" else "Removed from favorites"
                Toast.makeText(holder.itemView.context, message, Toast.LENGTH_SHORT).show()
            }

     */

            val isExpanded = expandedUniversities.contains(university.name)
            if (university.rector == "-" && university.phone == "-" &&
                university.fax == "-" && university.address == "-" && university.email == "-"
            ) {
                btnExpand.visibility = View.INVISIBLE
            } else {
                cardUnivInfo.visibility =
                    if (isExpanded) View.VISIBLE else View.GONE
                if (isExpanded) R.drawable.baseline_remove_24
                else R.drawable.baseline_add_24
            }

            universityLayout.setOnClickListener {
                onUniversityExpand(university.name)
            }

            txtPhone.setOnClickListener {
                callPhoneNumber(university, it.context)
            }

            txtWebsite.setOnClickListener {
                Log.d("UniversityAdapter", "website:${university.website}")
                onWebsiteClick(university.website, university.name)
            }

            btnFavorite.setOnClickListener {
                university.isFavorite = !university.isFavorite
                onFavoriteClick(university)
                Log.d("UNIVERSITY_ADAPTER", "Favorite clicked for ${university.name}")
                val message =
                    if (university.isFavorite) "Favorilere eklendi" else "Favorilerden kaldırıldı"
                Toast.makeText(holder.itemView.context, message, Toast.LENGTH_SHORT).show()
            }

   /*         btnFavorite.setOnClickListener {
                university.isFavorite = !university.isFavorite
                btnFavorite.setImageResource(
                    if (university.isFavorite) R.drawable.baseline_favorite_24
                    else R.drawable.baseline_favorite_border_24
                )
                onFavoriteClick(university)
                Log.d("UNIVERSITY_ADAPTER", "Favorite clicked for ${university.name}")
                val message =
                    if (university.isFavorite) "Favorilere eklendi" else "Favorilerden kaldırıldı"
                Toast.makeText(holder.itemView.context, message, Toast.LENGTH_SHORT).show()
            } // burası çalışınca ui güncelleniyor favoritesfragment'ta fakat yukarıdaki alan ui çalışmıyor


    */

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateUniversities(newUniversities: List<University>) {
        universityList = newUniversities
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

    private fun callPhoneNumber(university: University, context: Context) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:${university.phone}")
        context.startActivity(intent)
        Log.d("UniversityAdapter", "tel : ${university.phone}: ")
    }

}
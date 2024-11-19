package com.huseyinkiran.turkishuniversities.adapter

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Paint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.huseyinkiran.turkishuniversities.R
import com.huseyinkiran.turkishuniversities.model.University
import com.huseyinkiran.turkishuniversities.view.WebsiteActivity
import com.huseyinkiran.turkishuniversities.viewmodel.ProvinceVM

class UniversityAdapter(
    private var universityList: List<University>,
    private val viewModel: ProvinceVM,
    private val context: Context,
    private val onFavoriteClick: (University) -> Unit
) : RecyclerView.Adapter<UniversityAdapter.UniversityViewHolder>() {

    class UniversityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val uniName: TextView = itemView.findViewById(R.id.txtUniName)
        val phoneNo: TextView = itemView.findViewById(R.id.txtPhone)
        val fax: TextView = itemView.findViewById(R.id.txtFax)
        val website: TextView = itemView.findViewById(R.id.txtWebsite)
        val address: TextView = itemView.findViewById(R.id.txtAddress)
        val rector: TextView = itemView.findViewById(R.id.txtRector)
        val favoriteButton: ImageButton = itemView.findViewById(R.id.btnFavorite)
        val expandButton: ImageButton = itemView.findViewById(R.id.btnExpand)
        val cardUnivInfo: CardView = itemView.findViewById(R.id.cardUnivInfo)
        val cardUni: CardView = itemView.findViewById(R.id.cardUni)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UniversityViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cell_university, parent, false)
        return UniversityViewHolder(view)
    }

    override fun getItemCount(): Int = universityList.size

    override fun onBindViewHolder(holder: UniversityViewHolder, position: Int) {
        val university = universityList[position]
        holder.uniName.text = university.name
        holder.phoneNo.text = university.phone
        holder.fax.text = university.fax
        holder.website.text = university.website
        holder.address.text = university.address
        holder.rector.text = university.rector
        holder.cardUnivInfo.visibility = View.GONE

        holder.phoneNo.paintFlags = holder.phoneNo.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        holder.website.paintFlags = holder.website.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        viewModel.isUniversityFavorite(university.name).observeForever { isFavorite ->
            university.isFavorite = isFavorite
            holder.favoriteButton.setImageResource(
                if (university.isFavorite) R.drawable.baseline_favorite_24
                else R.drawable.baseline_favorite_border_24
            )
        }

        if (university.rector == "-" && university.phone == "-" &&
            university.fax == "-" && university.address == "-" && university.email == "-"
        ) {
            holder.expandButton.visibility = View.INVISIBLE
        } else {
            holder.cardUni.setOnClickListener {
                if (holder.cardUnivInfo.visibility == View.GONE) {
                    holder.cardUnivInfo.visibility = View.VISIBLE
                    holder.expandButton.setImageResource(R.drawable.baseline_remove_24)
                } else {
                    holder.cardUnivInfo.visibility = View.GONE
                    holder.expandButton.setImageResource(R.drawable.baseline_add_24)
                }
            }
        }


        holder.favoriteButton.setOnClickListener {

            university.isFavorite = !university.isFavorite

            holder.favoriteButton.setImageResource(
                if (university.isFavorite) R.drawable.baseline_favorite_24
                else R.drawable.baseline_favorite_border_24
            )
            onFavoriteClick(university)


            val message =
                if (university.isFavorite) "Added to favorites" else "Removed from favorites"
            Toast.makeText(holder.itemView.context, message, Toast.LENGTH_SHORT).show()
        }

        holder.website.setOnClickListener {
            val intent = Intent(context, WebsiteActivity::class.java).apply {
                putExtra("WEBSITE_URL", university.website)
                putExtra("UNIVERSITY_NAME", university.name)
            }
            context.startActivity(intent)
        }


        holder.phoneNo.setOnClickListener {
            val phoneNumber = university.phone

            AlertDialog.Builder(context)
                .setMessage("Bu numarayı aramak istediğinize emin misiniz?")
                .setCancelable(false)
                .setPositiveButton("Ara") { dialog, id ->
                    val intent = Intent(Intent.ACTION_CALL).apply {
                        data = Uri.parse("tel:$phoneNumber")
                    }

                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        context.startActivity(intent)
                    } else {
                        ActivityCompat.requestPermissions(
                            context as Activity,
                            arrayOf(Manifest.permission.CALL_PHONE),
                            1
                        )
                    }
                }
                .setNegativeButton("İptal") { dialog, id ->

                }
                .create()
                .show()
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newList: List<University>) {
        universityList = newList
        notifyDataSetChanged()
    }

}
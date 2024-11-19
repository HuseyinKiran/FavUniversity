package com.huseyinkiran.turkishuniversities.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huseyinkiran.turkishuniversities.R
import com.huseyinkiran.turkishuniversities.model.Province
import com.huseyinkiran.turkishuniversities.model.University
import com.huseyinkiran.turkishuniversities.viewmodel.ProvinceVM

class ProvinceAdapter(
    private var cityList: List<Province>,
    private val viewModel: ProvinceVM,
    private val context: Context,
    private val onFavoriteClick: (University) -> Unit
) : RecyclerView.Adapter<ProvinceAdapter.ProvinceViewHolder>() {

    class ProvinceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val cityName: TextView = itemView.findViewById(R.id.txtCityName)
        val expandBtn: ImageButton = itemView.findViewById(R.id.imgBtn)
        val uniRv : RecyclerView = itemView.findViewById(R.id.uniRv)
        val cardCity : CardView = itemView.findViewById(R.id.cardCity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProvinceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cell_city, parent, false)
        return ProvinceViewHolder(view)
    }

    override fun getItemCount(): Int = cityList.size

    override fun onBindViewHolder(holder: ProvinceViewHolder, position: Int) {
        val city = cityList[position]
        holder.cityName.text = city.province

        val universityAdapter = UniversityAdapter(city.universities, viewModel, context) {
            Log.d("ProvinceAdapter", "onBindViewHolder: ${it.name} ")
            onFavoriteClick(it)
        }
        holder.uniRv.layoutManager = LinearLayoutManager(holder.itemView.context)
        holder.uniRv.adapter = universityAdapter
        holder.uniRv.visibility = View.GONE

        holder.cardCity.setOnClickListener {
            if (holder.uniRv.visibility == View.GONE) {
                holder.uniRv.visibility = View.VISIBLE
                holder.expandBtn.setImageResource(R.drawable.baseline_remove_24)
            } else {
                holder.uniRv.visibility = View.GONE
                holder.expandBtn.setImageResource(R.drawable.baseline_add_24)
            }
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateProvinces(newProvinces: List<Province>) {
        cityList = cityList + newProvinces
        notifyDataSetChanged()
    }

}


package com.huseyinkiran.favuniversities.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favorites")
data class UniversityEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val name: String,
    @SerializedName("adress")
    val address: String,
    val email: String,
    val fax: String,
    val phone: String,
    val rector: String,
    val website: String,
)
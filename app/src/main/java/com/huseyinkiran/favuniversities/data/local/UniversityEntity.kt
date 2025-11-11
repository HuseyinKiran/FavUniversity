package com.huseyinkiran.favuniversities.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class UniversityEntity(
    @PrimaryKey val id: Int,
    val universityType: String,
    val name: String,
    val fax: String,
    val phone: String,
    val website: String,
    val address: String,
    val rector: String,
    val email: String,
    val addedAt: Long = System.currentTimeMillis()
)
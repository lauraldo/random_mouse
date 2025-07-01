package com.niolasdev.storage.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "cat")
data class CatDbo(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val catId: String,
    val url: String,
    val breeds: List<BreedDbo>? = null
)

@Serializable
class BreedDbo(
    val id: String,
    val name: String,
    val temperament: String?,
    val lifeSpan: String?,
    val description: String?,
    val origin: String?,
    val countryCode: String?,
    val weight: WeightDbo?,
    val height: HeightDbo?
)

@Serializable
class WeightDbo(
    val imperial: String?,
    val metric: String?
)

@Serializable
class HeightDbo(
    val imperial: String?,
    val metric: String?
)

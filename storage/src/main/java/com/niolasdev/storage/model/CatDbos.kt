package com.niolasdev.storage.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cat")
data class CatDbo(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val catId: String,
    val url: String,
    val breeds: List<BreedDbo>? = null
)

class BreedDbo(
    val id: String,
    val name: String,
    val temperament: String?,
    val lifeSpan: String?,
    val weight: WeightDbo?,
    val height: HeightDbo?
)

class WeightDbo(
    val imperial: String?,
    val metric: String?
)

class HeightDbo(
    val imperial: String?,
    val metric: String?
)

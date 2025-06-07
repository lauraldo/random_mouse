package com.niolasdev.storage.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cat")
data class CatDbo(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val catId: String,
    val url: String,
)

@Entity(tableName = "cats_with_breeds")
data class CatWithBreedsDbo(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val catId: Long,
    val breedId: Long,
)

@Entity(tableName = "breed")
data class BreedDbo(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo("breed_id") val breedId: String,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("temper") val temperament: String?,
    @ColumnInfo("lifespan") val lifespan: String?,
    @ColumnInfo("imperial_w") val imperialWeight: String?,
    @ColumnInfo("metric_w") val metricWeight: String?,
    @ColumnInfo("imperial_h") val imperialHeight: String?,
    @ColumnInfo("metric_h") val metricHeight: String?,
)


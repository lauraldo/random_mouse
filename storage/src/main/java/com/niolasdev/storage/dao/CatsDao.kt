package com.niolasdev.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.niolasdev.storage.model.CatDbo
import kotlinx.coroutines.flow.Flow

@Dao
interface CatsDao {

    @Query("SELECT * FROM CAT")
    suspend fun getAll(): List<CatDbo>

    @Query("SELECT * FROM CAT")
    fun observeAll(): Flow<List<CatDbo>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCat(cat: CatDbo): Long
}
package com.niolasdev.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.niolasdev.storage.model.BreedDbo
import com.niolasdev.storage.model.CatDbo
import com.niolasdev.storage.model.CatWithBreedsDbo

@Dao
interface CatsDao {

    @Transaction
    @Query("SELECT * FROM CAT")
    suspend fun getAll(): List<CatDbo>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCat(cat: CatDbo): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBreed(breed: BreedDbo): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCWB(cwb: CatWithBreedsDbo)

    @Transaction
    suspend fun insertCatWithBreeds(cat: CatDbo, breeds: List<BreedDbo>) {
        val catDbId = insertCat(cat)
        breeds.forEach { breed ->
            val breedDbId = insertBreed(breed)
            insertCWB(
                CatWithBreedsDbo(
                    catId = catDbId,
                    breedId = breedDbId,
                )
            )
        }
    }
}
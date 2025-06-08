package com.niolasdev.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.niolasdev.storage.dao.CatsDao
import com.niolasdev.storage.model.BreedConverter
import com.niolasdev.storage.model.CatDbo

@Database(entities = [CatDbo::class], version = 1)
@TypeConverters(BreedConverter::class)
internal abstract class CatsRoomDatabase : RoomDatabase() {

    abstract fun catsDao(): CatsDao
}

class CatsDatabase internal constructor(private val database: CatsRoomDatabase) {
    val catsDao: CatsDao
        get() = database.catsDao()

    fun close() {
        database.close()
    }
}

fun catsDatabase(applicationContext: Context): CatsDatabase {
    val catsRoomDatabase = Room.databaseBuilder(
        checkNotNull(applicationContext.applicationContext),
        CatsRoomDatabase::class.java,
        "cats"
    ).build()
    return CatsDatabase(catsRoomDatabase)
}
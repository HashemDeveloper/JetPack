package com.api.jetpack.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.api.jetpack.model.DogBreed

@Database(entities = [DogBreed::class], version = 1, exportSchema = false)
abstract class RoomDb: RoomDatabase() {

    companion object {
        val DB_NAME = "com.api.jetpack"
        @Volatile private var instance: RoomDb? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildRoomDatabase(context).also {
                instance = it
            }
        }
        private fun buildRoomDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            RoomDb::class.java,
            DB_NAME
        ).build()
    }

    abstract fun getIDogDao(): IDogDao
}
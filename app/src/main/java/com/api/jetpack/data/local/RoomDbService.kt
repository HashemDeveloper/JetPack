package com.api.jetpack.data.local

import android.content.Context
import javax.inject.Inject

class RoomDbService @Inject constructor(context: Context){
    private val roomDb = RoomDb.invoke(context)
    fun getDogDao(): IDogDao {
        return this.roomDb.getIDogDao()
    }
}
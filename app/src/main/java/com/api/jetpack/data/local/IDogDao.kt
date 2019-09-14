package com.api.jetpack.data.local

import androidx.room.*
import com.api.jetpack.model.DogBreed

@Dao
interface IDogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDogData(vararg dogBreed: DogBreed): List<Long>
    @Update
    suspend fun updateDogData(dogBreed: DogBreed)
    @Query("delete from dog_breed")
    suspend fun deleteAllDogData()
    @Query("select * from dog_breed where id ==:id")
    suspend fun getDogBreedsById(id: Long): DogBreed
    @Query("select * from dog_breed")
    suspend fun getAllDogBreeds(): List<DogBreed>
    @Delete
    suspend fun deleteDogBy(breed: DogBreed)
}
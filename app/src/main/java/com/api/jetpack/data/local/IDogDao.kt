package com.api.jetpack.data.local

import androidx.room.*
import com.api.jetpack.model.DogBreed
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface IDogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDogData(dogBreed: DogBreed): Long
    @Update
    fun updateDogData(dogBreed: DogBreed)
    @Delete
    fun deleteDogData(dogBreed: DogBreed)
    @Query("select * from dog_breed where dog_id ==:id")
    fun getDogBreedsById(id: String): Single<DogBreed>
    @Query("select * from dog_breed")
    fun getAllDogBreeds(): Observable<DogBreed>
}
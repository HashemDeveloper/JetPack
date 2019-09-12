package com.api.jetpack.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName = "dog_breed")
data class DogBreed(

    @ColumnInfo(name = "dog_id")
    @SerializedName("id")
    val breedId: String?,

    @ColumnInfo(name = "dog_name")
    @SerializedName("name")
    val dogBreed:String?,

    @ColumnInfo(name = "dog_life_span")
    @SerializedName("life_span")
    val lifespan: String?,

    @ColumnInfo(name = "dog_breed_group")
    @SerializedName("breed_group")
    val breedGroup: String?,

    @ColumnInfo(name = "dog_bred_for")
    @SerializedName("bred_for")
    val breedFor: String?,

    @ColumnInfo(name = "dog_temperament")
    @SerializedName("temperament")
    val temperament: String?,

    @ColumnInfo(name = "image_url")
    @SerializedName("url")
    val imageUrl: String?
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private var uuid: Long = 0

    fun getUuid(): Long {
        return this.uuid
    }
    fun setUuid(uuid: Long) {
        this.uuid = uuid
    }
}
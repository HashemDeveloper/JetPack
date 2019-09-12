package com.api.jetpack.data.local.sharedpreference

interface ISharedPrefService {
    fun saveUpdateTime(time: Long)
    fun getUpdateTime(): Long?
}
package com.api.jetpack.data.local.sharedpreference

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import javax.inject.Inject

class SharedPreferenceService @Inject constructor(): ISharedPrefService {

    companion object {
        private const val PREF_TIME = "pref_time"
        private var prefs: SharedPreferences? = null
        @Volatile private var instance: SharedPreferenceService? = null
        private val LOCK = Any()

        operator fun invoke(context: Context): SharedPreferenceService = instance ?: synchronized(LOCK) {
            instance ?: buildSharedPrefService(context).also {
                instance = it
            }
        }
        private fun buildSharedPrefService(context: Context): SharedPreferenceService {
            this.prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return SharedPreferenceService()
        }
    }

    override fun saveUpdateTime(time: Long) {
       prefs?.edit(commit = true) {
           putLong(PREF_TIME, time)
       }
    }
    override fun getUpdateTime(): Long? {
        return prefs?.getLong(PREF_TIME, 0)
    }
    override fun getCacheDuration(): String? {
        return prefs?.getString("pref_cache_id", "")
    }
}
package com.api.jetpack.view


import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat

import com.api.jetpack.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.prefrences_screen, rootKey)
    }
}

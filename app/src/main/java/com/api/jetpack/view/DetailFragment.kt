package com.api.jetpack.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

import com.api.jetpack.R
import com.api.jetpack.di.Injectable
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment(), Injectable {
    private var dogUUID = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            dogUUID = DetailFragmentArgs.fromBundle(it).dogUUID
        }
    }
}

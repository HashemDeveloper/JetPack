package com.api.jetpack.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController

import com.api.jetpack.R
import com.api.jetpack.di.Injectable
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment(), Injectable {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragment_dog_list_detail_bt_id.setOnClickListener{
            val navToDetailFragmentAction = ListFragmentDirections.actionDetailFragment()
            navToDetailFragmentAction.dogUUID = 5
            Navigation.findNavController(it).navigate(navToDetailFragmentAction)
        }
    }
}

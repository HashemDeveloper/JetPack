package com.api.jetpack.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer

import com.api.jetpack.R
import com.api.jetpack.databinding.FragmentDetailBinding
import com.api.jetpack.di.Injectable
import com.api.jetpack.di.viewmodel.ViewModelFactory
import com.api.jetpack.viewmodel.DogDetailViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class DetailFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val dogDetailViewModel: DogDetailViewModel by viewModels {
        this.viewModelFactory
    }
    private var dogUUID = 0
    private lateinit var bindingView: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AndroidSupportInjection.inject(this)
        this.bindingView = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return this.bindingView.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            dogUUID = DetailFragmentArgs.fromBundle(it).dogUUID
        }
        this.dogDetailViewModel.fetch(this.dogUUID)
        observeViewModel()
    }
    private fun observeViewModel() {
        this.dogDetailViewModel.getDogDetailLiveData().observe(this, Observer {dogBreed ->
            dogBreed.let {
                this.bindingView.dog = dogBreed
            }
        })
    }
}

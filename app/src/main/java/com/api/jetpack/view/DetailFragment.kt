package com.api.jetpack.view


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.palette.graphics.Palette

import com.api.jetpack.R
import com.api.jetpack.databinding.FragmentDetailBinding
import com.api.jetpack.di.Injectable
import com.api.jetpack.di.viewmodel.ViewModelFactory
import com.api.jetpack.model.DogPalette
import com.api.jetpack.viewmodel.DogDetailViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.Request
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.SizeReadyCallback
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
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
    private var imageUrl: String? = null

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
                this.imageUrl = it.imageUrl
                it.imageUrl?.let {
                    setupBackgroundColor(it)
                }
            }
        })
    }

    private fun setupBackgroundColor(url: String){
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object: SimpleTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource)
                        .generate { palette ->
                            val color = palette?.darkVibrantSwatch?.rgb ?: 0
                            val backgroundPalette = DogPalette(color)
                            bindingView.palette = backgroundPalette
                        }
                }

            })
    }
}


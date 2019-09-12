package com.api.jetpack.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer

import com.api.jetpack.R
import com.api.jetpack.di.Injectable
import com.api.jetpack.di.viewmodel.ViewModelFactory
import com.api.jetpack.utils.getProgressDrawable
import com.api.jetpack.utils.loadImage
import com.api.jetpack.viewmodel.DogDetailViewModel
import com.bumptech.glide.Glide
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_detail.*
import javax.inject.Inject

class DetailFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val dogDetailViewModel: DogDetailViewModel by viewModels {
        this.viewModelFactory
    }
    private var dogUUID = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AndroidSupportInjection.inject(this)
        return inflater.inflate(R.layout.fragment_detail, container, false)
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
                val imageView = fragment_detail_dog_image_view_id
                context?.let {
                    imageView.loadImage(dogBreed.imageUrl, getProgressDrawable(it))
                }
                fragment_detail_dog_name_view_id.text = dogBreed.dogBreed
                fragment_detail_dog_lifespan_view_id.text = dogBreed.lifespan
                fragment_detail_dog_purpose_id.text = dogBreed.breedGroup
                fragment_detail_dog_temperament_view_id.text = dogBreed.temperament
            }
        })
    }
}

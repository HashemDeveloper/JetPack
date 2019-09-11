package com.api.jetpack.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.api.jetpack.R
import com.api.jetpack.model.DogBreed
import com.api.jetpack.utils.getProgressDrawable
import com.api.jetpack.utils.loadImage
import kotlinx.android.synthetic.main.fragment_list_item_layout.view.*

class DogListAdapter(val dogList: ArrayList<DogBreed>) : RecyclerView.Adapter<DogListAdapter.DogListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogListViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.fragment_list_item_layout, parent, false)
        return DogListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return this.dogList.size
    }

    fun setDoglistData(dogList: List<DogBreed>) {
        this.dogList.clear()
        this.dogList.addAll(dogList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: DogListViewHolder, position: Int) {
        holder.onBindView(this.dogList[position])
    }

    class DogListViewHolder(var view: View): RecyclerView.ViewHolder(view){
        private val dogImageView = view.fragment_list_item_dog_image_view_id
        private val dogName = view.fragment_list_dog_name_view_id
        private val lifespan = view.fragment_list_item_dog_lifespan_view_id
        private val progressDrawable = getProgressDrawable(view.context)

        internal fun onBindView(dogBreed: DogBreed) {
            dogName.text = dogBreed.dogBreed
            lifespan.text = dogBreed.lifespan
            this.dogImageView.loadImage(dogBreed.imageUrl, this.progressDrawable)
        }
    }
}
package com.api.jetpack.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.api.jetpack.R
import com.api.jetpack.databinding.FragmentListItemLayoutBinding
import com.api.jetpack.model.DogBreed
import com.api.jetpack.utils.getProgressDrawable
import com.api.jetpack.utils.loadImage
import com.api.jetpack.view.ListFragmentDirections
import kotlinx.android.synthetic.main.fragment_list_item_layout.view.*

class DogListAdapter(val dogList: ArrayList<DogBreed>) :
    RecyclerView.Adapter<DogListAdapter.DogListViewHolder>(), DogClickListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogListViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<FragmentListItemLayoutBinding>(
            layoutInflater,
            R.layout.fragment_list_item_layout,
            parent,
            false
        )
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
        holder.view.dog = this.dogList[position]
        holder.view.listener = this
    }

    override fun onDogItemClicked(v: View) {
        val uuid = v.dogUuId.text.toString().toInt()
        val action = ListFragmentDirections.actionDetailFragment()
        action.dogUUID = uuid
        Navigation.findNavController(v).navigate(action)
    }

    class DogListViewHolder(var view: FragmentListItemLayoutBinding) :
        RecyclerView.ViewHolder(view.root) {}
}
package com.api.jetpack.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.api.jetpack.R
import com.api.jetpack.di.Injectable
import com.api.jetpack.view.adapters.DogListAdapter
import com.api.jetpack.viewmodel.DogListViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject

class ListFragment : Fragment(), Injectable {

    private val dogListAdapter = DogListAdapter(arrayListOf())

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val dogListViewModel: DogListViewModel by viewModels {
        this.viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AndroidSupportInjection.inject(this)
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.dogListViewModel.refresh()
        fragment_list_recycler_view_id.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dogListAdapter
        }
        fragment_list_refresh_view_id.setOnRefreshListener {
            fragment_list_recycler_view_id.visibility = View.GONE
            fragment_list_error_msg_view_id.visibility = View.GONE
            fragment_list_progress_bar_id.visibility = View.VISIBLE
            this.dogListViewModel.refreshBypassCache()
            fragment_list_refresh_view_id.isRefreshing = false
        }
        observeViewModel()
    }
    private fun observeViewModel() {
        this.dogListViewModel.getDogListLiveData().observe(this, Observer { dogList ->
            dogList.let {
                fragment_list_recycler_view_id.visibility = View.VISIBLE
                this.dogListAdapter.setDoglistData(it)
            }
        })
        this.dogListViewModel.getIsError().observe(this, Observer { isError ->
            isError.let {
                fragment_list_error_msg_view_id.visibility = if (it) View.VISIBLE else View.GONE
            }
        })
        this.dogListViewModel.getIsLoading().observe(this, Observer { isLoading ->
            isLoading.let {
                fragment_list_progress_bar_id.visibility = if(it) View.VISIBLE else View.GONE
                if (it) {
                    fragment_list_error_msg_view_id.visibility = View.GONE
                    fragment_list_recycler_view_id.visibility = View.GONE
                }
            }
        })
    }
}

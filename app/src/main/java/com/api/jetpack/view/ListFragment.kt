package com.api.jetpack.view


import android.opengl.Visibility
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager

import com.api.jetpack.R
import com.api.jetpack.databinding.FragmentListBinding
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
    lateinit var fragmentListBinding: FragmentListBinding

    private val dogListViewModel: DogListViewModel by viewModels {
        this.viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AndroidSupportInjection.inject(this)
        setHasOptionsMenu(true)
        this.fragmentListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
        return this.fragmentListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.dogListViewModel.refresh()
        this.fragmentListBinding.fragmentListRecyclerViewId.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dogListAdapter
        }
        this.fragmentListBinding.fragmentListRefreshViewId.setOnRefreshListener {
            this.fragmentListBinding.fragmentListRecyclerViewId.visibility = View.GONE
            this.fragmentListBinding.fragmentListErrorMsgViewId.visibility = View.GONE
            this.fragmentListBinding.fragmentListProgressBarId.visibility = View.VISIBLE
            this.dogListViewModel.refreshBypassCache()
            this.fragmentListBinding.fragmentListRefreshViewId.isRefreshing = false
        }

        observeViewModel()
    }
    private fun observeViewModel() {
        this.dogListViewModel.getDogListLiveData().observe(this, Observer { dogList ->
            dogList.let {
                this.fragmentListBinding.fragmentListRecyclerViewId.visibility = View.VISIBLE
                this.dogListAdapter.setDoglistData(it)
            }
        })
        this.dogListViewModel.getIsError().observe(this, Observer { isError ->
            isError.let {
                this.fragmentListBinding.fragmentListErrorMsgViewId.visibility = if (it) View.VISIBLE else View.GONE
            }
        })
        this.dogListViewModel.getIsLoading().observe(this, Observer { isLoading ->
            isLoading.let {
                this.fragmentListBinding.fragmentListProgressBarId.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    fragmentListBinding.fragmentListErrorMsgViewId.visibility = View.GONE
                    this.fragmentListBinding.fragmentListRecyclerViewId.visibility = View.GONE
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.list_fragment_menu_id ->{
                view?.let {
                    Navigation.findNavController(it).navigate(ListFragmentDirections.actionSettingFragment())
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

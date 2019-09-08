package com.api.jetpack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class DogActivity : AppCompatActivity(), HasSupportFragmentInjector{
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dog_activity_main_layout)
        this.navController = Navigation.findNavController(this, R.id.container)
        NavigationUI.setupActionBarWithNavController(this, this.navController)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return this.dispatchingAndroidInjector
    }

    override fun onSupportNavigateUp(): Boolean {
       return NavigationUI.navigateUp(this.navController, null)
    }
}

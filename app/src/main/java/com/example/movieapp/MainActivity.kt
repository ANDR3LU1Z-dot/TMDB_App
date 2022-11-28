package com.example.movieapp

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.movieapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var bindingMain: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var searchView: View
    private val viewModel by viewModels<DataStateViewModel> { defaultViewModelProviderFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingMain = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMain.root)

        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        navController = navHost.navController

        searchView = bindingMain.searchView


        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.movieDetailsFragment -> searchView.visibility = View.GONE
                else -> searchView.visibility = View.VISIBLE
            }

        }

        initObservers()
    }

    private fun initObservers(){
        viewModel.dataStateLiveData.observe(this, Observer{
            when(it){
                DataState.Error -> println("Error")
                DataState.Loading -> println("Loading")
                else -> println("Success")
            }
        })
    }

    override fun onNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}
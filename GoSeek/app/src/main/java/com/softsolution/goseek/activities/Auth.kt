package com.softsolution.goseek.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.softsolution.goseek.Interface.CallFragmentInterface
import com.softsolution.goseek.R
import com.softsolution.goseek.base.BaseActivity
import com.softsolution.goseek.databinding.ActivityAuthBinding
import com.softsolution.goseek.utils.Constants

class Auth : BaseActivity(), CallFragmentInterface {
    private lateinit var binding: ActivityAuthBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_auth)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHostFragment.navController
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.auth_nav)
        if (Constants.login) {
            graph.startDestination = R.id.loginFragment2
            navHostFragment.navController.graph = graph
        } else {
            graph.startDestination = R.id.optionsFragment2
            navHostFragment.navController.graph = graph
        }
    }

    override fun passFragmentCallback(name: String) {
    }
}
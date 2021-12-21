package com.softsolution.goseek.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.softsolution.goseek.Interface.CallFragmentInterface
import com.softsolution.goseek.R
import com.softsolution.goseek.base.BaseActivity
import com.softsolution.goseek.databinding.ActivityEmployerDashboardBinding

class EmployerDashboard : BaseActivity(),CallFragmentInterface{
    private lateinit var binding: ActivityEmployerDashboardBinding
   private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_employer_dashboard)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentEmployerContainer) as NavHostFragment
        navController = navHostFragment.navController

    }

    override fun passFragmentCallback(name: String) {

    }
}
package com.softsolution.goseek.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.softsolution.goseek.Interface.CallFragmentInterface
import com.softsolution.goseek.R
import com.softsolution.goseek.base.BaseActivity
import com.softsolution.goseek.databinding.ActivityUserDashboardBinding
import com.softsolution.goseek.utils.Constants.Companion.login

class UserDashboard : BaseActivity(), CallFragmentInterface {
    private lateinit var binding: ActivityUserDashboardBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_dashboard)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentUserContainer) as NavHostFragment
        navController = navHostFragment.navController

    }

    override fun passFragmentCallback(name: String) {

    }
}
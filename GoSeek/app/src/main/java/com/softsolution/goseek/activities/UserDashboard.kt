package com.softsolution.goseek.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.softsolution.goseek.Interface.CallFragmentInterface
import com.softsolution.goseek.R
import com.softsolution.goseek.base.BaseActivity
import com.softsolution.goseek.databinding.ActivityUserDashboardBinding

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
        when (name) {

            "profile" -> {
                navController.navigate(R.id.action_baseDashbordFragment3_to_myProfileFragment2)
            }
            "uploadResume" -> {
                navController.navigate(R.id.action_baseDashbordFragment3_to_uploadResumeFragment2)
            }
            "location" -> {
                navController.navigate(R.id.action_baseDashbordFragment3_to_mapsFragment)
            }
            "JobDescription" -> {
                navController.navigate(R.id.action_baseDashbordFragment3_to_jobDetailFragment2)
            }
            "changePassword" -> {
                navController.navigate(R.id.action_baseDashbordFragment3_to_forgetPasswordFragment2)
            }
        }
    }
}
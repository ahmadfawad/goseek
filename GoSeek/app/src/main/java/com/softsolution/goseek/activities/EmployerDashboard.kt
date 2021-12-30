package com.softsolution.goseek.activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.softsolution.goseek.Interface.CallFragmentInterface
import com.softsolution.goseek.R
import com.softsolution.goseek.base.BaseActivity
import com.softsolution.goseek.databinding.ActivityEmployerDashboardBinding

class EmployerDashboard : BaseActivity(), CallFragmentInterface {
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
        when (name) {
            "editCompanyprofile" -> {
                navController.navigate(R.id.action_posterBaseDashbordFragment2_to_editCompanyProfileFragment2)
            }
            "postedJobDetail" -> {
//                navController.navigate(R.id.action_posterBaseDashbordFragment2_to_companyJobDetail)
            }
            "changePassword" -> {
                navController.navigate(R.id.action_posterBaseDashbordFragment2_to_forgetPasswordFragment3)
            }
            "review" -> {
                navController.navigate(R.id.action_posterBaseDashbordFragment2_to_reviewFragment2)
            }
        }
    }

//    private var doubleBackToExitPressedOnce = false
//    override fun onBackPressed() {
//        if (doubleBackToExitPressedOnce) {
//            super.onBackPressed()
//            return
//        }
//
//        this.doubleBackToExitPressedOnce = true
//        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()
//
//        Handler(Looper.getMainLooper()).postDelayed(Runnable {
//            doubleBackToExitPressedOnce = false
//        }, 2000)
//    }
}
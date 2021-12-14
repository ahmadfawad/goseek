package com.softsolution.goseek.activities

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.softsolution.goseek.Interface.CallFragmentInterface
import com.softsolution.goseek.R
import com.softsolution.goseek.base.BaseActivity
import dev.sasikanth.colorsheet.ColorSheet


class MainActivity : BaseActivity(),CallFragmentInterface {



    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            val w: Window = window
//            w.setFlags(
//                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
//            )
//        }
    }


        // In Activity's onCreate() for instance
        // In Activity's onCreate() for instance

   /*     val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout= findViewById(R.id.drawerLayout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        appBarConfiguration = AppBarConfiguration(
                navController.graph, drawerLayout)

        setupActionBarWithNavController(navController, appBarConfiguration)
       navView.setupWithNavController(navController) */



    override fun passFragmentCallback(name: String) {
        when (name) {
            "profile" -> {
                navController.navigate(R.id.action_baseDashbordFragment_to_myProfileFragment)
            }
            "uploadResume" -> {
                navController.navigate(R.id.action_baseDashbordFragment_to_uploadResumeFragment)
            }
            "location" -> {
                navController.navigate(R.id.action_baseDashbordFragment_to_mapsFragment3)
            }
            "JobDescription" -> {
                navController.navigate(R.id.action_baseDashbordFragment_to_jobDetailFragment)
            }
            "changePassword" -> {
                navController.navigate(R.id.action_baseDashbordFragment_to_forgetPasswordFragment)
            }
            "login" -> {
                navController.navigate(R.id.action_baseDashbordFragment_to_loginFragment)
            }
            "register" -> {
                navController.navigate(R.id.action_baseDashbordFragment_to_optionsFragment)
            }
            "editCompanyprofile" -> {
                navController.navigate(R.id.action_posterBaseDashbordFragment_to_editCompanyProfileFragment)
            }
            "newJobSeekerDetail" -> {
                navController.navigate(R.id.action_posterBaseDashbordFragment_to_sucessfullPostedFragment)
            }
            "review" -> {
                navController.navigate(R.id.action_posterBaseDashbordFragment_to_reviewFragment)
            }
            "editPassword" -> {
                navController.navigate(R.id.action_posterBaseDashbordFragment_to_forgetPasswordFragment)
            }
            "changeLocation" -> {
                navController.navigate(R.id.action_posterBaseDashbordFragment_to_editLocationFragment)
            }
            "loginPoster" -> {
                navController.navigate(R.id.action_posterBaseDashbordFragment_to_loginFragment)
            }
            "registerPoster" -> {
                navController.navigate(R.id.action_posterBaseDashbordFragment_to_optionsFragment2)
            }
            "postedJobDetail" -> {
                navController.navigate(R.id.action_posterBaseDashbordFragment_to_posterJobDetailFragment)
            }
            "Job" -> {
                navController.navigate(R.id.action_posterJobDetailFragment_to_jobSeekerDetailFragment)
            }
        }
    }

  /*  override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    } */

    override fun onResume() {
        super.onResume()
        FullScreencall()
    }

    fun FullScreencall() {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            val v = this.window.decorView
            v.systemUiVisibility = View.GONE
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            val decorView = window.decorView
            val uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            decorView.systemUiVisibility = uiOptions
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }



}
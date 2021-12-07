package com.softsolution.goseek.fragments.jobPoster

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ShareCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.slider.RangeSlider
import com.softsolution.goseek.Interface.CallFragmentInterface
import com.softsolution.goseek.R
import com.softsolution.goseek.databinding.FragmentPosterBaseDashbordBinding
import com.softsolution.goseek.utils.Constants
import java.text.NumberFormat
import java.util.*

class PosterBaseDashbordFragment : Fragment()  {

    private var binding: FragmentPosterBaseDashbordBinding? = null
    var listener: CallFragmentInterface? = null
    var fragment: Fragment? = null
    var recyclerView: RecyclerView? = null
    var toolbar: Toolbar? = null
    lateinit var button: MaterialButton
    var drawerLayout: DrawerLayout? = null
    var navController: NavController? = null
    var navigationView: NavigationView? = null
    var check = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_poster_base_dashbord,
            container,
            false
        )
        (activity as AppCompatActivity?)!!.setSupportActionBar(binding!!.appbarlayouttoolbar)

        initData()
        initDrawerLayout()

        return binding!!.getRoot()
    }

    private fun initDrawerLayout() {


        if (Constants.login == true){
            var headerView = binding!!.navView.inflateHeaderView(R.layout.nav_header_main)
            headerView.findViewById<View>(R.id.nav_header_main)
            button=headerView.findViewById(R.id.back)

            button.setOnClickListener {
                binding!!.drawerLayout.closeDrawer(GravityCompat.START)
            }

        }else{
            var headerView = binding!!.navView.inflateHeaderView(R.layout.nav_header_sign_in)
            headerView.findViewById<View>(R.id.nav_header_sign_in)
            val menu: Menu = binding!!.navView.menu
            menu.findItem(R.id.allJob).setTitle("Register").setIcon(R.drawable.ic_register)
            menu.findItem(R.id.addNewJob).setTitle("Login").setIcon(R.drawable.ic_login)
            button=headerView.findViewById(R.id.back)

            button.setOnClickListener {
                binding!!.drawerLayout.closeDrawer(GravityCompat.START)
            }
        }


        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            requireActivity(),
            binding!!.drawerLayout,
            binding!!.appbarlayouttoolbar,
            R.string.drawer_open,
            R.string.drawer_close
        ){
            override fun onDrawerClosed(view: View){
                super.onDrawerClosed(view)
                //toast("Drawer closed")
            }

            override fun onDrawerOpened(drawerView: View){
                super.onDrawerOpened(drawerView)
                //toast("Drawer opened")
            }
        }


        // Configure the drawer layout to add listener and show icon on toolbar
        drawerToggle.isDrawerIndicatorEnabled = false
        val drawable = ResourcesCompat.getDrawable(resources, R.drawable.ic_group_1108, requireActivity().theme)
        drawerToggle.setHomeAsUpIndicator(drawable)

        binding!!.drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        //or you can add icon

        drawerToggle.setToolbarNavigationClickListener(View.OnClickListener {
            val drawer = binding!!.drawerLayout
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START)
            } else {
                drawer.openDrawer(GravityCompat.START)
            }
        })


        val firstFragment = PostedDashbordFragment()
        val secondFragment = NewJobFragment()

        binding!!.navView.setItemIconTintList(null)

        binding!!.navView.setNavigationItemSelectedListener{
            when (it.itemId){
                R.id.allJob -> {
                    binding!!.heading.text="All Jobs"
                    if (Constants.login == true) {
                        setCurrentFragment(firstFragment)
                        //          binding!!.filter.visibility=View.GONE
                        binding!!.viewDashbord.setVisibility(View.VISIBLE)
                        binding!!.viewApplied.setVisibility(View.INVISIBLE)
                        binding!!.viewFav.setVisibility(View.INVISIBLE)

                        binding!!.bottomNavView.menu.getItem(0).setChecked(true)
                    } else {

                        listener?.passFragmentCallback("registerPoster")
                    }
                }
                R.id.addNewJob -> {
                    binding!!.heading.text="New Job"
                    if (Constants.login == true) {
                        setCurrentFragment(secondFragment)
                        //             binding!!.filter.visibility=View.GONE
                        binding!!.viewDashbord.setVisibility(View.INVISIBLE)
                        binding!!.viewApplied.setVisibility(View.INVISIBLE)
                        binding!!.viewFav.setVisibility(View.VISIBLE)

                        binding!!.bottomNavView.menu.getItem(1).setChecked(true)
                    } else {
                        listener?.passFragmentCallback("loginPoster")
                    }
                }
                R.id.shareApp -> {
                    ShareCompat.IntentBuilder.from(requireActivity())
                        .setType("text/plain")
                        .setChooserTitle("Go Seek")
                        .setText("http://play.google.com/store/apps/details?id=" + requireActivity().getPackageName())
                        .startChooser();
                }
                R.id.rateApp -> {
                    try {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + requireActivity().getPackageName())))
                    } catch (e: ActivityNotFoundException) {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + requireActivity().getPackageName())))
                    }

                }

            }
            // Close the drawer
            binding!!.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }


    }

    private fun initData() {

        binding!!.bottomNavView.setItemIconTintList(null)
        binding!!.viewDashbord.setVisibility(View.VISIBLE)


        val firstFragment = PostedDashbordFragment()
        val secondFragment = NewJobFragment()
 //       val thirdFragment = LocationFragment()
        val fourthFragment = PostedProfileFragment()

        setCurrentFragment(firstFragment)

        binding!!.bottomNavView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.dashbordFragment -> {
                    setCurrentFragment(firstFragment)
                    binding!!.heading.text = "All Jobs"
                    //      binding!!.filter.visibility=View.VISIBLE
                    binding!!.viewDashbord.setVisibility(View.VISIBLE)
                    binding!!.viewApplied.setVisibility(View.INVISIBLE)
                    binding!!.viewFav.setVisibility(View.INVISIBLE)

                }
                R.id.favouriteFragment -> {
                    setCurrentFragment(secondFragment)
                    binding!!.heading.text = "New Job"
                    //      binding!!.filter.visibility=View.GONE
                    binding!!.viewDashbord.setVisibility(View.INVISIBLE)
                    binding!!.viewApplied.setVisibility(View.INVISIBLE)
                    binding!!.viewFav.setVisibility(View.VISIBLE)

                }
 /*               R.id.appliedFragment -> {
                    setCurrentFragment(thirdFragment)
                    //    binding!!.filter.visibility=View.GONE
                    binding!!.viewDashbord.setVisibility(View.INVISIBLE)
                    binding!!.viewApplied.setVisibility(View.VISIBLE)
                    binding!!.viewFav.setVisibility(View.INVISIBLE)
                    binding!!.viewProfile.setVisibility(View.INVISIBLE)
                }  */
                R.id.profileFragment -> {
                    setCurrentFragment(fourthFragment)
                    binding!!.heading.text = "Profile"
                    //  binding!!.filter.visibility=View.GONE
                    binding!!.viewDashbord.setVisibility(View.INVISIBLE)
                    binding!!.viewApplied.setVisibility(View.VISIBLE)
                    binding!!.viewFav.setVisibility(View.INVISIBLE)
                }
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment)=
        childFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, fragment)
            commit()
        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as CallFragmentInterface
    }


    override fun onResume() {
        super.onResume()
      //  binding!!.bottomNavView.getMenu().getItem(0).setChecked(true)
        val fourthFragment = PostedProfileFragment()
        if(binding!!.bottomNavView.selectedItemId==R.id.profileFragment){
            setCurrentFragment(fourthFragment)
            binding!!.heading.text = "Profile"
            //  binding!!.filter.visibility=View.GONE
            binding!!.viewDashbord.setVisibility(View.INVISIBLE)
            binding!!.viewApplied.setVisibility(View.VISIBLE)
            binding!!.viewFav.setVisibility(View.INVISIBLE)
        }

    }


}
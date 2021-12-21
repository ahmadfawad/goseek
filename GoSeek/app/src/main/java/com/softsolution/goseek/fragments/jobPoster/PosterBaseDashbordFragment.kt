package com.softsolution.goseek.fragments.jobPoster

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ShareCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.slider.RangeSlider
import com.google.android.material.tabs.TabLayout
import com.softsolution.goseek.Interface.CallFragmentInterface
import com.softsolution.goseek.R
import com.softsolution.goseek.activities.Auth
import com.softsolution.goseek.adapter.PageAdapter
import com.softsolution.goseek.adapter.jobSeekerAdapter.FilterButtonAdapter
import com.softsolution.goseek.base.BaseFragment
import com.softsolution.goseek.databinding.FragmentPosterBaseDashbordBinding
import com.softsolution.goseek.model.jobSeekerModel.FilterButtonData
import com.softsolution.goseek.network.LocalPreference
import com.softsolution.goseek.utils.Constants
import java.text.NumberFormat
import java.util.*

class PosterBaseDashbordFragment : BaseFragment() {

    private var binding: FragmentPosterBaseDashbordBinding? = null
    var listener: CallFragmentInterface? = null
    private var adapter: FilterButtonAdapter? = null
    var fragment: Fragment? = null
    var rangeSlider: RangeSlider? = null
    var recyclerView: RecyclerView? = null
    var toolbar: Toolbar? = null
    lateinit var button: MaterialButton
    val buttonList = ArrayList<FilterButtonData>()
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

//        initData()
        initDrawerLayout()


        binding!!.drawerLayout.setScrimColor(resources.getColor(R.color.white_trans))


        binding!!.frameLayout.adapter = PageAdapter(childFragmentManager)
        binding!!.tabLayout.setupWithViewPager(binding!!.frameLayout)

        binding!!.tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_alljob_red)
        binding!!.tabLayout.getTabAt(1)?.setIcon(R.drawable.ic_newjob_grey)
        binding!!.tabLayout.getTabAt(2)?.setIcon(R.drawable.user_grey)

        binding!!.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        tab.setIcon(R.drawable.ic_alljob_red)
                        binding!!.tabLayout.getTabAt(1)?.setIcon(R.drawable.ic_heart_grey)
                        binding!!.tabLayout.getTabAt(2)?.setIcon(R.drawable.ic_user)
                    }
                    1 -> {
                        tab.setIcon(R.drawable.ic_newjob_red)
                        binding!!.tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_alljob_grey)
                        binding!!.tabLayout.getTabAt(2)?.setIcon(R.drawable.ic_user)
                    }
                    2 -> {
                        tab.setIcon(R.drawable.ic_user_red)
                        binding!!.tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_alljob_grey)
                        binding!!.tabLayout.getTabAt(1)?.setIcon(R.drawable.ic_newjob_grey)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        return binding!!.root
    }

    private fun initDrawerLayout() {


        if (LocalPreference.shared.isLogin) {
            var headerView = binding!!.navView.inflateHeaderView(R.layout.nav_header_main)
            headerView.findViewById<View>(R.id.nav_header_main)
            button = headerView.findViewById(R.id.back)
            var name = headerView.findViewById<TextView>(R.id.tv_name)
            name.text = LocalPreference.shared.user?.Name
            button.setOnClickListener {
                binding!!.drawerLayout.closeDrawer(GravityCompat.START)
            }

        } else {
            var headerView = binding!!.navView.inflateHeaderView(R.layout.nav_header_sign_in)
            headerView.findViewById<View>(R.id.nav_header_sign_in)
            val menu: Menu = binding!!.navView.menu
            menu.findItem(R.id.allJob).setTitle("Register").setIcon(R.drawable.ic_register)
            menu.findItem(R.id.addNewJob).setTitle("Login").setIcon(R.drawable.ic_login)
            button = headerView.findViewById(R.id.back)

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
        ) {
            override fun onDrawerClosed(view: View) {
                super.onDrawerClosed(view)
                //toast("Drawer closed")
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                //toast("Drawer opened")
            }
        }


        // Configure the drawer layout to add listener and show icon on toolbar
        drawerToggle.isDrawerIndicatorEnabled = false
        val drawable = ResourcesCompat.getDrawable(
            resources,
            R.drawable.ic_group_1108,
            requireActivity().theme
        )
        drawerToggle.setHomeAsUpIndicator(drawable)

        binding!!.drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        //or you can add icon

        drawerToggle.setToolbarNavigationClickListener {
            val drawer = binding!!.drawerLayout
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START)
            } else {
                drawer.openDrawer(GravityCompat.START)
            }
        }


        val firstFragment = PostedDashbordFragment()
        val secondFragment = NewJobFragment()

        binding!!.navView.itemIconTintList = null

        binding!!.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.allJob -> {
                    if (LocalPreference.shared.isLogin) {
                        // setCurrentFragment(secondFragment)
                        binding!!.tabLayout.getTabAt(1)?.select()
                        binding!!.frameLayout.currentItem = 1
                        binding!!.filter.visibility = View.GONE
                        /*    binding!!.viewDashbord.setVisibility(View.INVISIBLE)
                            binding!!.viewApplied.setVisibility(View.INVISIBLE)
                            binding!!.viewFav.setVisibility(View.VISIBLE)
                            binding!!.viewProfile.setVisibility(View.INVISIBLE)*/
                        //            binding!!.bottomNavView.menu.getItem(1).setChecked(true)
                    } else {

                        listener?.passFragmentCallback("login")
                    }
                }
                R.id.addNewJob -> {
                    if (LocalPreference.shared.isLogin) {
                        //setCurrentFragment(thirdFragment)
                        binding!!.tabLayout.getTabAt(2)?.select()
                        binding!!.frameLayout.currentItem = 2
                        binding!!.filter.visibility = View.GONE
                        /* binding!!.viewDashbord.setVisibility(View.INVISIBLE)
                         binding!!.viewApplied.setVisibility(View.VISIBLE)
                         binding!!.viewFav.setVisibility(View.INVISIBLE)
                         binding!!.viewProfile.setVisibility(View.INVISIBLE)*/
                        //          binding!!.bottomNavView.menu.getItem(2).setChecked(true)
                    } else {
                        Constants.login = true
                        startActivity(Intent(mActivity, Auth::class.java))
                    }
                }
                R.id.shareApp -> {
                    ShareCompat.IntentBuilder.from(requireActivity())
                        .setType("text/plain")
                        .setChooserTitle("Go Seek")
                        .setText("http://play.google.com/store/apps/details?id=" + requireActivity().packageName)
                        .startChooser()
                }
                R.id.rateApp -> {
                    try {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("market://details?id=" + requireActivity().packageName)
                            )
                        )
                    } catch (e: ActivityNotFoundException) {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=" + requireActivity().packageName)
                            )
                        )
                    }

                }

            }
            // Close the drawer
            binding!!.drawerLayout.closeDrawer(GravityCompat.START)
            false
        }


    }

//    private fun initData() {
//
//        binding!!.bottomNavView.itemIconTintList = null
//        binding!!.viewDashbord.visibility = View.VISIBLE
//
//
//        val firstFragment = PostedDashbordFragment()
//        val secondFragment = NewJobFragment()
//        //       val thirdFragment = LocationFragment()
//        val fourthFragment = PostedProfileFragment()
//
//        setCurrentFragment(firstFragment)
//
//        binding!!.bottomNavView.setOnNavigationItemSelectedListener {
//            when (it.itemId) {
//                R.id.dashbordFragment -> {
//                    setCurrentFragment(firstFragment)
//                    binding!!.heading.text = "All Jobs"
//                    //      binding!!.filter.visibility=View.VISIBLE
//                    binding!!.viewDashbord.visibility = View.VISIBLE
//                    binding!!.viewApplied.visibility = View.INVISIBLE
//                    binding!!.viewFav.visibility = View.INVISIBLE
//
//                }
//                R.id.favouriteFragment -> {
//                    setCurrentFragment(secondFragment)
//                    binding!!.heading.text = "New Job"
//                    //      binding!!.filter.visibility=View.GONE
//                    binding!!.viewDashbord.visibility = View.INVISIBLE
//                    binding!!.viewApplied.visibility = View.INVISIBLE
//                    binding!!.viewFav.visibility = View.VISIBLE
//
//                }
//                /*               R.id.appliedFragment -> {
//                                   setCurrentFragment(thirdFragment)
//                                   //    binding!!.filter.visibility=View.GONE
//                                   binding!!.viewDashbord.setVisibility(View.INVISIBLE)
//                                   binding!!.viewApplied.setVisibility(View.VISIBLE)
//                                   binding!!.viewFav.setVisibility(View.INVISIBLE)
//                                   binding!!.viewProfile.setVisibility(View.INVISIBLE)
//                               }  */
//                R.id.profileFragment -> {
//                    setCurrentFragment(fourthFragment)
//                    binding!!.heading.text = "Profile"
//                    //  binding!!.filter.visibility=View.GONE
//                    binding!!.viewDashbord.visibility = View.INVISIBLE
//                    binding!!.viewApplied.visibility = View.VISIBLE
//                    binding!!.viewFav.visibility = View.INVISIBLE
//                }
//            }
//            true
//        }
//    }

    private fun setCurrentFragment(fragment: Fragment) =
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
//        val fourthFragment = PostedProfileFragment()
//        if (binding!!.bottomNavView.selectedItemId == R.id.profileFragment) {
//            setCurrentFragment(fourthFragment)
//            binding!!.heading.text = "Profile"
//            //  binding!!.filter.visibility=View.GONE
//            binding!!.viewDashbord.visibility = View.INVISIBLE
//            binding!!.viewApplied.visibility = View.VISIBLE
//            binding!!.viewFav.visibility = View.INVISIBLE
//        }

    }
    private fun loadData() {
        buttonList.clear()
        for (i in 0..6) {
            buttonList.add(FilterButtonData("10 km"))
        }
    }

    private fun showBottomSheetDialog() {
        val view: View = layoutInflater.inflate(R.layout.flter_screen_dialogue, null)
        val dialog = BottomSheetDialog(requireActivity(), R.style.DialogCustomTheme)
        dialog.setContentView(view)
        rangeSlider = dialog.findViewById(R.id.rangeSlider)
        rangeSlider!!.setLabelFormatter { value ->
            val currencyFormat = NumberFormat.getCurrencyInstance()
            currencyFormat.currency = Currency.getInstance("USD")
            currencyFormat.format(value.toDouble())
        }
        loadData()
        recyclerView = view.findViewById(R.id.recyclerView)
        adapter = FilterButtonAdapter(buttonList, requireActivity())
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.layoutManager = GridLayoutManager(requireActivity(), 4)
        recyclerView!!.adapter = adapter
        dialog.show()
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        listener = context as CallFragmentInterface
    }


}
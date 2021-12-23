package com.softsolution.goseek.fragments.jobSeeker

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
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ShareCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.slider.RangeSlider
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.softsolution.goseek.Interface.CallFragmentInterface
import com.softsolution.goseek.R
import com.softsolution.goseek.activities.Auth
import com.softsolution.goseek.adapter.PageAdapter
import com.softsolution.goseek.adapter.jobSeekerAdapter.FilterButtonAdapter
import com.softsolution.goseek.base.BaseFragment
import com.softsolution.goseek.databinding.FragmentBaseDashbordBinding
import com.softsolution.goseek.model.jobSeekerModel.FilterButtonData
import com.softsolution.goseek.network.LocalPreference
import com.softsolution.goseek.utils.Constants.Companion.login
import java.text.NumberFormat
import java.util.*


class BaseDashbordFragment : BaseFragment()
//    ,NavigationView.OnNavigationItemSelectedListener
{
    private var binding: FragmentBaseDashbordBinding? = null
    var listener: CallFragmentInterface? = null
    var fragment: Fragment? = null
    var recyclerView: RecyclerView? = null
    var toolbar: Toolbar? = null
    val buttonList = ArrayList<FilterButtonData>()
    private var adapter: FilterButtonAdapter? = null
    var rangeSlider: RangeSlider? = null
    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var button: MaterialButton
    var drawerLayout: DrawerLayout? = null
    var navController: NavController? = null
    var navigationView: NavigationView? = null
    var check = false
    // private lateinit var listener:NavController.OnDestinationChangedListener


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_base_dashbord,
            container,
            false
        )


//        (activity as AppCompatActivity?)?.setSupportActionBar(binding!!.appbarlayouttoolbar)
        //   val actionBar = this.requireActivity().actionBar
        //   actionBar?.setDisplayHomeAsUpEnabled(false)
        //   actionBar?.setDisplayShowHomeEnabled(false)
        //   binding!!.appbarlayouttoolbar.setContentInsetStartWithNavigation(0)
        //   binding!!.appbarlayouttoolbar.setNavigationIcon(R.drawable.ic_group_1108)


        //else{
        //       binding!!.drawerLayout.visibility=View.VISIBLE
        //       binding!!.navView.visibility=View.GONE
        //       binding!!.navViewSignIn.visibility=View.VISIBLE
        //       initDrawerLayoutSignIn()
        //   }


        //   binding!!.drawer.setOnClickListener{

        //     }

        binding!!.filter.setOnClickListener {
            showBottomSheetDialog()
        }

        //  binding!!.bottomNavView.setItemIconTintList(null)

        //    val nestedNavHostFragment = childFragmentManager.findFragmentById(R.id.fragment_dashbord) as? NavHostFragment
        /*   val navController = nestedNavHostFragment?.navController
           appBarConfiguration = AppBarConfiguration(navController!!.graph, binding!!.drawerLayout)
           binding!!.appbarlayouttoolbar.setupWithNavController(navController, appBarConfiguration)
           binding!!.appbarlayouttoolbar.setNavigationIcon(R.drawable.ic_group_1108)

           val actionBar = this.requireActivity().actionBar
           actionBar?.setDisplayHomeAsUpEnabled(false)
           actionBar?.setDisplayShowHomeEnabled(false)
   */
        //     initNavigation()
        initData()
        initDrawerLayout()
        //   initDrawerLayout()


        /*  listener=NavController.OnDestinationChangedListener{ controller, destination, arguments ->
              if (destination.id == R.id.dashbordFragment){
                  binding!!.drawerLayout.close()
                  navController.navigate(R.id.action_baseDashbordFragment_to_editLocationFragment)
              }
          }*/

        binding!!.drawerLayout.setScrimColor(resources.getColor(R.color.white_trans))


        binding!!.frameLayout.adapter = PageAdapter(childFragmentManager)
        binding!!.tabLayout.setupWithViewPager(binding!!.frameLayout)

        binding!!.tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_home_red)
        binding!!.tabLayout.getTabAt(1)?.setIcon(R.drawable.ic_heart_grey)
        binding!!.tabLayout.getTabAt(2)?.setIcon(R.drawable.ic_applied)
        binding!!.tabLayout.getTabAt(3)?.setIcon(R.drawable.ic_user)

        binding!!.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        tab.setIcon(R.drawable.ic_home_red)
                        binding!!.tabLayout.getTabAt(1)?.setIcon(R.drawable.ic_heart_grey)
                        binding!!.tabLayout.getTabAt(2)?.setIcon(R.drawable.ic_applied)
                        binding!!.tabLayout.getTabAt(3)?.setIcon(R.drawable.ic_user)
                    }
                    1 -> {
                        tab.setIcon(R.drawable.ic_fill_heart)
                        binding!!.tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_house)
                        binding!!.tabLayout.getTabAt(2)?.setIcon(R.drawable.ic_applied)
                        binding!!.tabLayout.getTabAt(3)?.setIcon(R.drawable.ic_user)
                    }
                    2 -> {
                        tab.setIcon(R.drawable.ic_applied_red)
                        binding!!.tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_house)
                        binding!!.tabLayout.getTabAt(1)?.setIcon(R.drawable.ic_heart_grey)
                        binding!!.tabLayout.getTabAt(3)?.setIcon(R.drawable.ic_user)
                    }
                    3 -> {
                        tab.setIcon(R.drawable.ic_user_red)
                        binding!!.tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_house)
                        binding!!.tabLayout.getTabAt(1)?.setIcon(R.drawable.ic_heart_grey)
                        binding!!.tabLayout.getTabAt(2)?.setIcon(R.drawable.ic_applied)
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
            var name = headerView.findViewById<TextView>(R.id.tv_name)
            button = headerView.findViewById(R.id.back)
            name.text = LocalPreference.shared.user?.Name

            button.setOnClickListener {
                binding!!.drawerLayout.closeDrawer(GravityCompat.START)
            }

        } else {
            var headerView = binding!!.navView.inflateHeaderView(R.layout.nav_header_sign_in)
            headerView.findViewById<View>(R.id.nav_header_sign_in)
            val menu: Menu = binding!!.navView.menu
            menu.findItem(R.id.appliedFragment).setTitle("Register").setIcon(R.drawable.ic_register)
            menu.findItem(R.id.favouriteFragment).setTitle("Login").setIcon(R.drawable.ic_login)
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

        }


        // Configure the drawer layout to add listener and show icon on toolbar
        drawerToggle.isDrawerIndicatorEnabled = false
//        val drawable = ResourcesCompat.getDrawable(
//            resources,
//            R.drawable.ic_group_1108,
//            requireActivity().theme
//        )
//        drawerToggle.setHomeAsUpIndicator(drawerToggle)

        binding?.drawerLayout?.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        //or you can add icon
        binding?.drawer?.setOnClickListener {
            val drawer = binding!!.drawerLayout
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START)
            } else {
                drawer.openDrawer(GravityCompat.START)
            }
        }
        drawerToggle.toolbarNavigationClickListener = View.OnClickListener {
            val drawer = binding!!.drawerLayout
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START)
            } else {
                drawer.openDrawer(GravityCompat.START)
            }
        }


        val secondFragment = FavouriteFragment()
        val thirdFragment = AppliedFragment()
        val fourthFragment = ProfileFragment()

        binding!!.navView.itemIconTintList = null

        binding!!.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.favouriteFragment -> {
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
                        login = true
                        startActivity(Intent(mActivity, Auth::class.java))
                    }
                }
                R.id.appliedFragment -> {
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
                        login = false
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


    /*  private fun initDrawerLayoutSignIn(){


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

          binding!!.navViewSignIn.setItemIconTintList(null)

          binding!!.navViewSignIn.setNavigationItemSelectedListener{
              when (it.itemId){
                  R.id.register -> {
                      listener?.passFragmentCallback("register")
                  }
                  R.id.login -> {
                      listener?.passFragmentCallback("login")
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

  */
    private fun initData() {

        /*    binding!!.bottomNavView.setItemIconTintList(null)
            binding!!.viewDashbord.setVisibility(View.VISIBLE)


            val firstFragment=DashbordFragment()
            val secondFragment=FavouriteFragment()
            val thirdFragment=AppliedFragment()
            val fourthFragment=ProfileFragment()

            setCurrentFragment(firstFragment)

            binding!!.bottomNavView.setOnNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.dashbordFragment -> {
                        setCurrentFragment(firstFragment)
                        binding!!.filter.visibility=View.VISIBLE
                        binding!!.viewDashbord.setVisibility(View.VISIBLE)
                        binding!!.viewApplied.setVisibility(View.INVISIBLE)
                        binding!!.viewFav.setVisibility(View.INVISIBLE)
                        binding!!.viewProfile.setVisibility(View.INVISIBLE)
                    }
                    R.id.favouriteFragment -> {
                        setCurrentFragment(secondFragment)
                        binding!!.filter.visibility=View.GONE
                        binding!!.viewDashbord.setVisibility(View.INVISIBLE)
                        binding!!.viewApplied.setVisibility(View.INVISIBLE)
                        binding!!.viewFav.setVisibility(View.VISIBLE)
                        binding!!.viewProfile.setVisibility(View.INVISIBLE)
                    }
                    R.id.appliedFragment -> {
                        setCurrentFragment(thirdFragment)
                        binding!!.filter.visibility=View.GONE
                        binding!!.viewDashbord.setVisibility(View.INVISIBLE)
                        binding!!.viewApplied.setVisibility(View.VISIBLE)
                        binding!!.viewFav.setVisibility(View.INVISIBLE)
                        binding!!.viewProfile.setVisibility(View.INVISIBLE)
                    }
                    R.id.profileFragment -> {
                        setCurrentFragment(fourthFragment)
                        binding!!.filter.visibility=View.GONE
                        binding!!.viewDashbord.setVisibility(View.INVISIBLE)
                        binding!!.viewApplied.setVisibility(View.INVISIBLE)
                        binding!!.viewFav.setVisibility(View.INVISIBLE)
                        binding!!.viewProfile.setVisibility(View.VISIBLE)
                    }
                }
                true
            }
    */
        /*  binding!!.bottomNavView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
              when (item.itemId) {
                  R.id.dashbordFragment -> {
                      binding!!.viewDashbord.setVisibility(View.VISIBLE)
                      binding!!.viewApplied.setVisibility(View.INVISIBLE)
                      binding!!.viewFav.setVisibility(View.INVISIBLE)
                      binding!!.viewProfile.setVisibility(View.INVISIBLE)

                  }
                  R.id.favouriteFragment -> {
                      binding!!.viewDashbord.setVisibility(View.INVISIBLE)
                      binding!!.viewApplied.setVisibility(View.INVISIBLE)
                      binding!!.viewFav.setVisibility(View.VISIBLE)
                      binding!!.viewProfile.setVisibility(View.INVISIBLE)

                  }
                  R.id.appliedFragment -> {
                      binding!!.viewDashbord.setVisibility(View.INVISIBLE)
                      binding!!.viewApplied.setVisibility(View.VISIBLE)
                      binding!!.viewFav.setVisibility(View.INVISIBLE)
                      binding!!.viewProfile.setVisibility(View.INVISIBLE)
                  }
                  R.id.profileFragment -> {
                      binding!!.viewDashbord.setVisibility(View.INVISIBLE)
                      binding!!.viewApplied.setVisibility(View.INVISIBLE)
                      binding!!.viewFav.setVisibility(View.INVISIBLE)
                      binding!!.viewProfile.setVisibility(View.VISIBLE)
                  }
              }
              false
          })*/

    }

    /*  private fun initNavigation() {
          val nestedNavHostFragment = childFragmentManager.findFragmentById(R.id.fragment_dashbord) as? NavHostFragment
          val navController = nestedNavHostFragment?.navController
          val id = navController!!.currentDestination!!.id
          if (id==R.id.dashbordFragment){
              binding!!.viewDashbord.setVisibility(View.VISIBLE)
              binding!!.viewApplied.setVisibility(View.INVISIBLE)
              binding!!.viewFav.setVisibility(View.INVISIBLE)
              binding!!.viewProfile.setVisibility(View.INVISIBLE)
          }else if (id ==R.id.appliedFragment){
              binding!!.viewDashbord.setVisibility(View.INVISIBLE)
              binding!!.viewApplied.setVisibility(View.VISIBLE)
              binding!!.viewFav.setVisibility(View.INVISIBLE)
              binding!!.viewProfile.setVisibility(View.INVISIBLE)
          }
          if (navController != null) {
              binding!!.bottomNavView.setupWithNavController(navController)
          } else {
              throw RuntimeException("Controller not found")
          }
      }

  */

    /*    fun onClick(view: View){
            when (view?.id) {
                R.id.filter -> {
                    showBottomSheetDialog()
                }
                R.id.drawer ->{
                    initDrawerLayout()
                }

            }
        }
    */
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

    fun loadData() {

        buttonList.clear()

        for (i in 0..6) {
            buttonList.add(FilterButtonData("10 km"))
        }


    }

    /* override fun onNavigationItemSelected(item: MenuItem): Boolean {
         item.setChecked(true)
         binding!!.drawerLayout.closeDrawers()
         val id: Int = item.getItemId()

         when (id) {
             R.id.dashbordFragment ->   navController!!.navigate(R.id.action_baseDashbordFragment3_to_dashbordFragment)

         }
         return true
     }*/


    private fun setCurrentFragment(fragment: Fragment) =
        childFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, fragment)
            commit()
        }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as CallFragmentInterface
    }


    /*   override fun onResume() {
           super.onResume()
           //  binding!!.bottomNavView.getMenu().getItem(0).setChecked(true)
           val fourthFragment = PostedProfileFragment()
           if(binding!!.bottomNavView.selectedItemId==R.id.profileFragment){
               setCurrentFragment(fourthFragment)
               //  binding!!.filter.visibility=View.GONE
               binding!!.viewDashbord.setVisibility(View.INVISIBLE)
               binding!!.viewApplied.setVisibility(View.INVISIBLE)
               binding!!.viewFav.setVisibility(View.INVISIBLE)
               binding!!.viewProfile.setVisibility(View.VISIBLE)
           }

       }
   */
}
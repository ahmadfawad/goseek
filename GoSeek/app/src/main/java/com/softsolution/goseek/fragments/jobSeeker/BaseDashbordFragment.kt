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


@Suppress("DEPRECATION")
class BaseDashbordFragment : BaseFragment() {
    private var binding: FragmentBaseDashbordBinding? = null
    var listener: CallFragmentInterface? = null
    var fragment: Fragment? = null
    var recyclerView: RecyclerView? = null
    var toolbar: Toolbar? = null
    private val buttonList = ArrayList<FilterButtonData>()
    private var adapter: FilterButtonAdapter? = null
    var rangeSlider: RangeSlider? = null
    lateinit var button: MaterialButton
    var drawerLayout: DrawerLayout? = null
    var navController: NavController? = null
    var navigationView: NavigationView? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_base_dashbord,
            container,
            false
        )




        binding!!.filter.setOnClickListener {
            showBottomSheetDialog()
        }

        initDrawerLayout()


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
                        headingTextChange("New Job Seeker", "Dashboard")
                        tab.setIcon(R.drawable.ic_home_red)
                        binding!!.tabLayout.getTabAt(1)?.setIcon(R.drawable.ic_heart_grey)
                        binding!!.tabLayout.getTabAt(2)?.setIcon(R.drawable.ic_applied)
                        binding!!.tabLayout.getTabAt(3)?.setIcon(R.drawable.ic_user)
                    }
                    1 -> {
                        headingTextChange("New Job Seeker", "Favourite Jobs")
                        tab.setIcon(R.drawable.ic_fill_heart)
                        binding!!.tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_house)
                        binding!!.tabLayout.getTabAt(2)?.setIcon(R.drawable.ic_applied)
                        binding!!.tabLayout.getTabAt(3)?.setIcon(R.drawable.ic_user)
                    }
                    2 -> {
                        headingTextChange("New Job Seeker", "Applied Jobs")
                        tab.setIcon(R.drawable.ic_applied_red)
                        binding!!.tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_house)
                        binding!!.tabLayout.getTabAt(1)?.setIcon(R.drawable.ic_heart_grey)
                        binding!!.tabLayout.getTabAt(3)?.setIcon(R.drawable.ic_user)
                    }
                    3 -> {
                        headingTextChange("New Job Seeker", "Profile")
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        headingTextChange("New Job Seeker", "Dashboard")
    }


    private fun initDrawerLayout() {


        if (LocalPreference.shared.isLogin) {
            val headerView = binding!!.navView.inflateHeaderView(R.layout.nav_header_main)
            headerView.findViewById<View>(R.id.nav_header_main)
            val name = headerView.findViewById<TextView>(R.id.tv_name)
            button = headerView.findViewById(R.id.back)
            name.text = LocalPreference.shared.user?.Name
            binding?.filter?.visibility = View.VISIBLE
            button.setOnClickListener {
                binding!!.drawerLayout.closeDrawer(GravityCompat.START)
            }

        } else {
            val headerView = binding!!.navView.inflateHeaderView(R.layout.nav_header_sign_in)
            headerView.findViewById<View>(R.id.nav_header_sign_in)
            val menu: Menu = binding!!.navView.menu
            binding?.filter?.visibility = View.GONE
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


        binding!!.navView.itemIconTintList = null

        binding!!.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.favouriteFragment -> {
                    headingTextChange("New Job Seeker", "Favourite Jobs")
                    if (LocalPreference.shared.isLogin) {
                        binding!!.tabLayout.getTabAt(1)?.select()
                        binding!!.frameLayout.currentItem = 1
                        binding!!.filter.visibility = View.GONE

                    } else {
                        login = true
                        startActivity(Intent(mActivity, Auth::class.java))
                    }
                }
                R.id.appliedFragment -> {
                    headingTextChange("New Job Seeker", "Applied Jobs")
                    if (LocalPreference.shared.isLogin) {
                        binding!!.tabLayout.getTabAt(2)?.select()
                        binding!!.frameLayout.currentItem = 2
                        binding!!.filter.visibility = View.GONE

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


    private fun showBottomSheetDialog() {
        val view: View = layoutInflater.inflate(R.layout.flter_screen_dialogue, null, false)
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

    private fun loadData() {

        buttonList.clear()

        for (i in 0..6) {
            buttonList.add(FilterButtonData("10 km"))
        }


    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as CallFragmentInterface
    }

    private fun headingTextChange(beforeLogin: String, afterLogin: String) {
        if (!LocalPreference.shared.isLogin) {
            binding?.tvHeading?.text = beforeLogin
        } else {
            binding?.tvHeading?.text = afterLogin
        }
    }
}

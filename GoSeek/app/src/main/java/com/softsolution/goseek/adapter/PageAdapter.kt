package com.softsolution.goseek.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.softsolution.goseek.fragments.jobSeeker.AppliedFragment
import com.softsolution.goseek.fragments.jobSeeker.DashbordFragment
import com.softsolution.goseek.fragments.jobSeeker.FavouriteFragment
import com.softsolution.goseek.fragments.jobSeeker.ProfileFragment

class PageAdapter (fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 4;
    }

    override fun getItem(position: Int): Fragment {
        when(position) {
            0 -> {
                return DashbordFragment()
            }
            1 -> {
                return FavouriteFragment()
            }
            2 -> {
                return AppliedFragment()
            }
            3->{
                return ProfileFragment()
            }
            else -> {
                return DashbordFragment()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position) {
            0 -> {
                return "Dashbord"
            }
            1 -> {
                return "Favourite"
            }
            2 -> {
                return "Applied"
            }
            3->{
                return "Profile"
            }
        }
        return super.getPageTitle(position)
    }

}
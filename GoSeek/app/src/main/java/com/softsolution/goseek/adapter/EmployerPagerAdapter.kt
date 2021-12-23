package com.softsolution.goseek.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.softsolution.goseek.fragments.jobPoster.NewJobFragment
import com.softsolution.goseek.fragments.jobPoster.PostedDashbordFragment
import com.softsolution.goseek.fragments.jobPoster.PostedProfileFragment
import com.softsolution.goseek.fragments.jobSeeker.AppliedFragment
import com.softsolution.goseek.fragments.jobSeeker.DashbordFragment
import com.softsolution.goseek.fragments.jobSeeker.FavouriteFragment
import com.softsolution.goseek.fragments.jobSeeker.ProfileFragment

class EmployerPagerAdapter (fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 3;
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> {
                PostedDashbordFragment()
            }
            1 -> {
                NewJobFragment()
            }
            2->{
                PostedProfileFragment()
            }
            else -> {
                PostedDashbordFragment()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position) {
            0 -> {
                return "JobDashbord"
            }
            1 -> {
                return "NewJob"
            }
            2->{
                return "Profile"
            }
        }
        return super.getPageTitle(position)
    }
}
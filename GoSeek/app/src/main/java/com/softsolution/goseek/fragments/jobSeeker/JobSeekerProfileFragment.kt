package com.softsolution.goseek.fragments.jobSeeker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.softsolution.goseek.R
import com.softsolution.goseek.databinding.FragmentJobSeekerProfileBinding

class JobSeekerProfileFragment : Fragment() {

    private var binding: FragmentJobSeekerProfileBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_job_seeker_profile,
            container,
            false
        )
        binding!!.setFragment(this)


        return binding!!.getRoot()

    }



    fun onClick(view: View){
        val navController = findNavController()
        when (view?.id) {
            R.id.back -> {
                navController.popBackStack()
            }

            R.id.explore_jobs -> {

            }

        }

    }
}
package com.softsolution.goseek.fragments.jobPoster

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.softsolution.goseek.R
import com.softsolution.goseek.databinding.FragmentJobSeekerDetailBinding

class JobSeekerDetailFragment : Fragment() {

    private var binding: FragmentJobSeekerDetailBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_job_seeker_detail, container, false)
        binding!!.setFragment(this)


        return binding!!.getRoot()

    }

    fun onClick(view: View) {
        when (view?.id) {
            R.id.back -> {
                this.findNavController().popBackStack()
            }
            R.id.cardview -> {
                val navController = findNavController()
                navController.navigate(R.id.action_jobSeekerDetailFragment_to_postedProfileDetailFragment)
              //  this.findNavController().popBackStack()
            }
        }

    }
}
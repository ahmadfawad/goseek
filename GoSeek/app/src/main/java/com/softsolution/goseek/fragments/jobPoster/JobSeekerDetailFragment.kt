package com.softsolution.goseek.fragments.jobPoster

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.softsolution.goseek.R
import com.softsolution.goseek.base.BaseFragment
import com.softsolution.goseek.databinding.FragmentJobSeekerDetailBinding
import com.softsolution.goseek.model.jobPosterModel.PostedData
import com.softsolution.goseek.network.NetworkClass
import com.softsolution.goseek.network.Response
import com.softsolution.goseek.network.URLApi


class JobSeekerDetailFragment : BaseFragment() {

    private var binding: FragmentJobSeekerDetailBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_job_seeker_detail, container, false)
        binding!!.fragment = this


        return binding!!.root

    }

    fun onClick(view: View) {
        when (view.id) {
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
package com.softsolution.goseek.fragments.jobPoster

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.softsolution.goseek.R
import com.softsolution.goseek.databinding.FragmentSucessfullBinding
import com.softsolution.goseek.databinding.FragmentSucessfullPostedBinding


class SucessfullPostedFragment : Fragment() {

    private var binding: FragmentSucessfullPostedBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_sucessfull_posted,
            container,
            false
        )

        binding!!.exploreJobs.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.action_sucessfullPostedFragment_to_posterBaseDashbordFragment)
        }

        return binding!!.getRoot()
    }

}


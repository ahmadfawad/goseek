package com.softsolution.goseek.fragments.jobSeeker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.softsolution.goseek.R
import com.softsolution.goseek.databinding.FragmentBaseDashbordBinding
import com.softsolution.goseek.databinding.FragmentSucessfullBinding
import com.softsolution.goseek.utils.Constants


class SucessfullFragment : Fragment() {
    private var binding: FragmentSucessfullBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_sucessfull,
            container,
            false
        )

        binding!!.exploreJobs.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.action_sucessfullFragment_to_baseDashbordFragment)
        }

        return binding!!.getRoot()
    }


}
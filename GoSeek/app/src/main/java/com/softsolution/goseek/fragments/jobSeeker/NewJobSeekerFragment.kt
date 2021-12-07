package com.softsolution.goseek.fragments.jobSeeker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.softsolution.goseek.R
import com.softsolution.goseek.databinding.FragmentNewJobSeekerBinding
import com.softsolution.goseek.utils.Constants


class NewJobSeekerFragment : Fragment() {
    private var binding: FragmentNewJobSeekerBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_job_seeker, container, false)
        binding!!.setFragment(this)

        return binding!!.getRoot()

    }

    fun onclick(view:View){
        when (view?.id) {
            R.id.back -> {
                this.findNavController().popBackStack()
            }

            R.id.next ->{
                Constants.login =true
                val navController = findNavController()
                navController.navigate(R.id.action_newJobSeekerFragment_to_emailVerificationFragment)
            }

        }
    }
}
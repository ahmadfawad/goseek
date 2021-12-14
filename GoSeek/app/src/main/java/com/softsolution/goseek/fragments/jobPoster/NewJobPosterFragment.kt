package com.softsolution.goseek.fragments.jobPoster

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.softsolution.goseek.R
import com.softsolution.goseek.databinding.FragmentNewJobPosterBinding
import com.softsolution.goseek.utils.Constants


class NewJobPosterFragment : Fragment() {
    private var binding: FragmentNewJobPosterBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_new_job_poster,
            container,
            false
        )
        binding!!.fragment = this

        return binding!!.root
    }

    fun onclick(view: View) {
        when (view?.id) {
            R.id.next -> {
                if (Constants.login) {
                    Constants.login = true
                    val navController = findNavController()
                    navController.navigate(R.id.action_newJobPosterFragment_to_posterBaseDashbordFragment)
                }
            }
            R.id.back -> {
                this.findNavController().popBackStack()
            }
        }

    }


}
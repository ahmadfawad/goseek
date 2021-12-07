package com.softsolution.goseek.fragments.jobSeeker

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.softsolution.goseek.Interface.CallFragmentInterface
import com.softsolution.goseek.R
import com.softsolution.goseek.databinding.FragmentNewJobSeekerBinding
import com.softsolution.goseek.databinding.FragmentUploadResumeBinding


class UploadResumeFragment : Fragment() {

    private var binding: FragmentUploadResumeBinding? = null
    var listener: CallFragmentInterface? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_upload_resume, container, false)
        binding!!.setFragment(this)

        return binding!!.getRoot()

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as CallFragmentInterface
    }

    fun onClick(view: View) {
        when (view?.id) {
            R.id.back -> {
                this.findNavController().popBackStack()
             //   listener!!.passFragmentCallback("uploadResume")
            }
        }
    }
}
package com.softsolution.goseek.fragments.jobPoster

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.softsolution.goseek.Interface.CallFragmentInterface
import com.softsolution.goseek.R
import com.softsolution.goseek.databinding.FragmentNewJobBinding


class NewJobFragment : Fragment() {
    private var binding: FragmentNewJobBinding? = null
    var listener: CallFragmentInterface? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_new_job,
            container,
            false
        )
        binding!!.fragment = this

        return binding!!.root
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.postJob -> {
                if (binding?.etTitle?.editableText?.isNullOrEmpty() == true) {
                    binding?.etTitle?.error = "Please enter job title"
                } else if (binding?.etDescription?.editableText?.isNullOrEmpty() == true) {
                    binding?.etDescription?.error = "Please enter job description"
                } else if (binding?.etWhoCanApply?.editableText.isNullOrEmpty()) {
                    binding?.etWhoCanApply?.error = "Enter who can apply"
                } else if (binding?.etWages?.editableText?.isNullOrEmpty() == true) {
                    binding?.etWages?.error = "Please enter wages"
                } else {
                    listener?.passFragmentCallback("newJobSeekerDetail")
                }
            }
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as CallFragmentInterface
    }

}
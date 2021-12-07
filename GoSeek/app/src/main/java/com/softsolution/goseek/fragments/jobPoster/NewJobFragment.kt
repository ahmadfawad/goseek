package com.softsolution.goseek.fragments.jobPoster

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
import com.softsolution.goseek.databinding.FragmentNewJobBinding
import com.softsolution.goseek.utils.Constants


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
        binding!!.setFragment(this)

        return binding!!.getRoot()
    }

    fun onClick(view: View) {
        when (view?.id) {
            R.id.next -> {
                    listener?.passFragmentCallback("newJobSeekerDetail")
            }
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as CallFragmentInterface
    }

}
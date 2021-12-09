package com.softsolution.goseek.fragments.jobSeeker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.softsolution.goseek.R
import com.softsolution.goseek.base.BaseFragment
import com.softsolution.goseek.databinding.FragmentEnterEmailBinding
import com.softsolution.goseek.network.NetworkClass
import com.softsolution.goseek.network.Response
import com.softsolution.goseek.network.URLApi

class EnterEmailFragment : BaseFragment() {
    private var binding: FragmentEnterEmailBinding? = null
    private val navController = findNavController()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_enter_email, container, false)


        val button = view?.findViewById(R.id.back) as MaterialButton
        button.setOnClickListener {
            navController.popBackStack()
        }

        return view

    }

    fun onclick(view: View) {
        when (view.id) {
            R.id.send -> {
                if (binding?.etEmail?.editableText?.isNullOrEmpty() == true) {
                    binding?.etEmail?.error = "Please enter your email"
                } else {
                    navController.popBackStack()
                }
            }
        }
    }


}
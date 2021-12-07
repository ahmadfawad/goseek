package com.softsolution.goseek.fragments.jobSeeker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.softsolution.goseek.R



class EmailVerificationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater?.inflate(R.layout.fragment_email_verification,container, false)

        val navController = findNavController()
        val button = view.findViewById(R.id.back) as MaterialButton
        val next = view.findViewById(R.id.next) as MaterialButton
        button.setOnClickListener {
            navController.popBackStack()
        }

        next.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.action_emailVerificationFragment_to_baseDashbordFragment)

            //findNavController().popBackStack(R.id.baseDashbordFragment,true)
        }
        return view
    }



}
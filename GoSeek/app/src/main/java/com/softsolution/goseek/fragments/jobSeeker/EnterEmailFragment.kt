package com.softsolution.goseek.fragments.jobSeeker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_enter_email, container, false)
        binding?.fragment = this

        return binding!!.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.backEmail?.setOnClickListener{
            val navController = findNavController()
            navController.popBackStack()
        }
    }

    fun onclick(view: View) {
        when (view.id) {
            R.id.send -> {
                if (binding?.etEmail?.editableText?.isNullOrEmpty() == true) {
                    binding?.etEmail?.error = "Please enter your email"
                } else {
                  forgotPassword(binding?.etEmail?.editableText.toString().trim())
                }
            }
        }
    }

    private fun forgotPassword(email : String) {
        showLoading()
        NetworkClass.callApi(URLApi.sendPassword(email), object : Response {
            override fun onSuccessResponse(response: String?, message: String) {
                hideLoading()
                val navController = findNavController()
                navController.popBackStack()
            }

            override fun onErrorResponse(error: String?, response: String?) {
                hideLoading()
                Toast.makeText(mActivity, error ?: "", Toast.LENGTH_SHORT).show()
            }

        })
    }

}
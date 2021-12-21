package com.softsolution.goseek.fragments.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.softsolution.goseek.R
import com.softsolution.goseek.base.BaseFragment
import com.softsolution.goseek.databinding.FragmentNewJobSeekerBinding
import com.softsolution.goseek.model.User
import com.softsolution.goseek.network.LocalPreference
import com.softsolution.goseek.network.NetworkClass
import com.softsolution.goseek.network.Response
import com.softsolution.goseek.network.URLApi
import com.softsolution.goseek.utils.Constants
import com.softsolution.goseek.utils.isValidEmail
import org.json.JSONObject


class NewJobSeekerFragment : BaseFragment() {
    private var binding: FragmentNewJobSeekerBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_new_job_seeker, container, false)
        binding!!.fragment = this

        return binding!!.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(LocalPreference.shared.isCompany){
            binding?.tvTitle?.text = "New Employer"
        }else{
            binding?.tvTitle?.text = "New Job Seeker"
        }
    }

    fun onclick(view: View) {
        when (view?.id) {
            R.id.back -> {
                this.findNavController().popBackStack()
            }

            R.id.next -> {
                if (binding?.etName?.editableText.isNullOrBlank()) {
                    binding?.etName?.error = "Please enter your name"
                } else if (binding?.etEmail?.editableText.isNullOrBlank()) {
                    binding?.etEmail?.error = "Please enter your email"
                } else if (!binding?.etEmail?.editableText?.toString()?.trim()?.isValidEmail()!!) {
                    binding?.etEmail?.error = "Please enter valid email"
                } else if (binding?.etCode?.editableText.isNullOrBlank()) {
                    binding?.etCode?.error = "Please enter mobile code"
                } else if (binding?.etMobileNumber?.editableText.isNullOrBlank()) {
                    binding?.etMobileNumber?.error = "Please enter mobile number"
                } else if (binding?.etPassword?.editableText.isNullOrBlank()) {
                    binding?.etPassword?.error = "Please enter password"
                } else if (binding?.etPassword?.editableText.toString().length < 8) {
                    binding?.etPassword?.error = "Your password must be a at least 8 letters"
                } else if (binding?.etConfirmPassword?.editableText.isNullOrBlank()) {
                    binding?.etConfirmPassword?.error = "Please retype your password"
                } else if (binding?.etPassword?.editableText.toString()
                        .trim() != binding?.etConfirmPassword?.editableText.toString().trim()
                ) {
                    binding?.etConfirmPassword?.error = "Your password does not match"
                } else if (!binding?.cbTerms?.isChecked!!) {
                    Toast.makeText(
                        mActivity,
                        "Please agree on Terms and Condition",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    if (!LocalPreference.shared.isCompany) {
                        registerUser(
                            binding?.etName?.editableText?.toString()?.trim() ?: "",
                            binding?.etEmail?.editableText?.toString()?.trim() ?: "",
                            binding?.etMobileNumber?.editableText?.toString()?.trim() ?: "",
                            binding?.etPassword?.editableText?.toString()?.trim() ?: ""
                        )
                    } else {
                        registerCompany(
                            binding?.etName?.editableText?.toString()?.trim() ?: "",
                            binding?.etEmail?.editableText?.toString()?.trim() ?: "",
                            binding?.etMobileNumber?.editableText?.toString()?.trim() ?: "",
                            binding?.etPassword?.editableText?.toString()?.trim() ?: ""
                        )
                    }
                }
            }

        }
    }

    private fun registerUser(
        username: String,
        email: String,
        phone: String,
        password: String,
    ) {
        showLoading()
        NetworkClass.callApi(URLApi.registerUser(username, email, phone, password),
            object : Response {
                override fun onSuccessResponse(response: String?, message: String) {
                    hideLoading()
                    val json = JSONObject(response ?: "")
                    val data = Gson().fromJson(json.toString(), User::class.java)
                    LocalPreference.shared.user = data
                    val navController = findNavController()
                    navController.navigate(R.id.action_newJobSeekerFragment2_to_emailVerificationFragment2)
                }

                override fun onErrorResponse(error: String?, response: String?) {
                    hideLoading()
                    Toast.makeText(requireContext(), error ?: "", Toast.LENGTH_SHORT).show()
                }

            })
    }

    private fun registerCompany(
        username: String,
        email: String,
        phone: String,
        password: String,
    ) {
        showLoading()
        NetworkClass.callApi(URLApi.registerCompany(username, email, phone, password),
            object : Response {
                override fun onSuccessResponse(response: String?, message: String) {
                    hideLoading()
                    val json = JSONObject(response ?: "")
                    val data = Gson().fromJson(json.toString(), User::class.java)
                    LocalPreference.shared.user = data
                    LocalPreference.shared.isLogin = true
                    LocalPreference.shared.isCompany = true
                    val navController = findNavController()
                    navController.navigate(R.id.action_newJobSeekerFragment2_to_emailVerificationFragment2)
                }

                override fun onErrorResponse(error: String?, response: String?) {
                    hideLoading()
                    Toast.makeText(requireContext(), error ?: "", Toast.LENGTH_SHORT).show()
                }

            })
    }

}
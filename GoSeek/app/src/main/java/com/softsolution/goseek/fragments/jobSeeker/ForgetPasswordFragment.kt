package com.softsolution.goseek.fragments.jobSeeker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.softsolution.goseek.R
import com.softsolution.goseek.base.BaseFragment
import com.softsolution.goseek.databinding.FragmentForgetPasswordBinding
import com.softsolution.goseek.network.LocalPreference
import com.softsolution.goseek.network.NetworkClass
import com.softsolution.goseek.network.Response
import com.softsolution.goseek.network.URLApi


class ForgetPasswordFragment : BaseFragment() {
    private lateinit var binding: FragmentForgetPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_forget_password, container, false)
        binding.fragment = this

        return binding.root


    }


    fun onclick(view: View) {
        when (view?.id) {
            R.id.save -> {
                when {
                    binding.etOldPassword.editableText.isNullOrEmpty() -> {
                        binding.etOldPassword.error = "Please enter old Password"
                    }
                    binding.etNewPassword.editableText.isNullOrBlank() -> {
                        binding.etNewPassword.error = "Please enter password"
                    }
                    binding.etNewPassword.editableText.toString().length < 8 -> {
                        binding.etNewPassword.error = "Your password must be a at least 8 letters"
                    }
                    binding.etConfirmPassword.editableText.isNullOrBlank() -> {
                        binding.etConfirmPassword.error = "Please retype your password"
                    }
                    binding.etNewPassword.editableText.toString()
                        .trim() != binding.etConfirmPassword.editableText.toString().trim() -> {
                        binding.etConfirmPassword.error = "Your password does not match"
                    }
                    else -> {
                        changePassword(
                            binding.etOldPassword.editableText.toString().trim(),
                            binding.etNewPassword.editableText.toString().trim(),
                            binding.etConfirmPassword.editableText.toString().trim(),
                            LocalPreference.shared.user?.MemberId.toString()
                        )
                    }
                }
            }
            R.id.back -> {
                val navController = findNavController()
                navController.popBackStack()
            }
        }
    }

    private fun changePassword(
        oldPassword: String,
        password: String,
        confirmPassword: String,
        memberId: String
    ) {
        showLoading()
        NetworkClass.callApi(
            URLApi.changePassword(
                oldPassword,
                password,
                confirmPassword,
                memberId
            ), object : Response {
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
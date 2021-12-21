package com.softsolution.goseek.fragments.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.softsolution.goseek.R
import com.softsolution.goseek.activities.EmployerDashboard
import com.softsolution.goseek.activities.UserDashboard
import com.softsolution.goseek.base.BaseFragment
import com.softsolution.goseek.databinding.FragmentLoginBinding
import com.softsolution.goseek.model.User
import com.softsolution.goseek.network.LocalPreference
import com.softsolution.goseek.network.NetworkClass
import com.softsolution.goseek.network.Response
import com.softsolution.goseek.network.URLApi
import org.json.JSONObject


class LoginFragment : BaseFragment() {
    private var binding: FragmentLoginBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding!!.fragment = this



        return binding!!.root
    }

    fun onclick(view: View) {
        when (view.id) {

            R.id.forgetPassword -> {
                val navController = findNavController()
                navController.navigate(R.id.action_loginFragment2_to_enterEmailFragment)
            }

            R.id.login -> {
                when {
                    binding?.etEmail?.editableText?.isNullOrEmpty() == true -> {
                        binding?.etEmail?.error = "Please enter your email"
                    }
                    binding?.etPassword?.editableText?.isNullOrEmpty() == true -> {
                        binding?.etPassword?.error = "Please enter your password"
                    }
                    else -> {
                        login(
                            binding?.etEmail?.editableText?.toString()?.trim() ?: "",
                            binding?.etPassword?.editableText?.toString()?.trim() ?: ""
                        )
                    }
                }
            }

            R.id.back -> {
                this.findNavController().popBackStack()
            }
            R.id.tvNewUser -> {
                val navController = findNavController()
                navController.navigate(R.id.action_loginFragment2_to_newJobSeekerFragment2)
            }
        }
    }

    private fun login(email: String, password: String) {
        showLoading()
        NetworkClass.callApi(URLApi.login(email, password), object : Response {
            override fun onSuccessResponse(response: String?, message: String) {
                hideLoading()
                val json = JSONObject(response ?: "")
                val data = Gson().fromJson(json.toString(), User::class.java)
                LocalPreference.shared.user = data
                LocalPreference.shared.isLogin = true
                if (data.IsVerify == false) {
                    val navController = findNavController()
                    navController.navigate(R.id.action_loginFragment2_to_emailVerificationFragment2)
                } else {
                    if (data.status == 1) {
                        val intent = Intent(mActivity, UserDashboard::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
//                        val navController = findNavController()
//                        navController.navigate(R.id.action_loginFragment_to_baseDashbordFragment)
                    } else {
                        val intent = Intent(mActivity, EmployerDashboard::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
//                        val navController = findNavController()
//                        navController.navigate(R.id.action_loginFragment_to_companyDashboard)
                    }
                }
            }

            override fun onErrorResponse(error: String?, response: String?) {
                hideLoading()
                Toast.makeText(mActivity, error ?: "", Toast.LENGTH_SHORT).show()
            }

        })
    }

}
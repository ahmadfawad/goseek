package com.softsolution.goseek.fragments.jobSeeker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.softsolution.goseek.R
import com.softsolution.goseek.databinding.FragmentLoginBinding
import com.softsolution.goseek.utils.Constants


class LoginFragment : Fragment() {
    private var binding: FragmentLoginBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding!!.setFragment(this)



        return binding!!.getRoot()
    }

    fun onclick(view: View) {
        when (view?.id) {

            R.id.forgetPassword -> {
                val navController = findNavController()
                navController.navigate(R.id.action_loginFragment_to_forgetPasswordFragment)
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
                        Constants.login = true
                        val navController = findNavController()
                        navController.navigate(R.id.action_loginFragment_to_baseDashbordFragment)
                    }
                }
            }

            R.id.back -> {
                this.findNavController().popBackStack()
            }
            R.id.tvNewUser -> {
                val navController = findNavController()
                navController.navigate(R.id.action_loginFragment_to_ResgisterUser)
            }
        }
    }

}
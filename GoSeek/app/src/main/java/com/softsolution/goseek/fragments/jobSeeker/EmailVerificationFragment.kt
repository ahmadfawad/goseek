package com.softsolution.goseek.fragments.jobSeeker

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
import com.softsolution.goseek.databinding.FragmentEmailVerificationBinding
import com.softsolution.goseek.model.User
import com.softsolution.goseek.network.LocalPreference
import com.softsolution.goseek.network.NetworkClass
import com.softsolution.goseek.network.Response
import com.softsolution.goseek.network.URLApi
import org.json.JSONObject


class EmailVerificationFragment : BaseFragment() {
    private lateinit var binding: FragmentEmailVerificationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_email_verification,
                container,
                false
            )
        binding.fragment = this

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvTextMail.text =
            "Please sent the Verification code that we\nsent you on your email ${LocalPreference.shared.user?.Email}"
        val navController = findNavController()
        binding.back.setOnClickListener {
            navController.popBackStack()
        }
    }

    fun onClick(view: View) {
        when (view?.id) {
            R.id.next -> {

                if (binding.squareField.text.isNullOrEmpty()) {
                    Toast.makeText(mActivity, "Please enter code", Toast.LENGTH_SHORT).show()
                } else {
                    optVerify(binding.squareField.text.toString())
                }


            }
        }

        //findNavController().popBackStack(R.id.baseDashbordFragment,true)
    }

    private fun optVerify(code: String) {
        showLoading()
        NetworkClass.callApi(URLApi.optVerify(code), object : Response {
            override fun onSuccessResponse(response: String?, message: String) {
                hideLoading()
                val json = JSONObject(response ?: "")
                val user = Gson().fromJson(json.toString(), User::class.java)
                LocalPreference.shared.user = user
                Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show()
                if(!LocalPreference.shared.isCompany){
                val navController = findNavController()
                    navController.navigate(R.id.action_emailVerificationFragment_to_baseDashbordFragment)
                }
                else{
                    val navController = findNavController()
                    navController.navigate(R.id.action_emailVerificationFragment_to_postJob)

                }
            }

            override fun onErrorResponse(error: String?, response: String?) {
                hideLoading()
                Toast.makeText(mActivity, error ?: "", Toast.LENGTH_SHORT).show()

            }

        })
    }

}
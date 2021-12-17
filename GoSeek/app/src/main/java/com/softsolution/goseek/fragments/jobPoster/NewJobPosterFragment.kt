package com.softsolution.goseek.fragments.jobPoster

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.softsolution.goseek.R
import com.softsolution.goseek.base.BaseFragment
import com.softsolution.goseek.databinding.FragmentNewJobPosterBinding
import com.softsolution.goseek.network.LocalPreference
import com.softsolution.goseek.network.NetworkClass
import com.softsolution.goseek.network.Response
import com.softsolution.goseek.network.URLApi


class NewJobPosterFragment : BaseFragment() {
    private var binding: FragmentNewJobPosterBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_new_job_poster,
            container,
            false
        )
        binding!!.fragment = this

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding?.etWages?.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//            }
//
//            @SuppressLint("SetTextI18n")
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                binding?.etWages?.setText(binding?.etWages?.text.toString() + "$")
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//
//            }
//        })
    }

    fun onclick(view: View) {
        when (view.id) {
            R.id.next -> {
                if (binding?.etJobTitle?.editableText.isNullOrEmpty()) {
                    binding?.etJobTitle?.error = "Please enter job title"
                } else if (binding?.etJobDescription?.editableText.isNullOrEmpty()) {
                    binding?.etJobDescription?.error = "Please enter job description"
                } else if (binding?.etWages?.editableText.isNullOrEmpty()) {
                    binding?.etWages?.error = "Please enter wages"
                } else {
                    postJob(
                        binding?.etJobTitle?.editableText.toString().trim(),
                        binding?.etJobDescription?.editableText.toString().trim(),
                        binding?.etWages?.editableText.toString().trim().replace("$", "")
                    )
                }
            }
            R.id.back -> {
                this.findNavController().popBackStack()
            }
        }

    }

    private fun postJob(jobTitle: String, JobDescription: String, Wages: String) {
        showLoading()
        NetworkClass.callApi(
            URLApi.postJob(
                jobTitle,
                JobDescription,
                Wages,
                LocalPreference.shared.user?.MemberId.toString()
            ), object : Response {
                override fun onSuccessResponse(response: String?, message: String) {
                    hideLoading()
                    val navController = findNavController()
                    navController.navigate(R.id.action_newJobPosterFragment_to_posterBaseDashbordFragment)
                }

                override fun onErrorResponse(error: String?, response: String?) {
                    hideLoading()
                    Toast.makeText(mActivity, error ?: "", Toast.LENGTH_SHORT).show()
                }

            })
    }


}
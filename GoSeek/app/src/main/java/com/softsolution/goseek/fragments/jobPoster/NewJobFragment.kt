package com.softsolution.goseek.fragments.jobPoster

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.softsolution.goseek.Interface.CallFragmentInterface
import com.softsolution.goseek.R
import com.softsolution.goseek.base.BaseFragment
import com.softsolution.goseek.databinding.FragmentNewJobBinding
import com.softsolution.goseek.network.LocalPreference
import com.softsolution.goseek.network.NetworkClass
import com.softsolution.goseek.network.Response
import com.softsolution.goseek.network.URLApi


class NewJobFragment : BaseFragment() {
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
        binding!!.fragment = this

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding?.etWages?.onFocusChangeListener =
//            View.OnFocusChangeListener { v, hasFocus ->
//                if (hasFocus)
//                    binding?.etWages?.setText("$")
//                binding?.etWages?.setSelection(1)
//            }
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

    fun onClick(view: View) {
        when (view.id) {
            R.id.postJob -> {
                if (binding?.etTitle?.editableText?.isNullOrEmpty() == true) {
                    binding?.etTitle?.error = "Please enter job title"
                } else if (binding?.etDescription?.editableText?.isNullOrEmpty() == true) {
                    binding?.etDescription?.error = "Please enter job description"
                } else if (binding?.etWages?.editableText?.isNullOrEmpty() == true) {
                    binding?.etWages?.error = "Please enter wages"
                } else {
                    postJob(
                        binding?.etTitle?.editableText.toString().trim(),
                        binding?.etDescription?.editableText.toString().trim(),
                        binding?.etWages?.editableText.toString().trim().replace("$", "")
                    )
//                    listener?.passFragmentCallback("newJobSeekerDetail")
                }
            }
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as CallFragmentInterface
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
                    Toast.makeText(mActivity, "Job Successfully Posted", Toast.LENGTH_SHORT).show()
                    binding?.etTitle?.setText("")
                    binding?.etDescription?.setText("")
                    binding?.etWages?.setText("")
//                val navController = findNavController()
//                navController.navigate(R.id.action_newJobPosterFragment_to_posterBaseDashbordFragment)
                }

                override fun onErrorResponse(error: String?, response: String?) {
                    hideLoading()
                    Toast.makeText(mActivity, error ?: "", Toast.LENGTH_SHORT).show()
                }

            })
    }

}
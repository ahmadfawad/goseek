package com.softsolution.goseek.fragments.jobPoster

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.softsolution.goseek.Interface.CallFragmentInterface
import com.softsolution.goseek.R
import com.softsolution.goseek.activities.Auth
import com.softsolution.goseek.base.BaseFragment
import com.softsolution.goseek.databinding.FragmentPostedProfileBinding
import com.softsolution.goseek.network.LocalPreference


class PostedProfileFragment : BaseFragment() {
    var listener: CallFragmentInterface? = null
    private var binding: FragmentPostedProfileBinding? = null
    var no: Button? = null
    var yes: Button? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_posted_profile, container, false)
        binding!!.fragment = this

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.tvName?.text = LocalPreference.shared.user?.Name
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.myProfile -> {
                listener?.passFragmentCallback("editCompanyprofile")
            }

            R.id.review -> {
                listener?.passFragmentCallback("review")
            }

            R.id.changePassword -> {
                listener?.passFragmentCallback("editPassword")
            }

            R.id.location -> {
                listener?.passFragmentCallback("changeLocation")
            }

            R.id.signout -> {
                val messageBoxView =
                    LayoutInflater.from(activity).inflate(R.layout.sign_out_dialog_vh, null)
                val messageBoxBuilder =
                    AlertDialog.Builder(requireActivity()).setView(messageBoxView)

                val no = messageBoxView.findViewById<Button>(R.id.no)
                val yes = messageBoxView.findViewById<Button>(R.id.yes)
                //show dialog
                val messageBoxInstance = messageBoxBuilder.show()

                no.setOnClickListener {
                    messageBoxInstance.dismiss()
                }
                yes.setOnClickListener {
                    LocalPreference.shared.removeAll()
                    startActivity(Intent(mActivity, Auth::class.java))
                    mActivity.finish()
                }


            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as CallFragmentInterface
    }


}
package com.softsolution.goseek.fragments.jobSeeker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.softsolution.goseek.Interface.CallFragmentInterface
import com.softsolution.goseek.R
import com.softsolution.goseek.activities.Auth
import com.softsolution.goseek.base.BaseFragment
import com.softsolution.goseek.databinding.FragmentProfileBinding
import com.softsolution.goseek.network.LocalPreference

class ProfileFragment : BaseFragment() {
    var listener: CallFragmentInterface? = null
    private var binding: FragmentProfileBinding? = null
    var no: Button? = null
    var yes: Button? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding!!.fragment = this

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.tvName?.text = LocalPreference.shared.user?.Name
    }

    fun onClick(view: View) {
        when (view?.id) {
            R.id.myProfile -> {
                listener?.passFragmentCallback("profile")
            }

            R.id.uploadResume -> {
                listener?.passFragmentCallback("uploadResume")
            }

            R.id.changePassword -> {
                listener?.passFragmentCallback("changePassword")
            }

            R.id.location -> {
                listener?.passFragmentCallback("location")
            }

            R.id.signout -> {
                val messageBoxView =
                    LayoutInflater.from(activity).inflate(R.layout.sign_out_dialog_vh, null)
                val messageBoxBuilder =
                    AlertDialog.Builder(requireActivity()).setView(messageBoxView)

                val no = messageBoxView.findViewById<Button>(R.id.no)
                val yes = messageBoxView.findViewById<Button>(R.id.yes)
                val cross = messageBoxView.findViewById<ImageButton>(R.id.cross)
                //show dialog
                val messageBoxInstance = messageBoxBuilder.show()

                cross.setOnClickListener {
                    messageBoxInstance.dismiss()
                }

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
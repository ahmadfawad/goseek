package com.softsolution.goseek.fragments.jobPoster

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.softsolution.goseek.Interface.CallFragmentInterface
import com.softsolution.goseek.R
import com.softsolution.goseek.base.BaseFragment
import com.softsolution.goseek.databinding.FragmentEditCompanyProfileBinding
import com.softsolution.goseek.model.User
import com.softsolution.goseek.network.LocalPreference
import com.softsolution.goseek.network.NetworkClass
import com.softsolution.goseek.network.Response
import com.softsolution.goseek.network.URLApi
import org.json.JSONObject

class EditCompanyProfileFragment : BaseFragment() {
    private var binding: FragmentEditCompanyProfileBinding? = null
    private val pickImage = 100
    var listener: CallFragmentInterface? = null
    private var imageUri: Uri? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_edit_company_profile,
            container,
            false
        )
        binding!!.fragment = this

        init()

        return binding!!.root
    }

    private fun init() {
        binding!!.profileImage.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }
        binding?.etCompanyName?.setText(LocalPreference.shared.user?.Name)
        binding?.etEmail?.setText(LocalPreference.shared.user?.Email)
        binding?.etPhoneNumber?.setText(LocalPreference.shared.user?.Phone)
        binding?.etDescription?.setText(LocalPreference.shared.user?.Description)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            binding!!.profileImage.setImageURI(imageUri)
        }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btnSave -> {
                editProfile(
                    binding?.etCompanyName?.editableText.toString().trim(),
                    binding?.etEmail?.editableText.toString().trim(),
                    binding?.etPhoneNumber?.editableText.toString().trim(),
                    LocalPreference.shared.user?.MemberId.toString(),
                    binding?.etDescription?.editableText.toString().trim()
                )
            }
            R.id.back -> {
                listener!!.passFragmentCallback("MyProfile")
                this.findNavController().popBackStack()
            }
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as CallFragmentInterface
    }

    private fun editProfile(
        BusinessName: String,
        Email: String,
        Phone: String,
        MemberId: String,
        Description: String
    ) {
        showLoading()
        NetworkClass.callApi(
            URLApi.editCompanyProfile(
                BusinessName,
                Email,
                Phone,
                MemberId,
                Description
            ), object : Response {
                override fun onSuccessResponse(response: String?, message: String) {
                    hideLoading()
                    val json = JSONObject(response ?: "")
                    val data = Gson().fromJson(json.toString(), User::class.java)
                    binding?.etCompanyName?.setText(data.Name)
                    binding?.etEmail?.setText(data.Email)
                    binding?.etPhoneNumber?.setText(data.Phone)
                    binding?.etDescription?.setText(data.Description)
                    Toast.makeText(mActivity, "Profile Successfully Updated.", Toast.LENGTH_SHORT).show()
                }

                override fun onErrorResponse(error: String?, response: String?) {
                    hideLoading()
                    Toast.makeText(mActivity, error ?: "", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
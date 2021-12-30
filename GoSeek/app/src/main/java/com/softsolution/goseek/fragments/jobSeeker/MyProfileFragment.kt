package com.softsolution.goseek.fragments.jobSeeker

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toFile
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.jaiselrahman.filepicker.activity.FilePickerActivity
import com.jaiselrahman.filepicker.config.Configurations
import com.jaiselrahman.filepicker.model.MediaFile
import com.softsolution.goseek.Interface.CallFragmentInterface
import com.softsolution.goseek.R
import com.softsolution.goseek.base.BaseFragment
import com.softsolution.goseek.databinding.FragmentMyProfileBinding
import com.softsolution.goseek.model.User
import com.softsolution.goseek.network.LocalPreference
import com.softsolution.goseek.network.NetworkClass
import com.softsolution.goseek.network.Response
import com.softsolution.goseek.network.URLApi
import com.softsolution.goseek.utils.getCountryDialCode
import com.yesterselga.countrypicker.CountryPicker
import com.yesterselga.countrypicker.Theme
import org.json.JSONObject
import java.io.File


class MyProfileFragment : BaseFragment() {
    private var binding: FragmentMyProfileBinding? = null
    private val IMAGE_REQUEST_CODE = 1
    var listener: CallFragmentInterface? = null
    private var isProfileImageSelected: Boolean = false
    private var profile_image_file: File? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_profile, container, false)
        binding!!.fragment = this

        binding?.etName?.setText(LocalPreference.shared.user?.Name)
        binding?.etEmail?.setText(LocalPreference.shared.user?.Email)
        binding?.etPhoneNumber?.setText(LocalPreference.shared.user?.Phone)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        binding!!.profileImage.setOnClickListener {
            val intent = Intent(mActivity, FilePickerActivity::class.java)
            intent.putExtra(
                FilePickerActivity.CONFIGS, Configurations.Builder()
                    .setCheckPermission(true)
                    .setShowImages(true)
                    .setShowFiles(false)
                    .setShowAudios(false)
                    .setShowVideos(false)
                    .setIgnoreNoMedia(false)
                    .enableImageCapture(true)
                    .setMaxSelection(1)
                    .setSkipZeroSizeFiles(true)
                    .setIgnoreHiddenFile(false)
                    .build()
            )
            startActivityForResult(intent, IMAGE_REQUEST_CODE)
        }
        binding?.tvCountry?.text = "+${requireActivity().getCountryDialCode()}"
        binding?.rlCountryPicker?.setOnClickListener {
            val picker = CountryPicker.newInstance("Select Country", Theme.LIGHT)

            picker.setListener { name, code, dialCode, flagDrawableResID ->
                binding?.tvCountry?.text = dialCode
                picker.dismiss()
            }
            picker.setStyle(DialogFragment.STYLE_NORMAL, R.style.countrypicker_style)
            picker.show(requireActivity().supportFragmentManager, "Select Country")


            binding?.btnUpdate?.setOnClickListener {
                editProfile(
                    binding?.etName?.editableText.toString().trim(),
                    binding?.etEmail?.editableText.toString().trim(),
                    binding?.etPhoneNumber?.editableText.toString().trim(),
                    LocalPreference.shared.user?.MemberId.toString()
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            val profile_image_selected: List<MediaFile>? =
                data.getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES)
            if (profile_image_selected != null && profile_image_selected.size ?: 0 > 0) {
                isProfileImageSelected = true
                profile_image_file = File(profile_image_selected.firstOrNull()?.path ?: "")
                binding?.profileImage?.let {
                    Glide.with(mActivity)
                        .load(profile_image_file?.path)
                        //                    .placeholder(R.drawable.person_placeholder)
                        .into(it)
                }

            }
        }

    }

    fun onClick(view: View) {
        when (view.id) {

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

    override fun onResume() {
        super.onResume()
//        binding?.profileImage?.let {
//            Glide.with(mActivity)
//                .load(profile_image_file?.path)
//                //                    .placeholder(R.drawable.person_placeholder)
//                .into(it)
//        }

    }

    private fun editProfile(
        UserName: String,
        Email: String,
        Phone: String,
        MemberId: String
    ) {
        showLoading()
        NetworkClass.callApi(
            URLApi.editCompanyProfile(
                UserName,
                Email,
                Phone,
                MemberId,
                Description = null
            ), object : Response {
                override fun onSuccessResponse(response: String?, message: String) {
                    hideLoading()
                    val json = JSONObject(response ?: "")
                    val data = Gson().fromJson(json.toString(), User::class.java)
                    LocalPreference.shared.user = data
                    binding?.etName?.setText(data.Name)
                    binding?.etEmail?.setText(data.Email)
                    binding?.etPhoneNumber?.setText(data.Phone)
                    Toast.makeText(mActivity, "Profile Successfully Updated.", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onErrorResponse(error: String?, response: String?) {
                    hideLoading()
                    Toast.makeText(mActivity, error ?: "", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun uploadProfileImage(memberId: String, Image: File) {
        showLoading()
        NetworkClass.callFileUpload(
            URLApi.uploadProfileImage(memberId), Image,
            "ProfileImage",
            object : Response {
                override fun onSuccessResponse(response: String?, message: String) {
                    hideLoading()
                    Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show()
                }

                override fun onErrorResponse(error: String?, response: String?) {
                    hideLoading()
                    Toast.makeText(mActivity, error ?: "", Toast.LENGTH_SHORT).show()
                }

            })
    }
}
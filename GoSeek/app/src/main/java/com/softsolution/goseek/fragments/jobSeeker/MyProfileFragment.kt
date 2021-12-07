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
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.softsolution.goseek.Interface.CallFragmentInterface
import com.softsolution.goseek.R
import com.softsolution.goseek.databinding.FragmentMyProfileBinding
import com.softsolution.goseek.utils.getCountryDialCode
import com.yesterselga.countrypicker.CountryPicker
import com.yesterselga.countrypicker.Theme


class MyProfileFragment : Fragment() {
    private var binding: FragmentMyProfileBinding? = null
    private val pickImage = 100
    var listener: CallFragmentInterface? = null
    private var imageUri: Uri? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_profile, container, false)
        binding!!.setFragment(this)

        init()

        return binding!!.getRoot()
    }

    private fun init() {
        binding!!.profileImage.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
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
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            binding!!.profileImage.setImageURI(imageUri)
        }
    }

    fun onClick(view: View) {
        when (view?.id) {
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
}
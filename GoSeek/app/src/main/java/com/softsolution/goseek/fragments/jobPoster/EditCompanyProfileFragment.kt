package com.softsolution.goseek.fragments.jobPoster

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.softsolution.goseek.Interface.CallFragmentInterface
import com.softsolution.goseek.R
import com.softsolution.goseek.databinding.FragmentEditCompanyProfileBinding
import com.softsolution.goseek.databinding.FragmentMyProfileBinding

class EditCompanyProfileFragment : Fragment() {
    private var binding: FragmentEditCompanyProfileBinding? = null
    private val pickImage = 100
    var listener: CallFragmentInterface? = null
    private var imageUri: Uri? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_company_profile, container, false)
        binding!!.setFragment(this)

        init()

        return binding!!.getRoot()
    }

    private fun init() {
        binding!!.profileImage.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == pickImage) {
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
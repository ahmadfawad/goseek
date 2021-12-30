package com.softsolution.goseek.fragments.jobSeeker

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toFile
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.jaiselrahman.filepicker.activity.FilePickerActivity
import com.jaiselrahman.filepicker.config.Configurations
import com.softsolution.goseek.Interface.CallFragmentInterface
import com.softsolution.goseek.R
import com.softsolution.goseek.base.BaseFragment
import com.softsolution.goseek.databinding.FragmentUploadResumeBinding
import com.softsolution.goseek.network.NetworkClass
import com.softsolution.goseek.network.Response
import com.softsolution.goseek.network.URLApi
import java.io.File


class UploadResumeFragment : BaseFragment() {

    private var binding: FragmentUploadResumeBinding? = null
    var listener: CallFragmentInterface? = null
    private val FILE_REQUEST_CODE = 1
    private var pdfFile : Uri? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_upload_resume, container, false)
        binding!!.fragment = this

        return binding!!.root

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as CallFragmentInterface
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == FILE_REQUEST_CODE) {
            pdfFile = data?.data

        }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.chooseFile ->{
                val intent = Intent(mActivity, FilePickerActivity::class.java)
                intent.putExtra(
                    FilePickerActivity.CONFIGS, Configurations.Builder()
                        .setCheckPermission(true)
                        .setShowImages(false)
                        .setShowFiles(true)
                        .setShowAudios(false)
                        .setShowVideos(false)
                        .setIgnoreNoMedia(false)
                        .enableImageCapture(true)
                        .setMaxSelection(1)
                        .setSkipZeroSizeFiles(true)
                        .setIgnoreHiddenFile(false)
                        .build()
                )
                startActivityForResult(intent, FILE_REQUEST_CODE)
            }
            R.id.back -> {
                this.findNavController().popBackStack()
             //   listener!!.passFragmentCallback("uploadResume")
            }
        }
    }
    private fun uploadResume(memberId : String, pdfFile : Uri){
        showLoading()
        NetworkClass.callFileUpload(URLApi.uploadResume(memberId),pdfFile.toFile(),"Pdf",object: Response {
            override fun onSuccessResponse(response: String?, message: String) {
                hideLoading()
            }

            override fun onErrorResponse(error: String?, response: String?) {
                hideLoading()
            }

        })
    }
}
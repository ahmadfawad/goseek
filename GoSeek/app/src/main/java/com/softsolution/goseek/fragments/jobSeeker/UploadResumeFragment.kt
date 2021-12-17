package com.softsolution.goseek.fragments.jobSeeker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jaiselrahman.filepicker.activity.FilePickerActivity
import com.jaiselrahman.filepicker.config.Configurations
import com.softsolution.goseek.Interface.CallFragmentInterface
import com.softsolution.goseek.R
import com.softsolution.goseek.base.BaseFragment
import com.softsolution.goseek.databinding.FragmentUploadResumeBinding


class UploadResumeFragment : BaseFragment() {

    private var binding: FragmentUploadResumeBinding? = null
    var listener: CallFragmentInterface? = null
    private val FILE_REQUEST_CODE = 1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_upload_resume, container, false)
        binding!!.setFragment(this)

        return binding!!.getRoot()

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as CallFragmentInterface
    }

    fun onClick(view: View) {
        when (view?.id) {
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
}
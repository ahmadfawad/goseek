package com.softsolution.goseek.loader

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import com.github.ybq.android.spinkit.style.Circle
import com.softsolution.goseek.base.BaseDialogueFragment
import com.softsolution.goseek.R


class ProgressDialogue : BaseDialogueFragment() {

//    private lateinit var progressbar : Dialog

    var txtProgress: AppCompatTextView? = null
    var mCircle: Circle? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.loader_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mCircle = Circle()
        mCircle?.setBounds(0, 0, 100, 100)
        mCircle?.color = Color.WHITE
        mCircle?.color = R.color.white
//        progressbar = mView.findViewById(R.id.progress)
//        progressbar.settype(Type.INTERWIND)
//        progressbar.setdurationTime(100)
//
        txtProgress = mView.findViewById(R.id.txtProgress)
    }

    override fun onResume() {
        super.onResume()
        startAnim()
    }

    override fun onPause() {
        super.onPause()
        stopAnim()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stopAnim()
    }

    private fun startAnim() {
//        avi?.show()
        mCircle?.start()
//        progressbar.show()

    }

    private fun stopAnim() {
//        avi?.hide()
        mCircle?.stop()
//        progressbar.gone()

    }
}
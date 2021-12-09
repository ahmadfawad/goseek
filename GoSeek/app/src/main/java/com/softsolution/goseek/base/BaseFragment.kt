@file:Suppress("DEPRECATION")

package com.softsolution.goseek.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment


abstract class BaseFragment : Fragment() {
    lateinit var mContext: Context
    lateinit var mActivity: BaseActivity
    public lateinit var mView: View
    var isNewScreenOpen: Boolean = false
    var newScreenOpened: ((Boolean) -> Unit)? = null
    val isIniteil: Boolean
        get() {
            return ::mView.isInitialized
        }

    open
    fun openPreviousState() {

    }

    fun showLoading(text: String = "Please wait...") {
        mActivity.showLoading(text)
    }



    fun hideLoading() {
        mActivity.hideLoading()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        mActivity = context as BaseActivity
    }


    protected open fun onBecameVisibleToUser() {
        //
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        HideUtil.init(mActivity)
        mView = view
    }

    protected open fun onRecycle() {
        //
    }

    final override fun onDestroy() {
        onRecycle()
        super.onDestroy()
    }

    protected open fun onBecameInvisibleToUser() {
        //
    }



    final override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            onBecameVisibleToUser()
        } else {
            onBecameInvisibleToUser()
        }
    }
}

interface CanManagePlayback {

    fun startPlayback()

    fun stopPlayback()

}
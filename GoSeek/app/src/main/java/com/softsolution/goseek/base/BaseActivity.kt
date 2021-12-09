@file:Suppress("unused", "UNUSED_PARAMETER")

package com.softsolution.goseek.base


import android.app.Dialog
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.view.*
import android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.loader.content.CursorLoader
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.softsolution.goseek.R
import com.softsolution.goseek.loader.ProgressDialogue
import com.softsolution.goseek.network.BaseApplication
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList


@Suppress("unused")
abstract class BaseActivity : AppCompatActivity() {


    private val pd = ProgressDialogue()
    private lateinit var mProgressBar: Dialog


    fun View.snack(message: String, duration: Int = Snackbar.LENGTH_LONG) {
        Snackbar.make(this, message, duration).show()
    }

    fun statusBarColor(color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.setStatusBarColor(color)
        }
    }

    //
    fun makeTopBottomTransparent() {
        val w: Window = window // in Activity's onCreate() for instance
        w.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,// or
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )// For Display Image View On Status Bar Background
    }

    fun getBitmap(path: String): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            var f: File = File(path)
            var options = BitmapFactory.Options()
            options.inPreferredConfig = Bitmap.Config.ARGB_8888
            bitmap = BitmapFactory.decodeStream(FileInputStream(f), null, options)
        } catch (e: Exception) {

        }
        return bitmap
    }

    open fun getWidthOrHeight(bmp: Bitmap, widthheight: String): Int? {
        var mWidthHeight: Int? = 0
        try {
            if (widthheight.equals("width")) {
                mWidthHeight = bmp?.width
            } else if (widthheight.equals("height")) {
                mWidthHeight = bmp?.height
            }
        } catch (e: FileNotFoundException) {

        } catch (e: IOException) {

        } catch (e: RuntimeException) {

        } finally {
        }
        return mWidthHeight
    }


    fun makeLightContentStatusBar() {
        setSystemUiLightStatusBar(true)
    }

    private fun setSystemUiLightStatusBar(isLightStatusBar: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val systemUiAppearance = if (isLightStatusBar) {
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                } else {
                    0
                }
                window.insetsController?.setSystemBarsAppearance(
                    systemUiAppearance,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )
            } else {
                val systemUiVisibilityFlags = if (isLightStatusBar) {
                    window.decorView.systemUiVisibility or SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                } else {
                    window.decorView.systemUiVisibility and SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
                }
                window.decorView.systemUiVisibility = systemUiVisibilityFlags
            }
        }
    }

    fun makeDarkContentStatusBar() {
        setSystemUiLightStatusBar(false)


    }


    fun showLoading(text: String = "Please wait...") {
        runOnUiThread {
            try {
                if (!pd.isVisible && !pd.isAdded) {
                    pd.show(supportFragmentManager, "pd")
                }
                Handler(Looper.getMainLooper()).postDelayed({
                    pd.txtProgress?.text = text
                }, 200)
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            }
        }
    }

    fun hideLoading() {
        runOnUiThread {
            try {
                if (pd.isAdded || pd.isVisible) pd.dismiss()
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            }
        }
    }

    fun replaceFragment(
        fragment: Fragment?,
        tagForBackStack: String? = null,
        idRepalce: Int = R.id.action_container
    ) {
        val transaction = supportFragmentManager.beginTransaction()
        if (tagForBackStack != null) {
            transaction.addToBackStack(tagForBackStack)
        }
        transaction.replace(idRepalce, fragment ?: Fragment())
        transaction.commitAllowingStateLoss()
    }

    fun addFragment(
        fragment: Fragment?,
        tagForBackStack: String? = null,
        idRepalce: Int = R.id.action_container
    ) {
        val transaction = supportFragmentManager.beginTransaction()
        if (tagForBackStack != null) {
            transaction.addToBackStack(tagForBackStack)
        }
        transaction.add(idRepalce, fragment ?: Fragment())
        transaction.commitAllowingStateLoss()
    }


    protected open fun onRecycle() {
        //
    }

    override fun onDestroy() {
        onRecycle()
        super.onDestroy()
    }

    protected open fun onBecameInvisibleToUser() {
        //
    }

    protected open fun onBecameVisibleToUser() {
        //
    }


    fun <T> generateList(response: String, type: Class<Array<T>>): ArrayList<T> {
        val arrayList = ArrayList<T>()
        if (response.isEmpty() || response == "null" || response == "\"[]\"") {
            return arrayList
        }
        arrayList.addAll(listOf(*Gson().fromJson<Array<T>>(response, type)))
        return arrayList
    }

    fun Any.toNotNullString(): String {

        return if (this == "null") {
            this.toString().replace("null", "")
        } else {
            this.toString()
        }
    }

    fun <T> List<T>.toArrayList(): ArrayList<T> {
        return ArrayList(this)
    }

    private fun File.writeBitmap(bitmap: Bitmap, format: Bitmap.CompressFormat, quality: Int) {
        outputStream().use { out ->
            bitmap.compress(format, quality, out)
            out.flush()
        }
    }
}


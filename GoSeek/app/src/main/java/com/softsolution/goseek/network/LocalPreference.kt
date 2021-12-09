package com.softsolution.goseek.network

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import com.google.gson.Gson
import com.softsolution.goseek.model.User


class LocalPreference(private val mContext: Context?) {
    fun removeAll() {
        editor?.apply {
            clear()
            apply()
        }

    }


    var user: User?
        get() {
            val stUser = preferences?.getString("UserObje", "") ?: ""
            if (stUser.isEmpty()) {
                return null
            }
            return Gson().fromJson(stUser, User::class.java)
        }
        set(newValue) {
            val userString = Gson().toJson(newValue)
            editor?.apply {
                putString("UserObje", userString)
                apply()
            }
        }




    var token: String?
        get() = preferences?.getString("AuthToken", "") ?: ""
        set(token) {
            editor?.apply {
                putString("AuthToken", token)
                apply()
            }
        }
    var emergencyNumber: String?
        get() = preferences?.getString("emergencyNumber", "") ?: ""
        set(token) {
            editor?.apply {
                putString("emergencyNumber", token)
                apply()
            }
        }
    var name: String?
        get() = preferences?.getString("name", "") ?: ""
        set(token) {
            editor?.apply {
                putString("name", token)
                apply()
            }
        }
    private var preferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    var portNumber: Int?
        get() = preferences?.getInt("PORT_NUMBER", 0)
        set(number) {
            editor?.putInt("PORT_NUMBER", number ?: 0)
            editor?.apply()
        }

    companion object {
        @JvmField
        var shared = LocalPreference(BaseApplication.instance)
    }

    init {
        preferences = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            mContext?.getSharedPreferences(
                BaseApplication.instance?.packageName, Context.MODE_PRIVATE
            )
        } else {
            mContext?.getSharedPreferences(
                BaseApplication.instance?.packageName, Context.MODE_PRIVATE
            )
        }
        editor = preferences?.edit()
        editor?.apply()
    }
}
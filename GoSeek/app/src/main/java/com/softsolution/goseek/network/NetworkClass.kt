@file:Suppress("unused")

package com.softsolution.goseek.network

import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.androidnetworking.interfaces.StringRequestListener
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.text.MessageFormat

class NetworkClass {
    private var callBack: Response? = null

    private fun handleResponse(response: JSONObject) {

        val message: String = response.optString("Message", "") ?: ""
        val status: Int = response.optInt("StatusCode", 0)
        val result: Any = if (response.has("data")) response.get("data") else ""
        if (callBack != null) {
            if (status == 1) {
                if (result is String && result == "Invalid User Token") {
                    Log.w(TAG, MessageFormat.format("APICall{0}", response))
                    callBack?.onErrorResponse("Invalid User Token")
                } else {
                    callBack?.onSuccessResponse(result.toString(), message)
                }
            } else {
                Log.w(TAG, MessageFormat.format("APICall{0}", response))
                callBack?.onErrorResponse(message, result.toString())
            }
        }
    }

    private fun handleError(error: ANError) {
        Log.w(TAG, MessageFormat.format("APICall{0}", error))
        if (error.errorBody != null) {
            if (callBack != null) {
                try {
                    val jsonObject = JSONObject(error.errorBody)
                    val message =
                        jsonObject.optString("message") ?: jsonObject.optString("exception") ?: ""
                    val result: Any = if (jsonObject.has("data")) jsonObject.get("data") else ""
                    callBack?.onErrorResponse(
                        if (message.isEmpty()) jsonObject.toString() else message,
                        response = result.toString()
                    )
                } catch (e: JSONException) {
                    e.printStackTrace()
                    callBack?.onErrorResponse(error.errorBody)
                }
            }
        } else {
            if (callBack != null) {
                callBack?.onErrorResponse(error.errorDetail)
            }
        }
    }

    @Deprecated("not Using right now")
    private var responseStringListener: StringRequestListener = object : StringRequestListener {
        override fun onResponse(responseValue: String) {
            val response = JSONObject(responseValue)
            handleResponse(response)
        }

        override fun onError(error: ANError) {
            handleError(error)
        }
    }

    private var responseListener: JSONObjectRequestListener = object : JSONObjectRequestListener {
        override fun onResponse(response: JSONObject) {
            handleResponse(response)
        }

        override fun onError(error: ANError) {
            handleError(error)
        }
    }

    companion object {
        val TAG = NetworkClass::class.java.toString()
        fun callFileUpload(
            baseLink: URLApi, file: File,
            name: String = "profile_image", callBack: Response?
        ) {
            val call = NetworkClass()
            call.callBack = callBack
            val token = LocalPreference.shared.token
            val headers = HashMap<String, String>()
            if (token?.isNotEmpty() == true) {
                headers["Authorization"] = "Bearer ${token.trim()}"
//                headers["session_token"] = "$token"
            }
//            headers["Accept"] = "application/json"
//            headers["Content-Type"] = "application/json"
            Log.w(TAG, MessageFormat.format("APICall{0} : {1}", URLApi.param(), baseLink))
            AndroidNetworking.upload(URLApi.link())
                .addHeaders(headers)
//                .addPathParameter(baseLink.param())
                .addMultipartFile(name, file)
                .addMultipartParameter(URLApi.paramHas())
                .setTag(baseLink)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(call.responseListener)
//                .addMulti
        }


        fun callFileUpload(
            baseLink: URLApi,
            file: ArrayList<File>,
            name: String = "lab_tests[]",
            callBack: Response?
        ) {
            val call = NetworkClass()
            call.callBack = callBack
            val token = LocalPreference.shared.token
            val headers = HashMap<String, String>()
            if (token?.isNotEmpty() == true) {
                headers["Authorization"] = "Bearer ${token.trim()}"
//                headers["session_token"] = "$token"
            }
//            headers["Accept"] = "application/json"
//            headers["Content-Type"] = "application/json"
            Log.w(TAG, MessageFormat.format("APICall{0} : {1}", URLApi.param(), baseLink))
            AndroidNetworking.upload(URLApi.link())
                .addHeaders(headers)
                .addMultipartFileList(name, file)
//                .addPathParameter(headers)
                .addMultipartParameter(URLApi.paramHas())
//                .addMultipartParameter(headers)
                .setTag(baseLink)
//                .setContentType("application/json")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(call.responseListener)
//                .addMulti
        }


        fun callApi(
            baseLink: URLApi, callBack: Response?
        ) {
            val call = NetworkClass()
            call.callBack = callBack
            val token = "31bf3856ad364e35"
            val headers = HashMap<String, String>()
            if (token?.isNotEmpty()) {
//                headers["Authorization"] = "31bf3856ad364e35"
                headers["Authorization"] = "$token"
            }
            headers["Accept"] = "application/json"
            headers["Content-type"] = "application/json"

            Log.d(
                TAG,
                MessageFormat.format(
                    "APICall{0} : {1} , {2}",
                    URLApi.param(),
                    URLApi.link(),
                    URLApi.method
                )
            )
            when (URLApi.method) {
                NetworkMethod.POST -> AndroidNetworking.post(URLApi.link())
                    .addHeaders(headers)
                    .addJSONObjectBody(URLApi.param())
                    .setTag(URLApi.link())
                    .setContentType("application/json")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(call.responseListener)
                NetworkMethod.GET -> {
                    AndroidNetworking.get(URLApi.link())
                        .addHeaders(headers)
                        .setTag(URLApi.link())
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(call.responseListener)
                }
                NetworkMethod.DELETE -> AndroidNetworking.delete(URLApi.link())
                    .addHeaders(headers)
                    .setTag(URLApi.link())
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(call.responseListener)
                NetworkMethod.PUT -> AndroidNetworking.put(URLApi.link())
                    .addHeaders(headers)
                    .addJSONObjectBody(URLApi.param())
                    .setTag(URLApi.link())
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(call.responseListener)
                NetworkMethod.PATCH -> AndroidNetworking.patch(URLApi.link())
                    .addHeaders(headers)
                    .addJSONObjectBody(URLApi.param())
                    .setTag(URLApi.link())
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(call.responseListener)
            }
        }
    }
}

enum class NetworkMethod {
    POST, GET, DELETE, PUT, PATCH
}

interface Response {
    fun onSuccessResponse(response: String?, message: String)
    fun onErrorResponse(error: String?, response: String? = "")
}
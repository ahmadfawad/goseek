@file:Suppress("SpellCheckingInspection", "unused", "FunctionName")

package com.softsolution.goseek.network


import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject


enum class ShareTypes(var value: String) {
    product("product"),
    post("post"),
    event("event"),
}


enum class UserType(var value: String) {
    Personal("personal"),
    Vendor("vendor")
}

@Suppress("UNUSED_PARAMETER")
object URLApi {


    private val TAG = URLApi::class.java.toString()

    const val SOCKET_URL = "http://45.56.122.34:1028"
    public const val BaseUrl =
        "http://apigoseek.superstudy.pk/api/" //Base URL here
    private var path: String = ""
    private var params: JSONObject = JSONObject()
    var method: NetworkMethod = NetworkMethod.GET
    fun link(): String {
        return BaseUrl + path
    }

    fun param(): JSONObject {
        return params
    }

    final fun paramHas(): HashMap<String, Any>? {
        return Gson().fromJson<HashMap<String, Any>>(
            param().toString(), object : TypeToken<HashMap<String?, Any?>?>() {}.type
        )
    }

    fun shareDeepLink(id: String, type: ShareTypes, userKind: UserType): String {
        return when (type) {
            ShareTypes.product -> {
                BaseUrl + "products/${id}"
            }
            ShareTypes.post -> {
                if (userKind == UserType.Vendor) {
                    BaseUrl + "post-details/${id}"//"vendor/postdetails?id=\(id)"
                } else {
                    BaseUrl + "post-details/${id}"//"user/postdetails?id=\(id)"
                }
            }
            ShareTypes.event -> {
                BaseUrl + "details?id=${id}&type=${type.value}"
            }
        }

    }



    fun registerUser(username: String, email: String, phone: String, password: String, isActive : String): URLApi {
        method = NetworkMethod.POST
        path = "Account/Register?="
        params = JSONObject()
        params.put("UserName", username)
        params.put("email", email)
        params.put("Phone", phone)
        params.put("Password", password)
        params.put("isActive", isActive)
        return this
    }

    fun optVerify(code : String):URLApi{
        method = NetworkMethod.GET
        path = "/Account/VarifyCode?ActivetionCode=$code"
        params = JSONObject()
//        params.put("ActivetionCode",code)
        return this
    }




}


object Randomized {
    fun generate(min: Int, max: Int): Int {
        return min + (Math.random() * (max - min + 1)).toInt()
    }
}

enum class PaymentMethod(val value: String) {
    STRIPE("stripe"),
    VESICASH("vesicash"),
    FREE("free"),
    KOINAHI("empty")
}

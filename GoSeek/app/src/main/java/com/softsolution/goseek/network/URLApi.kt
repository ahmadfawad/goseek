@file:Suppress("SpellCheckingInspection", "unused", "FunctionName")

package com.softsolution.goseek.network


import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import java.net.URL


@Suppress("UNUSED_PARAMETER")
object URLApi {


    private val TAG = URLApi::class.java.toString()

    const val SOCKET_URL = "http://45.56.122.34:1028"
    public const val BaseUrl =
        "https://apigoseek.superstudy.pk/" //Base URL here
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


    fun registerUser(
        username: String,
        email: String,
        phone: String,
        password: String,
    ): URLApi {
        method = NetworkMethod.POST
        path = "Register"
        params = JSONObject()
        params.put("UserName", username)
        params.put("email", email)
        params.put("Phone", phone)
        params.put("Password", password)
        return this
    }

    fun registerCompany(
        username: String,
        email: String,
        phone: String,
        password: String,

        ): URLApi {
        method = NetworkMethod.POST
        path = "CompanyRegister"
        params = JSONObject()
        params.put("BusinessName", username)
        params.put("email", email)
        params.put("Phone", phone)
        params.put("Password", password)
        return this
    }

    fun optVerify(code: String, memberId: String): URLApi {
        method = NetworkMethod.GET
        path = "VarifyCode?ActivetionCode=$code&MemberId=$memberId"
        params = JSONObject()
        return this
    }

    fun login(email: String, password: String): URLApi {
        method = NetworkMethod.GET
        path = "Login?Email=$email&Password=$password"
        params = JSONObject()
        return this

    }

    fun sendPassword(email: String): URLApi {
        method = NetworkMethod.POST
        path = "ForgetPassword"
        params = JSONObject()
        params.put("Email", email)
        return this
    }

    fun changePassword(
        oldPassword: String,
        password: String,
        confirmPassword: String,
        memberId: String
    ): URLApi {
        method = NetworkMethod.POST
        path =
            "ChangeUserProfilePassword?old=$oldPassword&newpass=$password&confrim=$confirmPassword"
        params = JSONObject()
        params.put("MemberId", memberId)
        return this
    }

    fun companyJobList(memberId: String, status: Int, page: Int): URLApi {
        method = NetworkMethod.GET
        path = "GetCompanyJobList?MemberId=$memberId&status=$status&page=$page"
        params = JSONObject()
        return this
    }

    fun postJob(jobTitle: String, JobDescription: String, Wages: String, memberId: String): URLApi {
        method = NetworkMethod.POST
        path = "AddNewJob"
        params = JSONObject()
        params.put("jobTitle", jobTitle)
        params.put("JobDescription", JobDescription)
        params.put("Wages", Wages)
        params.put("MemberId", memberId)
        return this
    }

    fun editCompanyProfile(
        BusinessName: String,
        Email: String,
        Phone: String,
        MemberId: String,
        Description: String? = null,
    ): URLApi {
        method = NetworkMethod.POST
        path = "CompanyProfileMember"
        params = JSONObject()
        if (LocalPreference.shared.user?.status == 2) {
            params.put("BusinessName  ", BusinessName)
        } else {
            params.put("UserName  ", BusinessName)
        }
        params.put("Email  ", Email)
        params.put("Phone   ", Phone)
        params.put("MemberId ", MemberId)
        if (Description.isNullOrEmpty()) {
        } else {
            params.put("Description  ", Description)
        }

        return this
    }

    fun getJobDetails(companyJobId: Int): URLApi {
        method = NetworkMethod.GET
        path = "jobdetail?CompanyJobId=$companyJobId"
        params = JSONObject()
        return this
    }
    fun uploadProfileImage(memberId: String):URLApi{
        method = NetworkMethod.POST
        path = "updateProfileImage"
        params = JSONObject()
        params.put("MemberId",memberId)
        return this
    }

    fun uploadResume(memberId: String):URLApi{
        method = NetworkMethod.POST
        path = "uploadresume"
        params = JSONObject()
        params.put("MemberId",memberId)
        return this

    }
}


object Randomized {
    fun generate(min: Int, max: Int): Int {
        return min + (Math.random() * (max - min + 1)).toInt()
    }
}

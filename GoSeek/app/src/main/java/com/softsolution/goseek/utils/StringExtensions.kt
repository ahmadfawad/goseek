package com.softsolution.goseek.utils

import android.content.Context
import android.telephony.TelephonyManager
import com.softsolution.goseek.R
import java.util.*

fun Context.getCountryDialCode(): String? {
    var contryId: String? = null
    var contryDialCode: String? = "1"
    val telephonyMngr = this.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    contryId = telephonyMngr.simCountryIso.toUpperCase(Locale.getDefault())
    val arrContryCode: Array<String> =
        this.resources.getStringArray(R.array.DialingCountryCode)
    for (i in arrContryCode.indices) {
        val arrDial = arrContryCode[i].split(",".toRegex()).toTypedArray()

        if (arrDial[1].trim { it <= ' ' } == contryId.trim()) {
            contryDialCode = arrDial[0]
            break
        }
    }
    return contryDialCode ?: "1"
}
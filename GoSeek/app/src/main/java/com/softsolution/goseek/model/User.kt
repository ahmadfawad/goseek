package com.softsolution.goseek.model

import java.io.Serializable

data class User(
    val Email: String? = null,
    val IsVerify: Boolean? = null,
    val MemberId: Int? = null,
    val Name: String? = null,
    val Phone: String? = null,
    val ProfileImage: String? = null,
    val status: Int? = null,
    val Description: String? = null
):Serializable

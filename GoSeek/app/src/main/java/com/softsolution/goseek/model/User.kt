package com.softsolution.goseek.model

data class User(
    val ActivetionCode: String? = null,
    val BusinessName: Any? = null,
    val CompanyJobs: List<Any>,
    val CopanyMapLocations: List<Any>,
    val Description: Any,
    val Email: String? = null,
    val FavouriteJobs: List<Any>,
    val IsActive: Any? = null,
    val MemberId: Int? = null,
    val Password: Any? = null,
    val Phone: String? = null,
    val ProfileImage: Any? = null,
    val Resumes: List<Any>? = null,
    val UserName: String? = null,
    val UserReviews: List<Any>
)
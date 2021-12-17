package com.softsolution.goseek.model.jobPosterModel

import java.io.Serializable

data class PostedData (
    var CompanyJobId: Int? = null,
    var JobTitle: String? = null,
    var JobDescription: String? = null,
    var Wages: String? = null,
    var CreatedDate: String? = null,
    var ststus: String? = null,
    var Count: Int? = null,
    var CompanyName:String? = null,
    var Location:String? = null
 ):Serializable
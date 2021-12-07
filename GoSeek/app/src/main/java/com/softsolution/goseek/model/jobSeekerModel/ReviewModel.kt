package com.softsolution.goseek.model.jobSeekerModel

import java.io.Serializable

data class ReviewModel(
    var review:String? = null,
    var rating:Int? = null,
    var review_date:String? = null,
    var review_writer_name:String? = null
)
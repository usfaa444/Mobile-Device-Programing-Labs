package com.usfaa.cvbuilder.data.models

data class Education(
    var id: Long = System.currentTimeMillis(),
    var image: String,
    var institute: String,
    var degree: String
)

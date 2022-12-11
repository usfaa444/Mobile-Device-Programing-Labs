package com.usfaa.cvbuilder.data.models

data class Work(
    var id: Long,
    var image: String,
    var dateFrom: String,
    var dateTo: String,
    var location: String,
    var title: String,
    var company: String,
    var skills: String
)
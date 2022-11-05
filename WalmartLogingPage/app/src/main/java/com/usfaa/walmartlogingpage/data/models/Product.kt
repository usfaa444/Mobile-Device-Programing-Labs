package com.usfaa.walmartlogingpage.data.models

data class Product(
    var title: String,
    var price: Double,
    var color: String,
    var image: Int,
    var itemId: String,
    var description: String
): java.io.Serializable

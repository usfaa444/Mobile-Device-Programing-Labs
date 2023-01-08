package com.usfaa.quizzapp.data.models

import java.util.Objects

data class Question (
    var id: Long,
    var question: String,
    var response: MutableList<Response>
    ) {

    override fun hashCode(): Int {
        return Objects.hash(id, question, response)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Question

        if (id != other.id) return false
        if (question != other.question) return false
        if (response != other.response) return false

        return true
    }
}
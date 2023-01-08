package com.usfaa.quizzapp.data.models

data class Result(
    val question: Question,
    val correctAnswer: Response,
    val yourAnswer: Response
)

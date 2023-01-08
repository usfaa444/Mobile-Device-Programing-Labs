package com.usfaa.quizzapp.data.models

data class ResultAnalysisData(
    val questions: List<Question>,
    val correctAnswers: List<Response>,
    val submittedAnswers: List<Response>
)

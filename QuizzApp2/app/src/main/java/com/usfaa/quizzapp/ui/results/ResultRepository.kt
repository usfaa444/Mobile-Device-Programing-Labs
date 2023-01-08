package com.usfaa.quizzapp.ui.results

import com.usfaa.quizzapp.data.models.Response
import com.usfaa.quizzapp.data.room.quiz.QuizDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ResultRepository @Inject constructor(
    private val quizDAO: QuizDAO) {

    suspend fun getQuestionCorrectAnswer(questionId: Long): Response? {
        return withContext(Dispatchers.IO) {
            return@withContext quizDAO.getQuestionCorrectResponse(questionId)
        }
    }
}
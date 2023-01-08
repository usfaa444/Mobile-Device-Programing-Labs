package com.usfaa.quizzapp.ui.quizz

import com.usfaa.quizzapp.data.models.Question
import com.usfaa.quizzapp.data.models.Response
import com.usfaa.quizzapp.data.room.quiz.QuizDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class QuizRepository @Inject constructor(
    private val quizDAO: QuizDAO
) {
    suspend fun getAllQuestions() : List<Question> {
        return withContext(Dispatchers.IO) {
            val questionData = quizDAO.getAllQuestions()
            val questions = mutableListOf<Question>()
            questionData?.let { qs ->
                qs.forEach { q ->
                    val responses = quizDAO.getResponsesByQuestionId(q.id)
                    responses?.let { r ->
                        questions.add(Question(q.id, q.question, r as MutableList<Response>))
                    }
                }
            }
            return@withContext questions
        }
    }
}
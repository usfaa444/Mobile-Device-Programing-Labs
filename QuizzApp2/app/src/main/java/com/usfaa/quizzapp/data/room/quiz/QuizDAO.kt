package com.usfaa.quizzapp.data.room.quiz

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.usfaa.quizzapp.data.models.Question
import com.usfaa.quizzapp.data.models.QuestionData
import com.usfaa.quizzapp.data.models.Response

@Dao
interface QuizDAO {
    @Query("SELECT * from response WHERE questionId = :questionId")
    fun getResponsesByQuestionId(questionId: Long): List<Response>?

    @Query("SELECT * from response WHERE questionId = :questionId AND isCorrectAnswer = 1")
    fun getQuestionCorrectResponse(questionId: Long): Response?

    @Query("SELECT * from QuestionData")
    fun getAllQuestions(): List<QuestionData>?

    @Insert
    fun insertQuestion(vararg question: QuestionData)

    @Insert
    fun insertResponse(vararg response: Response)

    @Delete
    fun deleteQuestion(question: QuestionData)
}
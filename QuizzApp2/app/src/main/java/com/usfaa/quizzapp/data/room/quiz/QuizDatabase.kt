package com.usfaa.quizzapp.data.room.quiz

import androidx.room.Database
import androidx.room.RoomDatabase
import com.usfaa.quizzapp.data.models.QuestionData
import com.usfaa.quizzapp.data.models.Response

@Database(entities = [QuestionData::class, Response::class], version = 1)
abstract class QuizDatabase : RoomDatabase(){
    abstract fun getQuoteDAO(): QuizDAO
}
package com.usfaa.quizzapp.di.module

import android.content.Context
import androidx.room.Room
import com.usfaa.quizzapp.data.room.quiz.QuizDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    @Singleton
    fun providesRoomModule(@ApplicationContext context: Context): QuizDatabase {
        return Room.databaseBuilder(context,
                QuizDatabase::class.java,
                ROOM_QUIZ_DATABASE
                ).build()
    }

    @Provides
    @Singleton
    fun providesQuizDao(quizDatabase: QuizDatabase) = quizDatabase.getQuoteDAO()

    companion object{
        const val ROOM_QUIZ_DATABASE = "quiz.db"
    }
}
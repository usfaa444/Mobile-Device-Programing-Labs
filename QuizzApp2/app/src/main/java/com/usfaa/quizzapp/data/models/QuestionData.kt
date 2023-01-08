package com.usfaa.quizzapp.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class QuestionData(
    @PrimaryKey var id: Long,
    @ColumnInfo(name = "question") var question: String)

package com.usfaa.quizzapp.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Response(
    @PrimaryKey var id: Long,
    @ColumnInfo(name = "response") var response: String,
    @ColumnInfo(name = "questionId") var questionId: Long,
    @ColumnInfo(name = "isCorrectAnswer") var isCorrectAnswer: Boolean
) {
    @Ignore var isSelected: Boolean = false
}

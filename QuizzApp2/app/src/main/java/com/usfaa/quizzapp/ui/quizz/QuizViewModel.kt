package com.usfaa.quizzapp.ui.quizz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.usfaa.quizzapp.data.models.Question
import com.usfaa.quizzapp.data.models.QuestionData
import com.usfaa.quizzapp.data.models.Response
import com.usfaa.quizzapp.data.models.ResultAnalysisData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val repository: QuizRepository
) : ViewModel() {
    private val questions = MutableLiveData<List<Question>>()
    private val message = MutableLiveData<String>()
    private val answers = MutableLiveData<MutableMap<Question, Response>>()
    private val resultAnalysisData = MutableLiveData<ResultAnalysisData>()

    fun getQuestionsLiveData(): LiveData<List<Question>> = questions
    fun getMessageLiveData(): LiveData<String> = message
    fun getAnswersLiveData(): LiveData<MutableMap<Question, Response>> = answers
    fun getResultAnalysisData(): LiveData<ResultAnalysisData> = resultAnalysisData

    fun getQuestions() {
        viewModelScope.launch {
            questions.value = repository.getAllQuestions()
        }
    }

    fun submitAnswers(ans: MutableMap<Question, Response>) {
        answers.value = ans
    }

    fun submitAnalysisData(analysisData: ResultAnalysisData) {
        resultAnalysisData.value = analysisData
    }
}
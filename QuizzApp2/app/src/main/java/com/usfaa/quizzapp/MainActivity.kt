package com.usfaa.quizzapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.usfaa.quizzapp.data.SimpleQuizQuestions
import com.usfaa.quizzapp.data.room.quiz.QuizDAO
import com.usfaa.quizzapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    @Inject lateinit var quizDAO: QuizDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val questions = quizDAO.getAllQuestions()
                if (questions?.isEmpty() == true) {
                    SimpleQuizQuestions.questions.forEach { questionData ->
                        quizDAO.insertQuestion(questionData)
                    }
                    SimpleQuizQuestions.responses.forEach { response ->
                        quizDAO.insertResponse(response)
                    }
                }
            }
        }

    }
}
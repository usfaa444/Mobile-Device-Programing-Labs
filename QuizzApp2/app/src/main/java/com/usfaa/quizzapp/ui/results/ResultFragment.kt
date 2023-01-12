package com.usfaa.quizzapp.ui.results

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.usfaa.quizzapp.R
import com.usfaa.quizzapp.data.models.Question
import com.usfaa.quizzapp.data.models.Response
import com.usfaa.quizzapp.data.models.ResultAnalysisData
import com.usfaa.quizzapp.databinding.FragmentResultBinding
import com.usfaa.quizzapp.ui.quizz.QuizViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class ResultFragment : Fragment() {

    private lateinit var binding: FragmentResultBinding
    private val viewModel: QuizViewModel by activityViewModels()
    @Inject
    lateinit var repository: ResultRepository
    private var score = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        score = 0
        viewModel.getAnswersLiveData().observe(viewLifecycleOwner) { answers ->
            showResult(answers)
        }

        binding.cardViewTrayAgain.setOnClickListener {
            findNavController().popBackStack(R.id.navigation_quiz, false)
        }
        binding.cardViewResultAnalysis.setOnClickListener {
            val direction = ResultFragmentDirections.actionNavigationResultToNavigationAnalysis()
            findNavController().navigate(direction)
        }
    }

    private fun showResult(answers: MutableMap<Question, Response?>) {
        val correctAnswers = mutableListOf<Response>()
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                answers.keys.forEach { question ->
                    val correctAnswer = repository.getQuestionCorrectAnswer(question.id)
                    correctAnswer?.let {
                        correctAnswers.add(it)
                        if (it.isCorrectAnswer && answers[question] != null && it.id == answers[question]?.id) {
                            score++
                        }
                    }
                }
                withContext(Dispatchers.Main) {
                    binding.lblScore.text = "$score/${answers.keys.size}"
                    binding.lblTotalQuestion.text = "Total Questions : ${answers.keys.size}"
                    binding.lblWrongAnswers.text = "Wrong Answers : ${answers.keys.size - score}"
                    val resultAnalysisData = ResultAnalysisData(answers.keys.toList(), correctAnswers, answers.values.toList())
                    viewModel.submitAnalysisData(resultAnalysisData)
                }
            }
        }
    }

}
package com.usfaa.quizzapp.ui.quizz

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.usfaa.quizzapp.R
import com.usfaa.quizzapp.data.adapters.ResponseAdapter
import com.usfaa.quizzapp.data.models.Question
import com.usfaa.quizzapp.data.models.Response
import com.usfaa.quizzapp.databinding.FragmentQuizBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class QuizFragment : Fragment() {

    private val viewModel: QuizViewModel by activityViewModels()
    private lateinit var binding: FragmentQuizBinding
    private lateinit var responseAdapter: ResponseAdapter
    private var questionList = listOf<Question>()
    private var answers = mutableMapOf<Question, Response?>()
    private var actualQuestion = 0
    private var isResponseSelected = false
    private lateinit var countDown: CountDownTimer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            showExitDialog()
        }

        viewModel.getQuestions()
        actualQuestion = 0

        binding.recyclerViewResponses.setHasFixedSize(true)
        binding.recyclerViewResponses.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        setUpObservers()

        binding.cardViewNextQuiz.setOnClickListener { handleNextQuiz(NEXT_QUESTION) }
        binding.cardViewHome.setOnClickListener { showExitDialog() }
        binding.cardViewSkip.setOnClickListener { handleNextQuiz(SKIP_QUESTION) }

        binding.fullTime.text = "${TIME_OUT_SKIP_QUESTION/1000} s"
        countDown = object : CountDownTimer(TIME_OUT_SKIP_QUESTION, 1000) {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onTick(millisUntilFinished: Long) {
                binding.remaining.text = "${(millisUntilFinished / 1000)}s"
            }

            override fun onFinish() {
                handleNextQuiz(TIME_OUT_QUESTION)
            }
        }.start()
    }

    private fun startCountDownAnimation() {
        val progressAnimator = ObjectAnimator.ofInt(binding.countDownProgress, "progress", 100, 0)
        progressAnimator.duration = TIME_OUT_SKIP_QUESTION
        progressAnimator.interpolator = LinearInterpolator()
        progressAnimator.start()
    }

    override fun onStop() {
        super.onStop()
        countDown.cancel()
    }

    private fun setUpObservers() {
        viewModel.run {
            getQuestionsLiveData().observe(viewLifecycleOwner) { questions ->
                questionList = questions
                showQuestion(questions[0])
            }
            getMessageLiveData().observe(viewLifecycleOwner) { message ->
                if (message.isNotEmpty()) {
                    showMessage(message)
                }
            }
        }
    }

    private fun handleNextQuiz(action: String) {
        when(action) {
            NEXT_QUESTION -> {
                if (!isResponseSelected) {
                    showMessage("You must select an answer")
                } else {
                    goToNextQuestion()
                }
            }
            SKIP_QUESTION -> {
                answers[questionList[actualQuestion-1]] = null
                goToNextQuestion()
            }
            TIME_OUT_QUESTION -> {
                if (!isResponseSelected) {
                    answers[questionList[actualQuestion-1]] = null
                }
                goToNextQuestion()
            }
        }
    }

    private fun goToNextQuestion() {
        if (actualQuestion >= questionList.size) {
            countDown.cancel()
            viewModel.submitAnswers(answers)
            val direction = QuizFragmentDirections.actionNavigationQuizToNavigationResult()
            findNavController().navigate(direction)
        } else {
            showQuestion(questionList[actualQuestion])
        }
    }

    private fun showExitDialog() {
        val dialog = AlertDialog.Builder(requireContext()).create()
        dialog.setMessage(getString(R.string.exit_warning))
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.stay)) { _, _ ->
            dialog.dismiss()
        }
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.leave)) { _, _ ->
            dialog.dismiss()
            findNavController().popBackStack()
        }
        dialog.show()
    }

    private fun showQuestion(question: Question) {
        countDown.cancel()
        countDown.start()
        startCountDownAnimation()
        responseAdapter = ResponseAdapter(question.response) { selectedResponse ->
            updateResponseSelection(question, selectedResponse.id)
            answers[question] = selectedResponse
            isResponseSelected = true
        }
        binding.lblQuestion.text = question.question
        binding.recyclerViewResponses.adapter = responseAdapter
        actualQuestion++
        isResponseSelected = false
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateResponseSelection(question: Question, selectedResponseId: Long) {
        question.response.forEach { r ->
            r.isSelected = r.id == selectedResponseId
        }
        responseAdapter.notifyDataSetChanged()
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    companion object {
        const val NEXT_QUESTION = "NEXT_QUESTION"
        const val SKIP_QUESTION = "SKIP_QUESTION"
        const val TIME_OUT_QUESTION = "TIME_OUT_QUESTION"
        const val TIME_OUT_SKIP_QUESTION = 60000L
    }

}
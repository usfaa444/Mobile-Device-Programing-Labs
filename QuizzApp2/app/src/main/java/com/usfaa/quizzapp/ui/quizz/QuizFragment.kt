package com.usfaa.quizzapp.ui.quizz

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
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
    private var answers = mutableMapOf<Question, Response>()
    private var actualQuestion = 0
    private var isResponseSelected = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*val q1 = QuestionData(1, "Q1")
        val r11 = Response(11, "R1", 1)
        val r12 = Response(12, "R2", 1)
        val r13 = Response(13, "R3", 1)
        val q2 = QuestionData(2, "Q2")
        val r21 = Response(21, "R1", 2)
        val r22 = Response(22, "R2", 2)
        val r23 = Response(23, "R3", 2)*/

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            showExitDialog()
        }

        viewModel.getQuestions()
        actualQuestion = 0

        binding.recyclerViewResponses.setHasFixedSize(true)
        binding.recyclerViewResponses.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        setUpObservers()

        binding.cardViewNextQuiz.setOnClickListener { handleNextQuiz() }
        binding.cardViewHome.setOnClickListener { showExitDialog() }
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

    private fun handleNextQuiz() {
        if (isResponseSelected) {
            if (actualQuestion >= questionList.size) {
                viewModel.submitAnswers(answers)
                val direction = QuizFragmentDirections.actionNavigationQuizToNavigationResult()
                findNavController().navigate(direction)
            } else {
                showQuestion(questionList[actualQuestion])
            }
        } else {
            showMessage("You must select an answer")
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

}
package com.usfaa.quizzapp.ui.analyse

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.usfaa.quizzapp.R
import com.usfaa.quizzapp.data.adapters.ResultAnalysisAdapter
import com.usfaa.quizzapp.data.models.Result
import com.usfaa.quizzapp.data.models.ResultAnalysisData
import com.usfaa.quizzapp.databinding.FragmentAnalysisBinding
import com.usfaa.quizzapp.ui.quizz.QuizViewModel

class AnalysisFragment : Fragment() {

    private val viewModel: QuizViewModel by activityViewModels()
    private lateinit var binding: FragmentAnalysisBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAnalysisBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewResultAnalysis.setHasFixedSize(true)
        binding.recyclerViewResultAnalysis.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        viewModel.getResultAnalysisData().observe(viewLifecycleOwner) { resultsAnalysisData ->
            formatData(resultsAnalysisData)
        }
    }

    private fun formatData(resultAnalysisData: ResultAnalysisData) {
        val results = mutableListOf<Result>()
        var i = 0
        resultAnalysisData.questions.forEach { question ->
            results.add(Result(question, resultAnalysisData.correctAnswers[i], resultAnalysisData.submittedAnswers[i]))
            i++
        }
        showResultAnalysis(results)
    }

    private fun showResultAnalysis(result: List<Result>) {
        val resultAdapter = ResultAnalysisAdapter(result)
        binding.recyclerViewResultAnalysis.adapter = resultAdapter
    }
}
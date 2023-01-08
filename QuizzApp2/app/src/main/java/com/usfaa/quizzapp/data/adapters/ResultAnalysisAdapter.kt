package com.usfaa.quizzapp.data.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.usfaa.quizzapp.R

class ResultAnalysisAdapter(private val result: List<com.usfaa.quizzapp.data.models.Result>):
    RecyclerView.Adapter<ResultAnalysisAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_analysis, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.lblQuestion.text = "Question ${position + 1}: " + result[position].question.question
        holder.lblCorrectAnswer.text = "Correct Ans: " + result[position].correctAnswer.response
        holder.lblYourAnswer.text = "Your Ans: " + result[position].yourAnswer.response
        if (result[position].correctAnswer.id == result[position].yourAnswer.id) {
            holder.lblYourAnswer.setTextColor(holder.itemView.context.resources.getColor(R.color.green))
        } else {
            holder.lblYourAnswer.setTextColor(holder.itemView.context.resources.getColor(R.color.red))
        }
    }

    override fun getItemCount() = result.size

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val lblQuestion: TextView = itemView.findViewById(R.id.lblQuestion)
        val lblCorrectAnswer: TextView = itemView.findViewById(R.id.lblCorrectAnswer)
        val lblYourAnswer: TextView = itemView.findViewById(R.id.lblYourAnswer)
    }
}
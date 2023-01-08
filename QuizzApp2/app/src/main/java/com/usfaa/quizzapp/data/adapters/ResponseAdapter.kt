package com.usfaa.quizzapp.data.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.usfaa.quizzapp.R
import com.usfaa.quizzapp.data.models.Response

class ResponseAdapter(private val responses: List<Response>, private val callback: (Response) -> Unit)
    : RecyclerView.Adapter<ResponseAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_response, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.radioButton.isChecked = responses[position].isSelected
        holder.radioButton.text = responses[position].response
        holder.radioButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                callback(responses[position])
            }
        }
    }

    override fun getItemCount() = responses.size

    class Holder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val radioButton: RadioButton = itemView.findViewById(R.id.cbResponse)
    }
}
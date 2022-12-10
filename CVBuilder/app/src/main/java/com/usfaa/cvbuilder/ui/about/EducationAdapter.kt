package com.usfaa.cvbuilder.ui.about

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.usfaa.cvbuilder.R
import com.usfaa.cvbuilder.data.models.Education

class EducationAdapter (private val educations: List<Education>,
                        private val callback: (Education, String) -> Unit)
    : RecyclerView.Adapter<EducationAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_education, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.lblInstitute.text = educations[position].institute
        holder.lblDegree.text = educations[position].degree
        holder.lblEdit.setOnClickListener {
            callback(educations[position], ACTION_EDIT)
        }
        holder.lblDelete.setOnClickListener {
            callback(educations[position], ACTION_DELETE)
        }
    }

    override fun getItemCount() = educations.size

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val lblEdit = itemView.findViewById<TextView>(R.id.lblEditEducation)
        val lblDelete = itemView.findViewById<TextView>(R.id.lblDeleteEducation)
        val imgInstitute = itemView.findViewById<ImageView>(R.id.imageView)
        val lblInstitute = itemView.findViewById<TextView>(R.id.lblInstitute)
        val lblDegree = itemView.findViewById<TextView>(R.id.lblDegree)

    }

    companion object {
        const val ACTION_EDIT = "ACTION_EDIT"
        const val ACTION_DELETE = "ACTION_DELETE"
    }
}
package com.usfaa.cvbuilder.ui.work

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.usfaa.cvbuilder.R
import com.usfaa.cvbuilder.data.models.Work

class WorkAdapter(private val works: List<Work>,
                  private val type: String,
                  private val callback: (Work, String, String) -> Unit)
    : RecyclerView.Adapter<WorkAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_work, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val work = works[position]
        holder.lblCompany.text = work.company
        holder.lblTitle.text = work.title
        holder.lblSkills.text= "Skills: ${work.skills}"
        if (type == WorkFragment.TYPE_WORK) {
            holder.lblFromToDate.visibility = View.VISIBLE
            holder.lblLocation.visibility = View.VISIBLE
            holder.image.setImageResource(R.drawable.company)
            holder.lblFromToDate.text = "${work.dateFrom} - ${work.dateTo}"
            holder.lblLocation.text = work.location
        } else {
            holder.lblFromToDate.visibility = View.GONE
            holder.lblLocation.visibility = View.GONE
            holder.image.setImageResource(R.drawable.project)
        }
        holder.lblEdit.setOnClickListener {
            callback(work, ACTION_EDIT, type)
        }
        holder.lblDelete.setOnClickListener {
            callback(work, ACTION_DELETE, type)
        }
    }

    override fun getItemCount() = works.size

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val lblCompany = itemView.findViewById<TextView>(R.id.lblCompany)
        val lblTitle = itemView.findViewById<TextView>(R.id.lblTitle)
        val lblFromToDate = itemView.findViewById<TextView>(R.id.lblFromToDate)
        val lblLocation = itemView.findViewById<TextView>(R.id.lblLocation)
        val lblSkills = itemView.findViewById<TextView>(R.id.lblSkills)
        val lblDelete = itemView.findViewById<TextView>(R.id.lblDeleteWork)
        val lblEdit = itemView.findViewById<TextView>(R.id.lblEditWork)
        val image = itemView.findViewById<ImageView>(R.id.imageView)
    }

    companion object {
        const val ACTION_EDIT = "ACTION_EDIT"
        const val ACTION_DELETE = "ACTION_DELETE"
    }
}
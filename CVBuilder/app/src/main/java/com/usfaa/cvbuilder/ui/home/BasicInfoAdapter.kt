package com.usfaa.cvbuilder.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.usfaa.cvbuilder.R
import com.usfaa.cvbuilder.data.models.BasicInfo

class BasicInfoAdapter (private val basicInfo: List<BasicInfo>,
                        private val callBackEdit: (BasicInfo, String) -> Unit)
    : RecyclerView.Adapter<BasicInfoAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_basic_info, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.lblTitle.text = basicInfo[position].title
        holder.lblInfo.text = basicInfo[position].info
        holder.lblEdit.setOnClickListener {
            callBackEdit(basicInfo[position], ACTION_EDIT)
        }
        holder.imgDelete.setOnClickListener {
            callBackEdit(basicInfo[position], ACTION_DELETE)
        }
    }

    override fun getItemCount() = basicInfo.size

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val lblTitle = itemView.findViewById<TextView>(R.id.lblTitle)
        val lblInfo = itemView.findViewById<TextView>(R.id.lblInfo)
        val lblEdit = itemView.findViewById<TextView>(R.id.lblEditBasicInfo)
        val imgDelete = itemView.findViewById<ImageView>(R.id.imgDelete)
    }

    companion object {
        const val ACTION_DELETE = "ACTION_DELETE"
        const val ACTION_EDIT = "ACTION_EDIT"
    }
}
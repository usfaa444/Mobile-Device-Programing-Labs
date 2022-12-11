package com.usfaa.cvbuilder.ui.work

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.usfaa.cvbuilder.R
import com.usfaa.cvbuilder.data.models.Work
import com.usfaa.cvbuilder.databinding.FragmentWorkBinding
import com.usfaa.cvbuilder.ui.base.BaseFragment

class WorkFragment : BaseFragment() {

    private lateinit var binding: FragmentWorkBinding
    private val viewModel: WorkViewModel by viewModels()
    private lateinit var progressDialog : ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWorkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
        binding.recyclerViewWork.setHasFixedSize(true)
        binding.recyclerViewWork.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.recyclerViewProject.setHasFixedSize(true)
        binding.recyclerViewProject.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Please wait...")
        binding.addWork.setOnClickListener {
            showAddOrEditWork(null, TYPE_WORK)
        }
        binding.addProject.setOnClickListener {
            showAddOrEditWork(null, TYPE_PROJECT)
        }
        viewModel.getAllWorks()
        viewModel.getAllProjects()
    }

    private fun showAddOrEditWork(workToEdit: Work?, type: String) {
        val layoutView = LayoutInflater.from(context).inflate(R.layout.layout_add_work, null)
        val dialog = AlertDialog.Builder(requireContext()).create()
        dialog.setView(layoutView)
        val txtTitle = layoutView.findViewById<EditText>(R.id.txtTitle)
        val lblWork = layoutView.findViewById<TextView>(R.id.lblWork)
        val txtCompany = layoutView.findViewById<EditText>(R.id.txtCompany)
        val txtLocation = layoutView.findViewById<EditText>(R.id.txtLocation)
        val txtStartDate = layoutView.findViewById<EditText>(R.id.txtStartDate)
        val txtEndDate = layoutView.findViewById<EditText>(R.id.txtEndDate)
        val txtSkills = layoutView.findViewById<EditText>(R.id.txtSkills)
        val cbStillWork = layoutView.findViewById<CheckBox>(R.id.cbStillWork)
        val infoWork = layoutView.findViewById<ConstraintLayout>(R.id.infoWork)
        val btnSave = layoutView.findViewById<Button>(R.id.btnSave)
        if (type == TYPE_WORK) {
            infoWork.visibility = View.VISIBLE
            lblWork.setText("Add Work")
            cbStillWork.setOnCheckedChangeListener { compoundButton, isChecked ->
                txtEndDate.isEnabled = !isChecked
                if (isChecked) {
                    txtEndDate.setText("Present")
                } else {
                    txtEndDate.setText("")
                }
            }
        } else {
            lblWork.setText("Add Project")
            infoWork.visibility = View.GONE
        }
        workToEdit?.let {
            txtTitle.setText(it.title)
            txtCompany.setText(it.company)
            txtSkills.setText(it.skills)
            if (type == TYPE_WORK) {
                txtLocation.setText(it.location)
                txtStartDate.setText(it.dateFrom)
                txtEndDate.setText(it.dateTo)
                if (it.dateTo ==  "Present") {
                    cbStillWork.isChecked = true
                    txtEndDate.isEnabled = false
                }
            }
        }
        btnSave.setOnClickListener {
            if (txtCompany.text.toString().isEmpty()) {
                showToastMessage("Please fill the company location!")
                return@setOnClickListener
            }
            if (txtTitle.text.toString().isEmpty()) {
                showToastMessage("Please fill the title you occupied!")
                return@setOnClickListener
            }
            if (txtSkills.text.toString().isEmpty()) {
                showToastMessage("Please enter the skills you used at this position!")
                return@setOnClickListener
            }
            if (type == TYPE_WORK) {
                if (txtLocation.text.toString().isEmpty()) {
                    showToastMessage("Please fill the start date!")
                    return@setOnClickListener
                }
                if (txtStartDate.text.toString().isEmpty()) {
                    showToastMessage("Please fill the start date!")
                    return@setOnClickListener
                }
                if (txtEndDate.text.toString().isEmpty() && !cbStillWork.isChecked) {
                    showToastMessage("Please fill the end date!")
                    return@setOnClickListener
                }
            }
            showOrHideProgress(true)
            val endDate = if (cbStillWork.isChecked) "Present" else txtEndDate.text.toString()
            val work = if (type == TYPE_WORK) Work(workToEdit?.id ?: System.currentTimeMillis(), "image",
            txtStartDate.text.toString(), endDate, txtLocation.text.toString(), txtTitle.text.toString(),
                txtCompany.text.toString(), txtSkills.text.toString()) else
                Work(workToEdit?.id ?: System.currentTimeMillis(), "image",
                    "", "", "", txtTitle.text.toString(),
                    txtCompany.text.toString(), txtSkills.text.toString())
            if (type == TYPE_WORK) {
                viewModel.saveWork(work)
            } else {
                viewModel.saveProject(work)
            }
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun setUpObservers() {
        viewModel.run {
            getWorks().observe(viewLifecycleOwner) { works ->
                showOrHideProgress(false)
                if (works.isNotEmpty()) {
                    binding.recyclerViewWork.visibility = View.VISIBLE
                    binding.lblNoData.visibility = View.GONE
                    showWorksList(works, TYPE_WORK)
                } else {
                    binding.recyclerViewWork.visibility = View.GONE
                    binding.lblNoData.visibility = View.VISIBLE
                }
            }
            getProjects().observe(viewLifecycleOwner) { works ->
                showOrHideProgress(false)
                if (works.isNotEmpty()) {
                    binding.recyclerViewProject.visibility = View.VISIBLE
                    showWorksList(works, TYPE_PROJECT)
                }
            }
            getMessage().observe(viewLifecycleOwner) { message ->
                showOrHideProgress(false)
                if (message.isNotEmpty()) {
                    showToastMessage(message)
                }
            }
        }
    }

    private fun showWorksList(works: List<Work>, t: String) {
        val adapter = WorkAdapter(works, t) {workToEdit, action, type ->
            if (action == WorkAdapter.ACTION_EDIT) {
                showAddOrEditWork(workToEdit, type)
            } else if (action == WorkAdapter.ACTION_DELETE) {
                showOrHideProgress(true)
                if (type == TYPE_WORK) {
                    viewModel.deleteWork(workToEdit)
                } else {
                    viewModel.deleteProject(workToEdit)
                }
            }
        }
        if (t == TYPE_WORK) {
            binding.recyclerViewWork.adapter = adapter
        } else {
            binding.recyclerViewProject.adapter = adapter
        }
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun showOrHideProgress(show: Boolean) {
        if (show) {
            progressDialog.show()
        } else {
            if (progressDialog.isShowing)
                progressDialog.dismiss()
        }
    }

    override fun shouldShowBottomNavigation() = true

    companion object {
        const val TYPE_WORK = "TYPE_WORK"
        const val TYPE_PROJECT = "TYPE_PROJECT"
    }
}
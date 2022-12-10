package com.usfaa.cvbuilder.ui.about

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.usfaa.cvbuilder.R
import com.usfaa.cvbuilder.data.models.Education
import com.usfaa.cvbuilder.databinding.FragmentAboutmeBinding
import com.usfaa.cvbuilder.ui.base.BaseFragment
import org.w3c.dom.Text

class AboutFragment : BaseFragment() {

    private lateinit var binding: FragmentAboutmeBinding
    private val viewModel: AboutViewModel by viewModels()
    private lateinit var progressDialog : ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutmeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Please wait...")
        binding.recyclerViewEducation.setHasFixedSize(true)
        binding.recyclerViewEducation.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.recyclerViewCertification.setHasFixedSize(true)
        binding.recyclerViewCertification.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        setUpObservers()
        viewModel.getAboutMeData()
        binding.lblEditAboutMe.setOnClickListener {
            showUpdateAboutMeDialog()
        }
        binding.addCertification.setOnClickListener {
            showAddOrEditEducation(null, TYPE_CERTIFICATION)
        }
        binding.addEducation.setOnClickListener {
            showAddOrEditEducation(null, TYPE_EDUCATION)
        }
    }

    private fun setUpObservers() {
        viewModel.run {
            getAboutMe().observe(viewLifecycleOwner) { aboutMe ->
                showOrHideProgress(false)
                binding.lblAboutMeText.text = aboutMe
            }
            getMessage().observe(viewLifecycleOwner) { message ->
                showOrHideProgress(false)
                if (message.isNotEmpty()) {
                    showToastMessage(message)
                }
            }
            getEducations().observe(viewLifecycleOwner) { educations ->
                showOrHideProgress(false)
                if (educations.isNotEmpty()) {
                    binding.recyclerViewEducation.visibility = View.VISIBLE
                    binding.lblNoDataEducation.visibility = View.GONE
                    val adapter = EducationAdapter(educations) { educationToEdit, action ->
                        if (action == EducationAdapter.ACTION_DELETE) {
                            showOrHideProgress(true)
                            viewModel.deleteEducation(educationToEdit)
                        } else if (action == EducationAdapter.ACTION_EDIT) {
                            showAddOrEditEducation(educationToEdit, TYPE_EDUCATION)
                        }
                    }
                    binding.recyclerViewEducation.adapter = adapter
                } else {
                    binding.recyclerViewEducation.visibility = View.GONE
                    binding.lblNoDataEducation.visibility = View.VISIBLE
                }
            }
            getCertifications().observe(viewLifecycleOwner) { certifications ->
                showOrHideProgress(false)
                if (certifications.isNotEmpty()) {
                    binding.recyclerViewCertification.visibility = View.VISIBLE
                    binding.lblNoDataCertification.visibility = View.GONE
                    val adapter = EducationAdapter(certifications) { educationToEdit, action ->
                        if (action == EducationAdapter.ACTION_DELETE) {
                            showOrHideProgress(true)
                            viewModel.deleteCertification(educationToEdit)
                        } else if (action == EducationAdapter.ACTION_EDIT) {
                            showAddOrEditEducation(educationToEdit, TYPE_CERTIFICATION)
                        }
                    }
                    binding.recyclerViewCertification.adapter = adapter
                } else {
                    binding.recyclerViewCertification.visibility = View.GONE
                    binding.lblNoDataCertification.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun showAddOrEditEducation(education: Education?, type: String) {
        val layoutView = LayoutInflater.from(context).inflate(R.layout.layout_add_education, null)
        val dialog = AlertDialog.Builder(requireContext()).create()
        dialog.setView(layoutView)
        val lblEducationType = layoutView.findViewById<TextView>(R.id.lblEducationType)
        val txtEducationInstitute = layoutView.findViewById<EditText>(R.id.txtEducationInstitute)
        val txtEducationDegree = layoutView.findViewById<EditText>(R.id.txtEducationDegree)
        val btnSave = layoutView.findViewById<Button>(R.id.btnSave)
        education?.let {
            txtEducationInstitute.setText(it.institute)
            txtEducationDegree.setText(it.degree)
        }
        lblEducationType.text = if (education != null) "Edit " else "Add " +
                if (type == TYPE_EDUCATION) "Education" else "Certification"
        btnSave.setOnClickListener {
            showOrHideProgress(true)
            if (type == TYPE_EDUCATION) {
                viewModel.saveEducation(education?.id ?: System.currentTimeMillis(), txtEducationInstitute.text.toString(), txtEducationDegree.text.toString())
            } else if (type == TYPE_CERTIFICATION) {
                viewModel.saveCertification(education?.id ?: System.currentTimeMillis(), txtEducationInstitute.text.toString(), txtEducationDegree.text.toString())
            }
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun showUpdateAboutMeDialog() {
        val layoutView = LayoutInflater.from(context).inflate(R.layout.layour_update_about_me, null)
        val dialog = AlertDialog.Builder(requireContext()).create()
        dialog.setView(layoutView)
        val txtAboutMe = layoutView.findViewById<EditText>(R.id.txtAboutMe)
        val btnSave = layoutView.findViewById<Button>(R.id.btnSave)
        btnSave.setOnClickListener {
            showOrHideProgress(true)
            viewModel.saveAboutMe(txtAboutMe.text.toString())
            dialog.dismiss()
        }
        dialog.show()
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
        const val TYPE_EDUCATION = "TYPE_EDUCATION"
        const val TYPE_CERTIFICATION = "TYPE_CERTIFICATION"
    }
}
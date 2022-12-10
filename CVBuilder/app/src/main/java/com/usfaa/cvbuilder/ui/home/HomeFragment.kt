package com.usfaa.cvbuilder.ui.home

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.usfaa.cvbuilder.R
import com.usfaa.cvbuilder.data.models.BasicInfo
import com.usfaa.cvbuilder.databinding.FragmentHomeBinding
import com.usfaa.cvbuilder.ui.base.BaseFragment

class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var progressDialog : ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
        viewModel.getBasicInfo()
        binding.recyclerViewBasicInfo.setHasFixedSize(true)
        binding.recyclerViewBasicInfo.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Please wait...")
        binding.addBasicInfo.setOnClickListener {
            showAddOrEditInfo(null)
        }
    }

    private fun showAddOrEditInfo(info: BasicInfo?) {
        val layoutView = LayoutInflater.from(context).inflate(R.layout.layout_add_basic_info, null)
        val dialog = AlertDialog.Builder(requireContext()).create()
        dialog.setView(layoutView)
        val txtTitle = layoutView.findViewById<EditText>(R.id.txtBasicInfoTitle)
        val txtData = layoutView.findViewById<EditText>(R.id.txtBasicInfoData)
        val btnAdd = layoutView.findViewById<Button>(R.id.btnAdd)
        info?.let {
            txtTitle.setText(it.title)
            txtData.setText(it.info)
        }
        btnAdd.setOnClickListener {
            showOrHideProgress(true)
            viewModel.saveBasicInfo(txtTitle.text.toString(), txtData.text.toString())
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun setUpObservers() {
        viewModel.run {
            getHomeBasicInfo().observe(viewLifecycleOwner) { basicInfo ->
                showBasicInfo(basicInfo)
            }
            getMessage().observe(viewLifecycleOwner) { message ->
                showOrHideProgress(false)
                if (message.isNotEmpty()) {
                    showToastMessage(message)
                }
            }
            getInfoSaved().observe(viewLifecycleOwner) { basicInfo ->
                showBasicInfo(basicInfo)
            }
        }
    }

    private fun showBasicInfo(data: List<BasicInfo>) {
        showOrHideProgress(false)
        if (data.isNotEmpty()) {
            binding.recyclerViewBasicInfo.visibility = View.VISIBLE
            binding.lblNoData.visibility = View.GONE
            val adapter = BasicInfoAdapter(data) { basicInfoToEdit, action ->
                if (action == BasicInfoAdapter.ACTION_EDIT) {
                    showAddOrEditInfo(basicInfoToEdit)
                } else if (action == BasicInfoAdapter.ACTION_DELETE) {
                    showOrHideProgress(true)
                    viewModel.deleteBasicInfo(basicInfoToEdit)
                }
            }
            binding.recyclerViewBasicInfo.adapter = adapter
        } else {
            binding.recyclerViewBasicInfo.visibility = View.GONE
            binding.lblNoData.visibility = View.VISIBLE
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
}
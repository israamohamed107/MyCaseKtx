package com.example.mycasektx.ui.requests

import android.app.Activity
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.mycasektx.R
import com.example.mycasektx.data.UserPreferencesData
import com.example.mycasektx.data.responses.PdfFile
import com.example.mycasektx.data.responses.RequestResponse
import com.example.mycasektx.databinding.FragmentNewRequestBinding
import com.example.mycasektx.ui.*
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewRequestFragment : Fragment(), FilesAdapter.OnItemClickListener {
    private val requestViewModel: RequestViewModel by viewModels()

    private lateinit var selectedFiles: ArrayList<PdfFile?>
    private lateinit var selectedFilesUri: ArrayList<String?>
    private lateinit var binding: FragmentNewRequestBinding
    private lateinit var filesAdapter: FilesAdapter
    private lateinit var court: String
    private lateinit var caseType: String
    private val selectPdfActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                //If multiple pdf selected
                if (data?.clipData != null) {
                    val count = data.clipData?.itemCount ?: 0
                    for (i in 0 until count) {
                        val pdfUri: Uri? = data.clipData?.getItemAt(i)?.uri
                        val pdfName = pdfUri?.getName(requireContext())
                        selectedFiles.add(PdfFile(pdfUri, pdfName))
                        selectedFilesUri.add(pdfUri.toString())
                        binding.recyclerFiles.visible(true)

                    }

                }
                //If single pdf selected
                else if (data?.data != null) {
                    val pdfUri: Uri? = data.data
                    val pdfName = pdfUri?.getName(requireContext())
                    selectedFiles.add(PdfFile(pdfUri, pdfName))
                    selectedFilesUri.add(pdfUri.toString())
                    binding.recyclerFiles.visible(true)

                }
                filesAdapter.submitList(selectedFiles)

            }
        }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewRequestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectedFiles = ArrayList()
        selectedFilesUri = ArrayList()

        filesAdapter = FilesAdapter(this)


        initSpinner()
        onClick()

    }

    private fun onClick() {

        binding.apply {
            btnSubmit.setOnClickListener {
                if (txtCaseTitle.text.isEmpty()) {
                    txtCaseTitle.error = getString(R.string.required)
                } else if (spinnerCourt.selectedItemPosition == 0) {
                    val text = spinnerCourt.selectedView as TextView
                    text.error = getString(R.string.required)
                } else if (spinnerCaseType.selectedItemPosition == 0) {
                    val text = spinnerCourt.selectedView as TextView
                    text.error = getString(R.string.required)
                } else if (txtClientName.text.isEmpty()) {
                    txtClientName.error = getString(R.string.required)
                } else if (txtClientAddress.text.isEmpty()) {
                    txtClientAddress.error = getString(R.string.required)
                } else if (txtClientDescription.text.isEmpty()) {
                    txtClientDescription.error = getString(R.string.required)
                } else if (txtOpposingName.text.isEmpty()) {
                    txtOpposingName.error = getString(R.string.required)
                } else if (txtOpposingAddress.text.isEmpty()) {
                    txtOpposingAddress.error = getString(R.string.required)
                } else if (txtOpposingDescription.text.isEmpty()) {
                    txtOpposingDescription.error = getString(R.string.required)
                } else {

                    val requestResponse = RequestResponse(
                        System.currentTimeMillis(),
                        binding.txtCaseTitle.text.toString(),
                        binding.spinnerCourt.selectedItem.toString(),
                        binding.spinnerCaseType.selectedItem.toString(),
                        binding.txtClientName.text.toString(),
                        binding.txtClientAddress.text.toString(),
                        binding.txtClientDescription.text.toString(),
                        binding.txtOpposingName.text.toString(),
                        binding.txtOpposingAddress.text.toString(),
                        binding.txtOpposingDescription.text.toString(),
                        false,
                        false,
                        selectedFilesUri,
                        USER_NAME,
                        USER_EMAIL
                    )
                    submitRequest(requestResponse , USER_PHONE)

                }
            }


        }
        binding.txtFiles.setOnClickListener {
            val intent = Intent()
            intent.action = ACTION_GET_CONTENT
            intent.type = "application/pdf"
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            selectPdfActivityResult.launch(intent)

        }
        binding.recyclerFiles.adapter = filesAdapter

    }

    private fun submitRequest(requestResponse: RequestResponse , userId:String) {
        requestViewModel.submitResponse(requestResponse , userId)
        requestViewModel.requestStatus.observe(viewLifecycleOwner){
            when(it){
                 true -> {
                    Snackbar.make(requireView(), "تم تقديم طلبك بنجاح", Snackbar.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
                false ->{
                    Snackbar.make(requireView(), "حاول مجددا", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initSpinner() {

        val courtAdapter = ArrayAdapter(requireContext(), R.layout.list_item, COURT_ARRAY)
        binding.spinnerCourt.adapter = courtAdapter

        binding.spinnerCourt.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                val value = parent!!.getItemAtPosition(position).toString()
                if (value == COURT_ARRAY[0]) {
                    (view as TextView).setTextColor(Color.GRAY)
                    court = ""
                } else {
                    (view as TextView).setTextColor(Color.BLACK)
                    court = COURT_ARRAY[position]
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }

        val casetypeAdapter = ArrayAdapter(requireContext(), R.layout.list_item, CASE_TYPES_ARRAY)
        binding.spinnerCaseType.adapter = casetypeAdapter

        binding.spinnerCaseType.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    val value = parent!!.getItemAtPosition(position).toString()
                    if (value == CASE_TYPES_ARRAY[0]) {
                        (view as TextView).setTextColor(Color.GRAY)
                    } else {
                        (view as TextView).setTextColor(Color.BLACK)
                        caseType = CASE_TYPES_ARRAY[position]

                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

            }

    }

    override fun onFileClicked(file: PdfFile) {
        Toast.makeText(requireContext(), "${file.name}", Toast.LENGTH_SHORT).show()
    }


}
package com.example.mycasektx.ui.session

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mycasektx.R
import com.example.mycasektx.data.responses.RequestResponse
import com.example.mycasektx.databinding.FragmentSessionDateBinding
import com.example.mycasektx.databinding.FragmentSessionFeesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SessionFeesFragment : Fragment() {

    private val sessionViewModel : SessionsViewModel by viewModels()
    private lateinit var binding: FragmentSessionFeesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSessionFeesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val request = sessionViewModel.requestItem

        setSessionInfo(request!!)

    }

    private fun setSessionInfo(request: RequestResponse) {

        binding.apply {
            txtRequestId.text = request.id.toString()
            txtCaseTitle.text = request.caseTitle
            txtCourt.text = request.court
        }
    }


}
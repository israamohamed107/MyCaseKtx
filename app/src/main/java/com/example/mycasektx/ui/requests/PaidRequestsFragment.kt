package com.example.mycasektx.ui.requests

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mycasektx.R
import com.example.mycasektx.data.responses.RequestResponse
import com.example.mycasektx.databinding.FragmentPaidRequestsBinding
import com.example.mycasektx.databinding.FragmentReviewedRequestsBinding
import com.example.mycasektx.ui.USER_PHONE
import com.example.mycasektx.ui.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaidRequestsFragment : Fragment() , RequestAdapter.OnItemClickListener {


    private lateinit var binding: FragmentPaidRequestsBinding
    private val requestViewModel :RequestViewModel by viewModels()
    private lateinit var requestAdapter: RequestAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaidRequestsBinding.inflate(inflater , container ,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestAdapter = RequestAdapter(this)
        binding.recyclerRequests.adapter = requestAdapter

        binding.progressBar.visible(true)
        getPaidRequests(USER_PHONE)
    }

    private fun getPaidRequests(userPhone: String) {
        requestViewModel.getPaidRequests(userPhone)

        requestViewModel.paidRequests.observe(viewLifecycleOwner){
            requestAdapter.submitList(it)
            binding.progressBar.visible(false)
        }

    }

    override fun onFileClicked(requestResponse: RequestResponse) {
        findNavController().navigate(AllRequestsFragmentDirections.actionAllRequestsFragmentToRequestDetailsFragment(requestResponse))
    }

}
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
import com.example.mycasektx.databinding.FragmentRequestBinding
import com.example.mycasektx.ui.USER_PHONE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RequestFragment : Fragment() ,RequestAdapter.OnItemClickListener{

    private lateinit var binding :FragmentRequestBinding
    private val requestViewModel: RequestViewModel by viewModels()
    private lateinit var requestAdapter: RequestAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRequestBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestAdapter = RequestAdapter(this)
        binding.recyclerRequests.adapter = requestAdapter
        getAllRequests(USER_PHONE)

    }

    private fun getAllRequests(userPhone:String){

        requestViewModel.getAllRequests(userPhone)

        requestViewModel.requests.observe(viewLifecycleOwner){
            requestAdapter.submitList(it)

        }
    }

    override fun onFileClicked(requestResponse: RequestResponse) {
        findNavController().navigate(AllRequestsFragmentDirections.actionAllRequestsFragmentToRequestDetailsFragment(requestResponse))
    }


}
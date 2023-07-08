package com.example.mycasektx.ui.requests

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mycasektx.R
import com.example.mycasektx.databinding.FragmentRequestDetailsBinding
import com.example.mycasektx.ui.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RequestDetailsFragment : Fragment() {


    private lateinit var binding:FragmentRequestDetailsBinding
    private val requestViewModel :RequestViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRequestDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setData()
        onClick()


    }
    private fun setData(){
        val request = requestViewModel.requestItem
        binding.apply {
            txtRequest.setText(" طلب رقم ${request?.id.toString()} ")
            txtRequestId.setText(request?.clientName)
            txtCaseTitle.setText(request?.caseTitle)
            txtCaseType.setText(request?.caseType)
            txtCourt.setText(request?.court)
            txtClientName.setText(request?.clientName)
            txtClientAddress.setText(request?.caseTitle)
            txtClientDescription.setText(request?.clientDescription)
            txtRequestOpposingName.setText(request?.opposingPartyName)
            txtOpposingAddress.setText(request?.opposingPartyAddress)
            txtOpposingDescription.setText(request?.opposingPartyDescription)

            btnMove.visible(request!!.isReviewed || request!!.isReviewed)

            if (request?.isPaid == false && request?.isReviewed == true) {
                btnMove.text = "مقدار الرسوم"
            }
                 else if(request?.isPaid == true && request?.isReviewed == true){
                btnMove.text = "موعد الجلسة"

            }

        }
    }

    private fun onClick(){
        val request = requestViewModel.requestItem
        binding.apply {
            btnMove.setOnClickListener{
                if(btnMove.text == "مقدار الرسوم" ){
                    findNavController().navigate(RequestDetailsFragmentDirections.actionRequestDetailsFragmentToSessionFeesFragment(request!!))
                }
                else if(btnMove.text == "موعد الجلسة"){
                    findNavController().navigate(RequestDetailsFragmentDirections.actionRequestDetailsFragmentToSessionDateFragment(request!!))
                }
            }
        }
    }


}
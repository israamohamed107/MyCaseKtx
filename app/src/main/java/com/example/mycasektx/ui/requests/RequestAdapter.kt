package com.example.mycasektx.ui.requests

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mycasektx.data.responses.RequestResponse
import com.example.mycasektx.databinding.RequestItemBinding

class RequestAdapter (private val onItemClickListener: OnItemClickListener) :
    ListAdapter<RequestResponse, RequestAdapter.RequestHolder>(TaskDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestHolder {
        val binding = RequestItemBinding.inflate(LayoutInflater.from(parent.context) ,parent,false)
        return RequestHolder(binding)
    }

    override fun onBindViewHolder(holder: RequestHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class RequestHolder (private val binding: RequestItemBinding) : RecyclerView.ViewHolder(binding.root){

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val request = getItem(position)
                        onItemClickListener.onFileClicked(request)
                    }
                }
            }
        }

            fun bind(requestResponse: RequestResponse){
                binding.apply {
                    txtRequestId.text = " طلب رقم ${requestResponse.id.toString()} "
                    txtClientName.text =requestResponse.clientName

                }
            }

        }

        class TaskDiffCallback : DiffUtil.ItemCallback<RequestResponse>(){
            override fun areItemsTheSame(
                oldItem: RequestResponse,
                newItem: RequestResponse
            ): Boolean =  oldItem.id == newItem.id


            override fun areContentsTheSame(
                oldItem: RequestResponse,
                newItem: RequestResponse
            ): Boolean = oldItem == newItem
        }

        interface OnItemClickListener {
            fun onFileClicked(requestResponse: RequestResponse)
        }



}
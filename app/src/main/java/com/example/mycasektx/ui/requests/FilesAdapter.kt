package com.example.mycasektx.ui.requests

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mycasektx.data.responses.PdfFile
import com.example.mycasektx.databinding.FileItemBinding
import com.example.mycasektx.databinding.ListItemBinding

class FilesAdapter (private val onItemClickListener: OnItemClickListener) :
    ListAdapter<PdfFile , FilesAdapter.FileHolder>(TaskDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileHolder {
        val binding = FileItemBinding.inflate(LayoutInflater.from(parent.context) , parent ,false)
        return FileHolder(binding)
    }

    override fun onBindViewHolder(holder: FileHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class FileHolder (val binding: FileItemBinding) : RecyclerView.ViewHolder(binding.root){

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val file = getItem(position)
                        onItemClickListener.onFileClicked(file)
                    }
                }
                }
            }

        fun bind(pdfFile: PdfFile){
            binding.apply {
                txtName.text = pdfFile.name
            }
        }

    }

    class TaskDiffCallback : DiffUtil.ItemCallback<PdfFile>(){
        override fun areItemsTheSame(oldItem: PdfFile, newItem: PdfFile): Boolean =
            oldItem.name == newItem.name


        override fun areContentsTheSame(oldItem: PdfFile, newItem: PdfFile): Boolean = oldItem == newItem

    }

    interface OnItemClickListener {
        fun onFileClicked(file: PdfFile)
    }



}
package com.example.retrofittest.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.retrofittest.R
import com.example.retrofittest.data.models.TaskData
import com.example.retrofittest.databinding.ItemTaskBinding

class TaskAdapter : ListAdapter<TaskData, TaskAdapter.TaskViewHolder>(diffCallBack) {

    inner class TaskViewHolder(private val binding: ItemTaskBinding) : ViewHolder(binding.root) {
        fun bind() {

            val d = getItem(adapterPosition)

            binding.tvTask.text = d.task
            binding.tvDesc.text = d.description

            binding.root.setOnClickListener {
                onClick.invoke(d.id, d.task, d.description)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            ItemTaskBinding.bind(
                LayoutInflater.from(
                    parent.context
                ).inflate(R.layout.item_task, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind()
    }

    private object diffCallBack: DiffUtil.ItemCallback<TaskData>() {
        override fun areItemsTheSame(oldItem: TaskData, newItem: TaskData): Boolean {
           return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TaskData, newItem: TaskData): Boolean {
            return oldItem == newItem
        }

    }

    var onClick:(id: Int, task: String, desc: String) -> Unit = {_, _, _ ->}
    fun setOnItemClickListener(onClick: (id: Int, task: String, desc: String) -> Unit) {
        this.onClick = onClick
    }
}
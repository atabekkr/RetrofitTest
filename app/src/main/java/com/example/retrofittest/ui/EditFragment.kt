package com.example.retrofittest.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.retrofittest.R
import com.example.retrofittest.data.local.LocalStorage
import com.example.retrofittest.databinding.FragmentEditBinding
import com.example.retrofittest.presentation.TasksViewModel
import com.google.android.material.snackbar.Snackbar

class EditFragment : Fragment(R.layout.fragment_edit) {

    private lateinit var binding: FragmentEditBinding
    private  val navArgs: EditFragmentArgs by navArgs()
    private lateinit var viewModel: TasksViewModel

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentEditBinding.bind(view)

        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        )[TasksViewModel::class.java]

        val id = navArgs.id
        val task = navArgs.task
        val desc = navArgs.desc

        binding.apply{
            tvName.setText(task)
            tvDesc.setText(desc)

            btnSave.setOnClickListener {
                val newTask = tvName.text.toString()
                val newDesc = tvDesc.text.toString()

                if (newTask != task || newDesc != desc) {
                    lifecycleScope.launchWhenResumed {
                        viewModel.updateTask(newDesc, newTask, id)
                    }
                    Snackbar.make(btnSave, "Task updated success", Snackbar.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
            }

            btnDelete.setOnClickListener {
                lifecycleScope.launchWhenResumed {
                    viewModel.deleteTask(id)
                }
                findNavController().popBackStack()
            }

            isDone.setOnClickListener {
                if (LocalStorage().isDone) {
                    isDone.text = "Not Done"
                    LocalStorage().isDone = false
                } else {
                    LocalStorage().isDone = true
                    isDone.text = "Done"
                }
                lifecycleScope.launchWhenResumed {
                    Log.d("Warrr", "${LocalStorage().isDone}")
                    viewModel.updateIsDone(LocalStorage().isDone, id)
                }
            }
        }

    }
}
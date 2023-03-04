package com.example.retrofittest.ui.tasks

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.retrofittest.R
import com.example.retrofittest.databinding.FragmentCreateTaskBinding
import com.example.retrofittest.presentation.TasksViewModel

class CreateTaskFragment : Fragment(R.layout.fragment_create_task) {

    private lateinit var binding: FragmentCreateTaskBinding
    private lateinit var viewModel: TasksViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreateTaskBinding.bind(view)

        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        )[TasksViewModel::class.java]

        binding.apply {


            btnCreate.setOnClickListener {
                val task = etTask.text.toString()
                val desc = etDesc.text.toString()
                if (task.isNotEmpty() && desc.isNotEmpty()) {
                    lifecycleScope.launchWhenResumed {

                        viewModel.createTask(desc, task)
                        findNavController().popBackStack()
                    }
                } else {
                    Toast.makeText(requireContext(), "Toltir", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
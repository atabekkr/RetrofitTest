package com.example.retrofittest.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.retrofittest.R
import com.example.retrofittest.databinding.FragmentTasksBinding
import com.example.retrofittest.presentation.TasksViewModel
import com.example.retrofittest.ui.adapters.TaskAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class TasksFragment : Fragment(R.layout.fragment_tasks) {

    private lateinit var binding: FragmentTasksBinding
    private lateinit var viewModel: TasksViewModel
    private val adapter = TaskAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTasksBinding.bind(view)

        binding.recyclerView.adapter = adapter

        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireActivity(),
                DividerItemDecoration.VERTICAL
            )
        )

        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        )[TasksViewModel::class.java]

        lifecycleScope.launchWhenResumed {
            viewModel.getAllTasks()
        }

        initObservers()

        binding.fab.setOnClickListener {
            findNavController().navigate(
                R.id.action_tasksFragment_to_createTaskFragment
            )
        }

        adapter.setOnItemClickListener {
            findNavController().navigate(
                R.id.action_tasksFragment_to_editFragment
            )
        }

    }

    override fun onStop() {
        super.onStop()

        lifecycleScope.launchWhenResumed {
            viewModel.getAllTasks()
        }
    }

    private fun initObservers() {
        viewModel.getAllTasksFlow.onEach {
            adapter.submitList(it)
        }.launchIn(lifecycleScope)
    }
}
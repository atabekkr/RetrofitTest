package com.example.retrofittest.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.retrofittest.R
import com.example.retrofittest.databinding.FragmentRegisterBinding
import com.example.retrofittest.presentation.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import packagename.telegramclone.utils.toast

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var viewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)


        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        )[MainViewModel::class.java]

        initObservers()


        binding.apply {
            btnRegister.setOnClickListener {
                if (etName.text.toString().isNotEmpty() && etPassword.text.toString()
                        .isNotEmpty() && etPhone.text.toString().isNotEmpty()
                ) {
                    lifecycleScope.launchWhenResumed {
                        viewModel.isSuccess(
                            name = etName.text.toString(),
                            password = etPassword.text.toString(),
                            phone = etPhone.text.toString()
                        )
                    }
                    findNavController().navigate(
                        R.id.action_registerFragment_to_tasksFragment
                    )
                } else {
                    Toast.makeText(requireContext(), "Toltirin", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initObservers() {
        viewModel.getSuccessFlow.onEach {
            Log.w("TTTT", it)
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }.launchIn(lifecycleScope)
    }
}
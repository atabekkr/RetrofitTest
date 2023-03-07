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
import com.example.retrofittest.data.local.LocalStorage
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

            btnBackToLogin.setOnClickListener {
                findNavController().popBackStack()
            }

            fab.setOnClickListener {
                if (etUsername.text.toString().isNotEmpty() && etPassword.text.toString()
                        .isNotEmpty() && etPhone.text.toString().isNotEmpty()
                ) {
                    lifecycleScope.launchWhenResumed {
                        viewModel.isSuccess(
                            name = etUsername.text.toString(),
                            password = etPassword.text.toString(),
                            phone = etPhone.text.toString()
                        )
                    }

                } else {
                    Toast.makeText(requireContext(), "Toltirin", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initObservers() {
        viewModel.getSuccessFlow.onEach {
            LocalStorage().token = it
            LocalStorage().isLogin = true
            LocalStorage().isDone = false
            findNavController().navigate(
                R.id.action_registerFragment_to_tasksFragment
            )
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }.launchIn(lifecycleScope)


        viewModel.getMessageFlow.onEach {
            toast("Siz bul nomerden register qilip bolg'ansiz")
        }.launchIn(lifecycleScope)
    }
}
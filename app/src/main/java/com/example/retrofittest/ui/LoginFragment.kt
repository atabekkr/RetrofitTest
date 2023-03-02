package com.example.retrofittest.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.retrofittest.R
import com.example.retrofittest.data.local.LocalStorage
import com.example.retrofittest.databinding.FragmentLoginBinding
import com.example.retrofittest.presentation.MainViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment: Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        viewModel = ViewModelProvider(
            requireActivity(),
             ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        )[MainViewModel::class.java]


        initObservers()

        binding.apply {
            tvLogup.setOnClickListener {
                findNavController().navigate(
                    R.id.action_loginFragment_to_registerFragment
                )
            }

            btnLogin.setOnClickListener {
                val password = etPassword.text.toString()
                val phone = etPhone.text.toString()
                if (password.isNotEmpty() && phone.isNotEmpty()) {
                    lifecycleScope.launchWhenResumed {
                        viewModel.isLogin(phone, password)
                    }
                    findNavController().navigate(
                        R.id.action_loginFragment_to_tasksFragment
                    )
                } else {
                    Toast.makeText(requireContext(), "Login ya Parol qate", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initObservers() {
        viewModel.getLoginFlow.onEach {
            LocalStorage().token = it
        }.launchIn(lifecycleScope)
    }
}
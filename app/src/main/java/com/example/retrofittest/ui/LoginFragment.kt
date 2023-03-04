package com.example.retrofittest.ui

import android.os.Bundle
import android.util.Log
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
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import packagename.telegramclone.utils.toast

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var success: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        )[MainViewModel::class.java]


        initObservers()

        binding.apply {
            btnRegister.setOnClickListener {
                findNavController().navigate(
                    R.id.action_loginFragment_to_registerFragment
                )
            }

            try {
                fab.setOnClickListener {
                    val password = etPassword.text.toString()
                    val phone = etPhone.text.toString()

                    if (password.isNotEmpty() && phone.isNotEmpty()) {

                        lifecycleScope.launch {
                            viewModel.isLogin(phone, password)
                        }

                    } else {
                        Toast.makeText(requireContext(), "Login ya Parol qate", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun initObservers() {
        viewModel.getLoginFlow.onEach {
            LocalStorage().token = it
            LocalStorage().isLogin = true
            findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToTasksFragment()
            )
        }.launchIn(lifecycleScope)

        viewModel.getMessageLoginFlow.onEach {
            toast("Siz registratsiyadan otpegen ekensiz")
        }.launchIn(lifecycleScope)
    }
}
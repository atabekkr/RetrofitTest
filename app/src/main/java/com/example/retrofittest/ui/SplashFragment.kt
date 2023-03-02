package com.example.retrofittest.ui

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.retrofittest.R
import kotlinx.coroutines.delay

class SplashFragment : Fragment(R.layout.fragment_splash) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenResumed {
            delay(2000)
                findNavController().navigate(
                    R.id.action_splashFragment_to_loginFragment
                )

        }
    }
}
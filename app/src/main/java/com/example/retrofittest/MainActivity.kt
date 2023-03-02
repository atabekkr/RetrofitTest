package com.example.retrofittest

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.retrofittest.databinding.ActivityMainBinding
import com.example.retrofittest.data.models.RegisterRequestBodyData
import com.example.retrofittest.presentation.MainViewModel
import com.example.retrofittest.retrofit.RetrofitHelper
import com.example.retrofittest.retrofit.TodoApi

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var viewModel: MainViewModel

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)
        )[MainViewModel::class.java]

        //initObservers()


        val retrofit = RetrofitHelper.getInstance()

        val api = retrofit.create(TodoApi::class.java)


        val token = "13|u6k8KsWumueN5jH2Xq8HP9cF7GHyUjwd5JTjM4ed"
        lifecycleScope.launchWhenResumed {

//           val responce = api.getAllTasks("Bearer $token")
//
//            if (responce.isSuccessful) {
//                Log.d("TTTT", "${responce.body()}")
//            } else {
//                Log.d("TTTT", "Message: ${responce.message()}")
//            }


//            val responce =
//                api.registerApi(RegisterRequestBodyData("Atash", "12345679", "+998935647391"))
//            if (responce.isSuccessful) {
//                val responceData = responce.body()?.payload?.name
//                binding.text.text = responceData
//                Toast.makeText(this@MainActivity, "Success $responceData", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()
//            }
        }


    }
}
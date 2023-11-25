package com.example.appcartaoservidorv1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.appcartaoservidorv1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // Variavel de Binding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Indica a view a ser inflada
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        // Configura o ciclo de vida
        binding.lifecycleOwner = this
    }
}
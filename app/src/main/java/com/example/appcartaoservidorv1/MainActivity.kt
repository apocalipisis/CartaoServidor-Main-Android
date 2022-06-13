package com.example.appcartaoservidorv1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.appcartaoservidorv1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // Variavel de Binding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Indica a view a ser inflada
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        // Muda a cor da barra de status
        window.statusBarColor = ContextCompat.getColor(this, R.color.primaryColor)
        // Configura o ciclo de vida
        binding.setLifecycleOwner(this)
    }
}
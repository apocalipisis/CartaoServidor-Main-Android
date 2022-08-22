package com.example.appcartaoservidorv1.viewmodels.comerciante

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FuncionarioscomercianteViewModel(val matricula: String, val token: String) : ViewModel() {
}

// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class FuncionarioscomercianteViewModelFactory(
    private val matricula: String,
    private val token: String,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FuncionarioscomercianteViewModel::class.java)) {
            return FuncionarioscomercianteViewModel(matricula, token) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
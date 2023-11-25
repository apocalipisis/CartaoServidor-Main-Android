package com.example.appcartaoservidorv1.viewmodels.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class UsuarionaopermitidoViewModel() :
    ViewModel() {
}

// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class UsuarionaopermitidoViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsuarionaopermitidoViewModel::class.java)) {
            return UsuarionaopermitidoViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


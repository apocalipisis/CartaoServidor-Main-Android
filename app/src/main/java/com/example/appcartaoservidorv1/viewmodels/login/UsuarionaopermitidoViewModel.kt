package com.example.appcartaoservidorv1.viewmodels.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.viewmodels.LoginViewModel

class UsuarionaopermitidoViewModel(val tipo: String) :
    ViewModel() {
}

// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class UsuarionaopermitidoViewModelFactory(val tipo:String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsuarionaopermitidoViewModel::class.java)) {
            return UsuarionaopermitidoViewModel(tipo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


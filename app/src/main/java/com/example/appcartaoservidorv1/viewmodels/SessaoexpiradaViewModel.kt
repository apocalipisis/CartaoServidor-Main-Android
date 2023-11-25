package com.example.appcartaoservidorv1.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SessaoexpiradaViewModel(): ViewModel() {
}


// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class SessaoexpiradaViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SessaoexpiradaViewModel::class.java)) {
            return SessaoexpiradaViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
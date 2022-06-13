package com.example.appcartaoservidorv1.viewmodels.comerciante

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class VendercomercianteViewModel(val valor: Float) : ViewModel() {

}

// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class VendercomercianteViewModelFactory(private val valor: Float) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VendercomercianteViewModel::class.java)) {
            return VendercomercianteViewModel(valor) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
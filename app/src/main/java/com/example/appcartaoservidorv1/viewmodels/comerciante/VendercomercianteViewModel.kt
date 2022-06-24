package com.example.appcartaoservidorv1.viewmodels.comerciante

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class VendercomercianteViewModel(
    val valor: Float,
    val nomeComerciante: String,
    val matriculaComerciante: String,
    val token: String,
) : ViewModel() {

}

// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class VendercomercianteViewModelFactory(
    private val valor: Float,
    private val nomeComerciante: String,
    private val matriculaComerciante: String,
    private val token: String,
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VendercomercianteViewModel::class.java)) {
            return VendercomercianteViewModel(valor, nomeComerciante, matriculaComerciante, token) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
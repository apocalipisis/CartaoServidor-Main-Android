package com.example.appcartaoservidorv1.viewmodels.comerciante

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class InserirvalorViewModel(val nomeComerciante: String, val matriculaComerciante: String, val token: String) :
    ViewModel() {
//    var valor: Double = 0.0
    val valor = MutableLiveData<Double>()

    init {
        valor.value = 0.0
    }

}

// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class InserirvalorViewModelFactory(
    private val nomeComerciante: String,
    private val matriculaComerciante: String,
    private val token: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InserirvalorViewModel::class.java)) {
            return InserirvalorViewModel(nomeComerciante, matriculaComerciante, token) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
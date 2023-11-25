package com.example.appcartaoservidorv1.viewmodels.comerciantefuncionario

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ComercianteFuncionarioVendaValorViewModel(
    val matriculaComerciante: String,
    val nomeComerciante: String,
    val matriculaVendedor: String,
    val nomeVendedor: String,
    val token: String
) :
    ViewModel() {
    val valor = MutableLiveData<Double>()

    init {
        valor.value = 0.0
    }
}

// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class ComercianteFuncionarioVendaValorViewModelFactory(
    private val matriculaComerciante: String,
    private val nomeComerciante: String,
    private val matriculaVendedor: String,
    private val nomeVendedor: String,
    private val token: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ComercianteFuncionarioVendaValorViewModel::class.java)) {
            return ComercianteFuncionarioVendaValorViewModel(
                matriculaComerciante,
                nomeComerciante,
                matriculaVendedor,
                nomeVendedor,
                token
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
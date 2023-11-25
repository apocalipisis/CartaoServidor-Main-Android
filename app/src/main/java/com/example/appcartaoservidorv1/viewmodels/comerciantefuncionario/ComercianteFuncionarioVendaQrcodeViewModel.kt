package com.example.appcartaoservidorv1.viewmodels.comerciantefuncionario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.viewmodels.comerciantegerente.ComerciantegerenteVendaQrcodeViewModel

class ComercianteFuncionarioVendaQrcodeViewModel (
    val valor: Float,
    val matriculaComerciante: String,
    val nomeComerciante: String,
    val matriculaVendedor: String,
    val nomeVendedor: String,
    val token: String,
) : ViewModel() {

}

// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class ComercianteFuncionarioVendaQrcodeViewModelFactory(
    private val valor: Float,
    private val matriculaComerciante: String,
    private val nomeComerciante: String,
    private val matriculaVendedor: String,
    private val nomeVendedor: String,
    private val token: String,
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ComercianteFuncionarioVendaQrcodeViewModel::class.java)) {
            return ComercianteFuncionarioVendaQrcodeViewModel(
                valor,
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
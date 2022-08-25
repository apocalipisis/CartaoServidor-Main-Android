package com.example.appcartaoservidorv1.viewmodels.comerciantegerente

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ComerciantegerenteinfoViewModel(
    val nome: String,
    val matricula: String,
    val tipoUsuario: String,
    val status: String,
    val cpf: String,
    val matriculaMae: String,
    val cnpj: String,
) : ViewModel()  {

}

// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class ComerciantegerenteinfoViewModelFactory(
    private val nome: String,
    private val matricula: String,
    private val tipoUsuario: String,
    private val status: String,
    private val cpf: String,
    private val matriculaMae: String,
    private val cnpj: String,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ComerciantegerenteinfoViewModel::class.java)) {
            return ComerciantegerenteinfoViewModel(
                nome,
                matricula,
                tipoUsuario,
                status,
                cpf,
                matriculaMae,
                cnpj,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
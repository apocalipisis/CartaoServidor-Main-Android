package com.example.appcartaoservidorv1.viewmodels.comerciantefuncionario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.viewmodels.comerciantegerente.ComerciantegerenteinfoViewModel

class ComercianteFuncionarioInfoViewModel (
    val nome: String,
    val matricula: String,
    val tipoUsuario: String,
    val status: String,
    val matriculaMae: String,
    val cnpj: String,
    val nomeComerciante: String,
) : ViewModel()  {

}

// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class ComercianteFuncionarioInfoViewModelFactory(
    private val nome: String,
    private val matricula: String,
    private val tipoUsuario: String,
    private val status: String,
    private val matriculaMae: String,
    private val cnpj: String,
    private val nomeComerciante: String,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ComercianteFuncionarioInfoViewModel::class.java)) {
            return ComercianteFuncionarioInfoViewModel(
                nome,
                matricula,
                tipoUsuario,
                status,
                matriculaMae,
                cnpj,
                nomeComerciante,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
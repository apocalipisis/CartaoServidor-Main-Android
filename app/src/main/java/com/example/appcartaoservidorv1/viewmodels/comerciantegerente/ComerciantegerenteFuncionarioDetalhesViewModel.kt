package com.example.appcartaoservidorv1.viewmodels.comerciantegerente

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.models.Funcionario
import com.example.appcartaoservidorv1.viewmodels.comerciante.FuncionariocomerciantedetalhesViewModel

class ComerciantegerenteFuncionarioDetalhesViewModel(
    val funcionario: Funcionario,
    val token: String
) :
    ViewModel() {
}

// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class ComerciantegerenteFuncionarioDetalhesViewModelFactory(
    private val funcionario: Funcionario,
    private val token: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ComerciantegerenteFuncionarioDetalhesViewModel::class.java)) {
            return ComerciantegerenteFuncionarioDetalhesViewModel(funcionario, token) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

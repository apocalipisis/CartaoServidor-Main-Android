package com.example.appcartaoservidorv1.viewmodels.comerciante

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.models.Funcionario
import com.example.appcartaoservidorv1.models.Gerente

class FuncionariocomerciantedetalhesViewModel(val funcionario: Funcionario, val token: String) :
    ViewModel() {
}


// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class FuncionariocomerciantedetalhesViewModelFactory(
    private val funcionario: Funcionario,
    private val token: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FuncionariocomerciantedetalhesViewModel::class.java)) {
            return FuncionariocomerciantedetalhesViewModel(funcionario, token) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

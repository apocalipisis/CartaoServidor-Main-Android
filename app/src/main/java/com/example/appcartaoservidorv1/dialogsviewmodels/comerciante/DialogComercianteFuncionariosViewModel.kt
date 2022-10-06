package com.example.appcartaoservidorv1.dialogsviewmodels.comerciante

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DialogComercianteFuncionariosViewModel(val matricula: String, val token: String) :
    ViewModel() {

}

// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class DialogComercianteFuncionariosViewModelFactory(
    private val matricula: String,
    private val token: String,
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DialogComercianteFuncionariosViewModel::class.java)) {
            return DialogComercianteFuncionariosViewModel(matricula, token) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
package com.example.appcartaoservidorv1.viewmodels.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class UsuarioinativoViewModel(val nome: String, val matricula: String) : ViewModel() {

}

// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class UsuarioinativoViewModelFactory(val nome: String, val matricula: String) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsuarioinativoViewModel::class.java)) {
            return UsuarioinativoViewModel(nome, matricula) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

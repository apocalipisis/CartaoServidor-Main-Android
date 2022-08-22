package com.example.appcartaoservidorv1.viewmodels.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class UsuarioinativoViewModel (val nome: String): ViewModel(){
    private var _mensagem = MutableLiveData<String>()
    val mensagem: LiveData<String>
        get() = _mensagem

    init {
        _mensagem.value = "Olá, $nome"
    }

}

// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class UsuarioinativoViewModelFactory(val nome:String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsuarioinativoViewModel::class.java)) {
            return UsuarioinativoViewModel(nome) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

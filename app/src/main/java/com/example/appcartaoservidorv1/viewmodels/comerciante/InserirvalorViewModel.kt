package com.example.appcartaoservidorv1.viewmodels.comerciante

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.text.DecimalFormat


class InserirvalorViewModel() :
    ViewModel() {
    // Valor a ser passado
    val Valor = MutableLiveData<String>()

    init {
        Valor.value = "0,00"
    }

}

// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class InserirvalorViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InserirvalorViewModel::class.java)) {
            return InserirvalorViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
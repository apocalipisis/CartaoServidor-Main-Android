package com.example.appcartaoservidorv1.viewmodels.comerciante

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.text.DecimalFormat


class InserirvalorViewModel(val nomeComerciante : String) :
    ViewModel() {
    // Valor a ser passado
    val ValorStr = MutableLiveData<String>()
    val ValorFloat = MutableLiveData<Float>()

    init {
        ValorStr.value = "0,00"
    }

}

// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class InserirvalorViewModelFactory(private val nomeComerciante: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InserirvalorViewModel::class.java)) {
            return InserirvalorViewModel(nomeComerciante) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
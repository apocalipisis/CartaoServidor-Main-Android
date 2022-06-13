package com.example.appcartaoservidorv1.viewmodels.comerciante

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.text.DecimalFormat

class InserirsenhaViewModel (val matricula: String, val nome: String) :
    ViewModel() {


}

// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class InserirsenhaViewModelFactory(
    private val matricula: String,
    private val nome: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InserirsenhaViewModel::class.java)) {
            return InserirsenhaViewModel(matricula, nome) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
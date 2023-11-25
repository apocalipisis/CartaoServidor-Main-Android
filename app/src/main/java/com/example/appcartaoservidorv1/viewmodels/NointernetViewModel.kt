package com.example.appcartaoservidorv1.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NointernetViewModel (): ViewModel(){

}


// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class NointernetViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NointernetViewModel::class.java)) {
            return NointernetViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

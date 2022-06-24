package com.example.appcartaoservidorv1.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NointernetstatusvendaViewModel  (): ViewModel(){

}


// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class NointernetstatusvendaViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NointernetstatusvendaViewModel::class.java)) {
            return NointernetstatusvendaViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

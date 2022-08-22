package com.example.appcartaoservidorv1.viewmodels.comerciante

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.models.Gerente
import com.example.appcartaoservidorv1.models.Transacao
import com.example.appcartaoservidorv1.services.utilidades.dataCompletaMesExtenso
import com.example.appcartaoservidorv1.services.utilidades.formatDinheiro

class GerentescomerciantedetalhesViewModel(val gerente: Gerente, val token:String):ViewModel() {
}

// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class GerentescomerciantedetalhesViewModelFactory(private val gerente: Gerente, private val token:String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GerentescomerciantedetalhesViewModel::class.java)) {
            return GerentescomerciantedetalhesViewModel(gerente, token) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

package com.example.appcartaoservidorv1.dialogsviewmodels.servidor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.models.ComercianteModel

class DialogServidorComerciantesdetalhesViewModel (val comerciante: ComercianteModel) : ViewModel() {
}

// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class DialogServidorComerciantesdetalhesViewModelFactory(private val comerciante: ComercianteModel) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DialogServidorComerciantesdetalhesViewModel::class.java)) {
            return DialogServidorComerciantesdetalhesViewModel(comerciante) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
package com.example.appcartaoservidorv1.viewmodels.servidor

import android.util.Log
import androidx.lifecycle.*
import com.example.appcartaoservidorv1.Constantes
import com.example.appcartaoservidorv1.models.cartao.DTONovoCartao
import com.example.appcartaoservidorv1.services.api.APIServidor
import kotlinx.coroutines.launch

class ServidorCartaoSolicitacaodetalhesViewModel(
    val matricula: String,
    val token: String,
) : ViewModel() {

}

// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class ServidorCartaoSolicitacaodetalhesViewModelFactory(
    private val matricula: String,
    private val token: String,
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ServidorCartaoSolicitacaodetalhesViewModel::class.java)) {
            return ServidorCartaoSolicitacaodetalhesViewModel(matricula, token) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
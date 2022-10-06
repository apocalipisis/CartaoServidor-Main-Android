package com.example.appcartaoservidorv1.dialogsviewmodels.servidor

import android.util.Log
import androidx.lifecycle.*
import com.example.appcartaoservidorv1.Constantes
import com.example.appcartaoservidorv1.models.auxiliares.ParBoolString
import com.example.appcartaoservidorv1.models.cartao.DTONovoCartao
import com.example.appcartaoservidorv1.services.api.APIServidor
import kotlinx.coroutines.launch

class DialogServidorCartaoCancelarsolicitacaoViewModel(val matricula: String, val token: String) :
    ViewModel() {
    // Status da consulta a API Cartao Servidor
    enum class ApiStatus { LOADING, ERROR, DONE }

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    // Resposta da API
    var response: ParBoolString = ParBoolString(false, Constantes.Erro4)

    fun cancelarSolicitacao() {
        _status.value = ApiStatus.LOADING
        viewModelScope.launch {
            try {
                response = APIServidor.APIServidorService.cancelarSolicitacaoNovoCartaoServidor(
                    matricula,
                    token
                )
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
            }
        }
    }
}

// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class DialogServidorCartaoCancelarsolicitacaoViewModelFactory(
    private val matricula: String,
    private val token: String,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DialogServidorCartaoCancelarsolicitacaoViewModel::class.java)) {
            return DialogServidorCartaoCancelarsolicitacaoViewModel(matricula, token) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
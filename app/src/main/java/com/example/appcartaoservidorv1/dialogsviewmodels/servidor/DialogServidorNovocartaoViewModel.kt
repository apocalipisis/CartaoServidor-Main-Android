package com.example.appcartaoservidorv1.dialogsviewmodels.servidor

import androidx.lifecycle.*
import com.example.appcartaoservidorv1.Constantes
import com.example.appcartaoservidorv1.models.auxiliares.ParBoolString
import com.example.appcartaoservidorv1.models.cartao.DTONovoCartao
import com.example.appcartaoservidorv1.services.api.APIComerciante
import com.example.appcartaoservidorv1.services.api.APIServidor
import kotlinx.coroutines.launch

class DialogServidorNovocartaoViewModel(val matricula: String, val token: String) : ViewModel() {
    // Resposta da API
    private val _response = MutableLiveData<ParBoolString>()
    val response: LiveData<ParBoolString>
        get() = _response

    // Status da consulta a API
    enum class ApiStatus { LOADING, ERROR, DONE }

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    fun novoCartao(
        matricula: String,
        token: String
    ) {
        _status.value = ApiStatus.LOADING
        viewModelScope.launch {
            try {
                _response.value =
                    APIServidor.APIServidorService.solicitarNovoCartao(
                        matricula,
                        token
                    )
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _response.value = ParBoolString(false, Constantes.Erro4)
                _status.value = ApiStatus.ERROR
            }
        }
    }
}

// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class DialogServidorNovocartaoViewModelFactory(
    private val matricula: String,
    private val token: String,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DialogServidorNovocartaoViewModel::class.java)) {
            return DialogServidorNovocartaoViewModel(matricula, token) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
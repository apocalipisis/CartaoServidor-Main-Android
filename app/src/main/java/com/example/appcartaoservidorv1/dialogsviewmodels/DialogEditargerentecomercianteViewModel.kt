package com.example.appcartaoservidorv1.dialogsviewmodels

import android.util.Log
import androidx.lifecycle.*
import com.example.appcartaoservidorv1.models.auxiliares.ParBoolString
import com.example.appcartaoservidorv1.services.myAndroidApi
import kotlinx.coroutines.launch

class DialogEditargerentecomercianteViewModel(val matricula: String, val token: String) : ViewModel() {

    // Resposta da API
    private val _response = MutableLiveData<ParBoolString>()
    val response: LiveData<ParBoolString>
        get() = _response

    // Status da consulta a API
    enum class ApiStatus { LOADING, ERROR, DONE }

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    fun editarGerente(
        matricula: String,
        isAtivo: Boolean,
        token: String,
    ) {
        _status.value = ApiStatus.LOADING
        viewModelScope.launch {
            try {
                _response.value =
                    myAndroidApi.retrofitService.editarGerente(
                        matricula,
                        isAtivo,
                        token,
                    )
//                _response.value = ParBoolString(true, "Teste Status")
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _response.value = ParBoolString(false, "Problemas no servidor, tente novamente")
                _status.value = ApiStatus.ERROR
            }
        }

    }
}

// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class DialogEditargerentecomercianteViewModelFactory(
    private val matricula: String,
    private val token: String,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DialogEditargerentecomercianteViewModel::class.java)) {
            return DialogEditargerentecomercianteViewModel(matricula, token) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
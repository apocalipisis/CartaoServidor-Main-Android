package com.example.appcartaoservidorv1.dialogsviewmodels.comerciantegerente

import androidx.lifecycle.*
import com.example.appcartaoservidorv1.models.auxiliares.ParBoolString
import com.example.appcartaoservidorv1.services.api.APIComerciante
import com.example.appcartaoservidorv1.services.api.APIComercianteGerente
import kotlinx.coroutines.launch

class DialogComerciantegerenteFuncionarioEditarViewModel(val matricula: String, val token: String) : ViewModel() {

    // Resposta da API
    private val _response = MutableLiveData<ParBoolString>()
    val response: LiveData<ParBoolString>
        get() = _response

    // Status da consulta a API
    enum class ApiStatus { LOADING, ERROR, DONE }

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    fun editarFuncionario(
        matricula: String,
        isAtivo: Boolean,
        token: String,
    ) {
        _status.value = ApiStatus.LOADING
        viewModelScope.launch {
            try {
                _response.value =
                    APIComercianteGerente.APIComercianteGerenteService.editarFuncionarioComercianteGerente(
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
class DialogComerciantegerenteFuncionarioEditarViewModelFactory(
    private val matricula: String,
    private val token: String,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DialogComerciantegerenteFuncionarioEditarViewModel::class.java)) {
            return DialogComerciantegerenteFuncionarioEditarViewModel(matricula, token) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
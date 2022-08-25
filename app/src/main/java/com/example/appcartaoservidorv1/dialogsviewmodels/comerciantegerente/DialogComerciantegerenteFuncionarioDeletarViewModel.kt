package com.example.appcartaoservidorv1.dialogsviewmodels.comerciantegerente

import androidx.lifecycle.*
import com.example.appcartaoservidorv1.models.auxiliares.ParBoolString
import com.example.appcartaoservidorv1.services.api.APIComercianteGerente
import kotlinx.coroutines.launch

class DialogComerciantegerenteFuncionarioDeletarViewModel(val matricula: String, val token: String) :
    ViewModel()  {


    // Resposta da API
    private val _response = MutableLiveData<ParBoolString>()
    val response: LiveData<ParBoolString>
        get() = _response

    // Status da consulta a API
    enum class ApiStatus { LOADING, ERROR, DONE }

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    var deletado: Boolean = false

    fun deletarFuncionario(
        matricula: String,
        token: String
    ) {
        _status.value = ApiStatus.LOADING
        viewModelScope.launch {
            try {
                _response.value =
                    APIComercianteGerente.APIComercianteGerenteService.deletarFuncionarioComercianteGerente(
                        matricula,
                        token
                    )
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _response.value = ParBoolString(false, "Problemas no servidor, tente novamente")
                _status.value = ApiStatus.ERROR
            }
        }
    }
}

// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class DialogComerciantegerenteFuncionarioDeletarViewModelFactory(
    private val matricula: String,
    private val token: String,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DialogComerciantegerenteFuncionarioDeletarViewModel::class.java)) {
            return DialogComerciantegerenteFuncionarioDeletarViewModel(matricula, token) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
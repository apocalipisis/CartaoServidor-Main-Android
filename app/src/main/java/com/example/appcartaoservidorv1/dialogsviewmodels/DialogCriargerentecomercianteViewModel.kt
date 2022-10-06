package com.example.appcartaoservidorv1.dialogsviewmodels

import androidx.lifecycle.*
import com.example.appcartaoservidorv1.Constantes
import com.example.appcartaoservidorv1.models.auxiliares.ParBoolString
import com.example.appcartaoservidorv1.services.api.APIComerciante
import kotlinx.coroutines.launch

class CriargerentecomercianteViewModel(val matricula: String, val token: String) : ViewModel() {
    // Resposta da API
    private val _response = MutableLiveData<ParBoolString>()
    val response: LiveData<ParBoolString>
        get() = _response

    // Status da consulta a API
    enum class ApiStatus { LOADING, ERROR, DONE }

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    // Nome digitada pelo usuario
    var nome = MutableLiveData<String>()

    // Cpf digitada pelo usuario
    var cpf = MutableLiveData<String>()

    init {
        nome.value = ""
        cpf.value = ""
    }

    //----------------------------------------------------------------------------------------------
    fun criarGerente(
        nome: String,
        cpf: String,
        matriculaMae: String,
        token: String
    ) {
        _status.value = ApiStatus.LOADING
        viewModelScope.launch {
            try {
                _response.value =
                    APIComerciante.APIComercianteService.inserirGerenteComerciante(
                        nome,
                        cpf,
                        matriculaMae,
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
class CriargerentecomercianteViewModelFactory(
    private val matricula: String,
    private val token: String,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CriargerentecomercianteViewModel::class.java)) {
            return CriargerentecomercianteViewModel(matricula,token) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
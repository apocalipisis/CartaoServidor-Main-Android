package com.example.appcartaoservidorv1.dialogsviewmodels

import androidx.lifecycle.*
import com.example.appcartaoservidorv1.models.auxiliares.ParBoolString
import com.example.appcartaoservidorv1.services.api.APIComerciante
import kotlinx.coroutines.launch

class DialogCriarfuncionariocomercianteViewModel(val matricula: String, val token: String) : ViewModel() {

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

    // Informa se o nome digitado pelo usuário é valido
    var isNomeValido = MutableLiveData<String>()

    // Informa se o cpf digitado pelo usuário é valido

    init {
        nome.value = ""

        isNomeValido.value = ""
    }

    fun criarGerente(
        nome: String,
        isAtivo: Boolean,
        matriculaMae: String,
        token: String
    ) {
        isNomeValido.value = ""

        if (!verificaNome(nome)) {
            isNomeValido.value = "Nome Inválido"
            return
        }
        _status.value = ApiStatus.LOADING
        viewModelScope.launch {
            try {
                _response.value =
                    APIComerciante.APIComercianteService.inserirFuncionarioComerciante(
                        nome,
                        isAtivo,
                        matriculaMae,
                        token
                    )
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _response.value = ParBoolString(false, "Problemas no servidor, tente novamente")
                _status.value = ApiStatus.ERROR
            }
        }
    }

    private fun verificaNome(nome: String): Boolean {
        return nome.isNotEmpty()
    }


}


// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class DialogCriarfuncionariocomercianteViewModelFactory(
    private val matricula: String,
    private val token: String,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DialogCriarfuncionariocomercianteViewModel::class.java)) {
            return DialogCriarfuncionariocomercianteViewModel(matricula, token) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
package com.example.appcartaoservidorv1.dialogsviewmodels.comerciantegerente

import androidx.lifecycle.*
import com.example.appcartaoservidorv1.models.auxiliares.ParBoolString
import com.example.appcartaoservidorv1.services.api.APIComerciante
import com.example.appcartaoservidorv1.services.api.APIComercianteGerente
import kotlinx.coroutines.launch

class DialogComerciantegerenteFuncionarioCriarViewModel (val matricula: String, val token: String) : ViewModel() {

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

    // Informa se o nome digitado pelo usuário é valido
    var isNomeValido = MutableLiveData<String>()


    init {
        nome.value = ""

        isNomeValido.value = ""
    }

    fun criarFuncionario(
        nome: String,
        isAtivo: Boolean,
        matricula: String,
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
                    APIComercianteGerente.APIComercianteGerenteService.inserirFuncionarioComercianteGerente(
                        nome,
                        isAtivo,
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

    private fun verificaNome(nome: String): Boolean {
        return nome.isNotEmpty()
    }


}


// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class DialogComerciantegerenteFuncionarioCriarViewModelFactory(
    private val matricula: String,
    private val token: String,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DialogComerciantegerenteFuncionarioCriarViewModel::class.java)) {
            return DialogComerciantegerenteFuncionarioCriarViewModel(matricula, token) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
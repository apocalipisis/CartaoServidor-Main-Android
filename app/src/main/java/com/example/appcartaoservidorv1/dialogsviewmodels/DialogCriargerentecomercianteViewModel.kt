package com.example.appcartaoservidorv1.dialogsviewmodels

import android.util.Log
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.*
import com.example.appcartaoservidorv1.models.auxiliares.ParBoolString
import com.example.appcartaoservidorv1.services.myAndroidApi
import kotlinx.coroutines.launch

class CriargerentecomercianteViewModel : ViewModel() {
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

    // Informa se o nome digitado pelo usuário é valido
    var isNomeValido = MutableLiveData<String>()

    // Informa se o cpf digitado pelo usuário é valido
    var isCpfValido = MutableLiveData<String>()

    init {
        nome.value = ""
        cpf.value = ""

        isNomeValido.value = ""
        isCpfValido.value = ""
    }

    fun criarGerente(
        nome: String,
        cpf: String,
        isAtivo: Boolean,
        matriculaMae: String,
        token: String
    ) {
        isNomeValido.value = ""
        isCpfValido.value = ""

        if (!verificaNome(nome)) {
            isNomeValido.value = "Nome Inválido"
            return
        }

        if (!verificaCpf(cpf)) {
            isCpfValido.value = "Cpf Inválido"
            return
        }

        _status.value = ApiStatus.LOADING
        viewModelScope.launch {
            try {
                _response.value =
                    myAndroidApi.retrofitService.inserirGerente(
                        nome,
                        cpf,
                        isAtivo,
                        matriculaMae,
                        token
                    )
//                _response.value = ParBoolString(true, "Usuario inserido com Sucesso")
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

    private fun verificaCpf(cpf: String): Boolean {
        return cpf.isDigitsOnly() && cpf.length == 11 && cpf.isNotEmpty()
    }

}

// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class CriargerentecomercianteViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CriargerentecomercianteViewModel::class.java)) {
            return CriargerentecomercianteViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
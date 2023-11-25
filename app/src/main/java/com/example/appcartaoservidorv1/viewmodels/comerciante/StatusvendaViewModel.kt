package com.example.appcartaoservidorv1.viewmodels.comerciante

import android.util.Log
import androidx.lifecycle.*
import com.example.appcartaoservidorv1.Constantes
import com.example.appcartaoservidorv1.models.DTO_InserirTransacao
import com.example.appcartaoservidorv1.models.auxiliares.ParBoolString
import com.example.appcartaoservidorv1.services.api.APIComerciante
import kotlinx.coroutines.launch

class StatusvendaViewModel(
    val valor: Float,
    val matricula: String,
    val matriculaComerciante: String,
    val senha: String,
    val numeroCartao: String,
    val token: String,
) : ViewModel() {

    // Status da consulta a API
    enum class ApiStatus { LOADING, ERROR, DONE }

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    // Resposta da API
    lateinit var response: ParBoolString

    // Mensagem a ser mostrada
    private val _mensagem = MutableLiveData<String>()
    val mensagem: LiveData<String>
        get() = _mensagem


    init {
        inserirTransacao(valor, matricula, matriculaComerciante, senha, numeroCartao, token)
    }

    // Consulta a api
    private fun inserirTransacao(
        valor: Float,
        matricula: String,
        matriculaComerciante: String,
        senha: String,
        numeroCartao: String,
        token: String,
    ) {
        _status.value = ApiStatus.LOADING
        viewModelScope.launch {
            try {
                response = APIComerciante.APIComercianteService.inserirVendaComerciante(
                    matricula,
                    matriculaComerciante,
                    matriculaComerciante,
                    valor.toString(),
                    numeroCartao,
                    senha,
                    token,
                )
                _mensagem.value = response.s
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _mensagem.value = Constantes.Erro4
                _status.value = ApiStatus.ERROR
            }
        }
    }
}

// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class StatusvendaViewModelFactory(
    private val valor: Float,
    private val matricula: String,
    private val matriculaComerciante: String,
    private val senha: String,
    private val numeroCartao: String,
    private val token: String,
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StatusvendaViewModel::class.java)) {
            return StatusvendaViewModel(
                valor,
                matricula,
                matriculaComerciante,
                senha,
                numeroCartao,
                token
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
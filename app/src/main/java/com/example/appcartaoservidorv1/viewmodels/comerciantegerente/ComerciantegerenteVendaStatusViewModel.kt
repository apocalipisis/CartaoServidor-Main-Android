package com.example.appcartaoservidorv1.viewmodels.comerciantegerente

import android.util.Log
import androidx.lifecycle.*
import com.example.appcartaoservidorv1.Constantes
import com.example.appcartaoservidorv1.models.auxiliares.ParBoolString
import com.example.appcartaoservidorv1.services.api.APIComercianteGerente
import kotlinx.coroutines.launch

class ComerciantegerenteVendaStatusViewModel(
    val matriculaCliente: String,
    val matriculaComerciante: String,
    val matriculaVendedor: String,
    val valor: Float,
    val senha: String,
    val nomeVendedor: String,
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
        inserirTransacao(
            matriculaCliente,
            matriculaComerciante,
            matriculaVendedor,
            valor,
            senha,
            numeroCartao,
            token
        )
    }

    // Consulta a api
    private fun inserirTransacao(
        matriculaCliente: String,
        matriculaComerciante: String,
        matriculaVendedor: String,
        valor: Float,
        senha: String,
        numeroCartao: String,
        token: String,
    ) {
        _status.value = ApiStatus.LOADING
        viewModelScope.launch {
            try {
                response =
                    APIComercianteGerente.APIComercianteGerenteService.inserirVendaComercianteGerente(
                        matriculaCliente,
                        matriculaComerciante,
                        matriculaVendedor,
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
class ComerciantegerenteVendaStatusViewModelFactory(
    private val matriculaCliente: String,
    private val matriculaComerciante: String,
    private val matriculaVendedor: String,
    private val valor: Float,
    private val senha: String,
    private val nomeVendedor: String,
    private val numeroCartao: String,
    private val token: String,
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ComerciantegerenteVendaStatusViewModel::class.java)) {
            return ComerciantegerenteVendaStatusViewModel(
                matriculaCliente,
                matriculaComerciante,
                matriculaVendedor,
                valor,
                senha,
                nomeVendedor,
                numeroCartao,
                token,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
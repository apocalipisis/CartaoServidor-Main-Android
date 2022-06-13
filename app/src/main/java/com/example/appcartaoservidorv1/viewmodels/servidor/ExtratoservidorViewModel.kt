package com.example.appcartaoservidorv1.viewmodels.servidor

import android.util.Log
import androidx.lifecycle.*
import com.example.appcartaoservidorv1.models.DTO_Servidor
import com.example.appcartaoservidorv1.models.Transacao
import com.example.appcartaoservidorv1.services.myAndroidApi
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

class ExtratoservidorViewModel(val matricula: String, val listTransacao: Array<Transacao>) :
    ViewModel() {
    // Status da consulta a API
    enum class ApiStatus { LOADING, ERROR, DONE }
    private val _status = MutableLiveData<ServidorViewModel.ApiStatus>()
    val status: LiveData<ServidorViewModel.ApiStatus>
        get() = _status
    // Resposta da API
    lateinit var response: List<Transacao>

    // Controla a navegação para uma página de detalhes
    private val _navigateToTransacaoDetail = MutableLiveData<Transacao>()
    val navigateToTransacaoDetail
        get() = _navigateToTransacaoDetail

    // Transações
    private val _transacoes = MutableLiveData<List<Transacao>>()
    val transacoes: LiveData<List<Transacao>>
        get() = _transacoes

    // Passa o valor da transação para frente (transação detalhes)
    fun onTransacaoClicked(transacao: Transacao) {
        _navigateToTransacaoDetail.value = transacao
    }
    // Limpa o valor da transação
    fun onTransacaoDetailNavigated() {
        _navigateToTransacaoDetail.value = null
    }

    init {
//        ConsultaExtrato(matricula)
        _transacoes.value = listTransacao.toList()
    }

//    fun ConsultaExtrato(matricula: String) {
//        _status.value = ServidorViewModel.ApiStatus.LOADING
////        Timber.i("Consultando o extrato")
//        Log.i("ExtratoViewModel", "Consultando o extrato")
//
//        viewModelScope.launch {
//            try {
//                response = myAndroidApi.retrofitService.extratoServidor(matricula)
////                Timber.i("${response}")
//                Log.i("ExtratoViewModel", "${response}")
//                _transacoes.value = response
////                _mensagemAPI.value = response.transacoes.toString()
////                _saldo.value = formatSaldo(response.saldo)
////                _status.value = ServidorViewModel.ApiStatus.DONE
//            } catch (e: Exception) {
//                _status.value = ServidorViewModel.ApiStatus.ERROR
////                Timber.i("${e}")
//                Log.i("ExtratoViewModel", "${e}")
////                _mensagemAPI.value = "Problemas no servidor, tente novamente"
//            }
//        }
//    }


}

// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class ExtratoservidorViewModelFactory(
    private val matricula: String,
    private val listTransacao: Array<Transacao>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExtratoservidorViewModel::class.java)) {
            return ExtratoservidorViewModel(matricula, listTransacao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
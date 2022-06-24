package com.example.appcartaoservidorv1.viewmodels.comerciante

import android.util.Log
import androidx.lifecycle.*
import com.example.appcartaoservidorv1.dataEmMes
import com.example.appcartaoservidorv1.formatDinheiro
import com.example.appcartaoservidorv1.models.DTO_Comerciante
import com.example.appcartaoservidorv1.services.myAndroidApi
import kotlinx.coroutines.launch
import java.util.*

class ComercianteViewModel(val matricula: String, val nome: String, val token: String) : ViewModel() {

    // Status da consulta a API
    enum class ApiStatus { LOADING, ERROR, DONE }
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status
    // Mensagem sobre consulta a API
    private val _mensagemAPI = MutableLiveData<String>()
    val mensagemAPI: LiveData<String>
        get() = _mensagemAPI
    // Resposta da API
    lateinit var response: DTO_Comerciante

    // Variavel com a descrição do faturamento
    private val _descricaoFaturamento = MutableLiveData<String>()
    val descricaoFaturamento: LiveData<String>
        get() = _descricaoFaturamento
    // Faturamento
    private val _faturamento = MutableLiveData<String>()
    val faturamento: LiveData<String>
        get() = _faturamento

    // Instancia da data atual
    private val data = Calendar.getInstance().time

    init {
        _descricaoFaturamento.value = "Faturamento no mês de ${dataEmMes(data)}"
        ConsultaComerciante(matricula)
//        response.matricula = "00002"
    }

    // Consulta a api
    fun ConsultaComerciante(matricula: String) {
        _status.value = ApiStatus.LOADING
        viewModelScope.launch {
            try {
                response = myAndroidApi.retrofitService.consultaComerciante(matricula, token)
//                _faturamento.value = formatDinheiro(response.faturamento)
                _faturamento.value = formatDinheiro(response.faturamento)
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                Log.i("Comerciante",e.toString())
                _status.value = ApiStatus.ERROR
                _mensagemAPI.value = "Problemas no servidor, tente novamente"
            }
        }
    }
}

// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class ComercianteViewModelFactory(private val matricula: String, private val nome: String, private val token: String) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ComercianteViewModel::class.java)) {
            return ComercianteViewModel(matricula, nome, token) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
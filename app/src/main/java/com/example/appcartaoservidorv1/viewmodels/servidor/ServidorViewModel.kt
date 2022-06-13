package com.example.appcartaoservidorv1.viewmodels.servidor

import androidx.lifecycle.*
import com.example.appcartaoservidorv1.models.DTO_Servidor
import com.example.appcartaoservidorv1.services.myAndroidApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class ServidorViewModel(val matricula: String, val nome: String) : ViewModel() {
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
    lateinit var response: DTO_Servidor

    // Variavel com a descrição do saldo
    private val _descricaoSaldo = MutableLiveData<String>()
    val descricaoSaldo: LiveData<String>
        get() = _descricaoSaldo
    // Saldo
    private val _saldo = MutableLiveData<String>()
    val saldo: LiveData<String>
        get() = _saldo

    // Instancia da data atual
    private val data = Calendar.getInstance().time

    init {
        _descricaoSaldo.value = "Saldo Disponivel em ${formatData(data)}"
        ConsultaServidor(matricula)
    }

    // Consulta a api
    fun ConsultaServidor(matricula: String) {
        _status.value = ApiStatus.LOADING
        viewModelScope.launch {
            try {
                response = myAndroidApi.retrofitService.consultaServidor(matricula)
                _saldo.value = formatDinheiro(response.saldo)
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _mensagemAPI.value = "Problemas no servidor, tente novamente"
            }
        }
    }
    // Função que coloca o saldo no formato adequado e transforma em string
    fun formatDinheiro(entrada: Double): String {
        return DecimalFormat("#,##0.00").format(entrada)
    }
    // Função que formata a data e retorna o mês em extenso
    fun formatData(data: Date): String {
        val formatter = SimpleDateFormat("MMMM",Locale.getDefault())
        return formatter.format(data)
    }
}

// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class ServidorViewModelFactory(private val matricula: String, private val nome: String) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ServidorViewModel::class.java)) {
            return ServidorViewModel(matricula, nome) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
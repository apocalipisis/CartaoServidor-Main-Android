package com.example.appcartaoservidorv1.viewmodels.comerciante

import androidx.lifecycle.*
import com.example.appcartaoservidorv1.models.DTO_Comerciante
import com.example.appcartaoservidorv1.models.DTO_Servidor
import com.example.appcartaoservidorv1.services.myAndroidApi
import com.example.appcartaoservidorv1.viewmodels.servidor.ServidorViewModel
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class ComercianteViewModel(val matricula: String, val nome: String) : ViewModel() {

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
        _descricaoFaturamento.value = "Faturamento no mês de ${formatData(data)}"
//        ConsultaComerciante(matricula)
//        response.matricula = "00002"
    }

    // Consulta a api
    fun ConsultaComerciante(matricula: String) {
        _status.value = ApiStatus.LOADING
        viewModelScope.launch {
            try {
//                response = myAndroidApi.retrofitService.consultaComerciante(matricula)
//                _faturamento.value = formatDinheiro(response.saldo)
                response.matricula = "00002"
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
        val formatter = SimpleDateFormat("MMMM", Locale.getDefault())
        return formatter.format(data)
    }

}

// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class ComercianteViewModelFactory(private val matricula: String, private val nome: String) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ComercianteViewModel::class.java)) {
            return ComercianteViewModel(matricula, nome) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
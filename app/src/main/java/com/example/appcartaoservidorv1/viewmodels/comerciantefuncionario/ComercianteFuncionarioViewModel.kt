package com.example.appcartaoservidorv1.viewmodels.comerciantefuncionario

import android.util.Log
import androidx.lifecycle.*
import com.example.appcartaoservidorv1.Constantes
import com.example.appcartaoservidorv1.models.DTOComercianteFuncionario
import com.example.appcartaoservidorv1.services.api.APIComercianteFuncionario
import com.example.appcartaoservidorv1.services.utilidades.dataEmMes
import com.example.appcartaoservidorv1.services.utilidades.formatDinheiro
import kotlinx.coroutines.launch
import java.util.*

class ComercianteFuncionarioViewModel(val matricula: String, val nome: String, val token: String) :
    ViewModel() {

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
    lateinit var response: DTOComercianteFuncionario

    // Variavel com a descrição do faturamento
    private val _descricaoFaturamento = MutableLiveData<String>()
    val descricaoFaturamento: LiveData<String>
        get() = _descricaoFaturamento

    // Faturamento
    private val _totalVendas = MutableLiveData<String>()
    val totalVendas: LiveData<String>
        get() = _totalVendas

    // Instancia da data atual
    private val data = Calendar.getInstance().time

    init {
        _descricaoFaturamento.value = "Suas vendas no mês de ${dataEmMes(data)}"
        consultaComerciante(matricula)
    }

    // Consulta a api
    fun consultaComerciante(matricula: String) {
        _status.value = ApiStatus.LOADING
        viewModelScope.launch {
            try {
                response =
                    APIComercianteFuncionario.APIComercianteFuncionarioService.consultaComercianteFuncionario(
                        matricula,
                        token
                    )
                _totalVendas.value = formatDinheiro(response.totalVendas)
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                Log.i("Teste", e.toString())
                _status.value = ApiStatus.ERROR
                _mensagemAPI.value = Constantes.Erro4
            }
        }
    }

}


// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class ComercianteFuncionarioViewModelFactory(
    private val matricula: String,
    private val nome: String,
    private val token: String
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ComercianteFuncionarioViewModel::class.java)) {
            return ComercianteFuncionarioViewModel(matricula, nome, token) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
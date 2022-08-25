package com.example.appcartaoservidorv1.viewmodels.comerciante

import androidx.lifecycle.*
import com.example.appcartaoservidorv1.models.Funcionario
import com.example.appcartaoservidorv1.services.api.APIComerciante
import kotlinx.coroutines.launch

class FuncionariocomercianteViewModel (val matricula: String, val token: String) : ViewModel() {

    //  Contador do numero de consultas
    private var nConsulta: Int = 0

    // Verifica se uma solicitação foi enviada
    var loading = false

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
    private lateinit var response: List<Funcionario>

    // Controla a navegação para uma página de detalhes
    private val _navigateToFuncionarioDetail = MutableLiveData<Funcionario>()
    val navigateToFuncionarioDetail
        get() = _navigateToFuncionarioDetail

    // Transações
    private val _funcionarios = MutableLiveData<List<Funcionario>>()
    val funcionarios: LiveData<List<Funcionario>>
        get() = _funcionarios

    // Passa o valor da transação para frente (transação detalhes)
    fun onFuncionarioClicked(funcionario: Funcionario) {
        _navigateToFuncionarioDetail.value = funcionario
    }

    // Limpa o valor da transação
    fun onFuncionarioDetailNavigated() {
        _navigateToFuncionarioDetail.value = null
    }

    init {
        _funcionarios.value = listOf()
    }

    fun consultarFuncionarios() {
        _status.value = ApiStatus.LOADING
        viewModelScope.launch {
            try {
                response =
                    APIComerciante.APIComercianteService.nConsultarFuncionariosComerciante(matricula, nConsulta, token)
                _funcionarios.value = _funcionarios.value?.plus(response)
                if (response.isNotEmpty()) {
                    loading = false
                    nConsulta++
                }
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _mensagemAPI.value = "Problemas no servidor, tente novamente"
                loading = false
                _status.value = ApiStatus.ERROR
            }
        }
    }

    fun reloadList() {
        nConsulta = 0

        val list: List<Funcionario> = listOf()
        _funcionarios.value = list

        consultarFuncionarios()
    }
}

// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class FuncionariocomercianteViewModelModelFactory(
    private val matricula: String,
    private val token: String,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FuncionariocomercianteViewModel::class.java)) {
            return FuncionariocomercianteViewModel(matricula, token) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
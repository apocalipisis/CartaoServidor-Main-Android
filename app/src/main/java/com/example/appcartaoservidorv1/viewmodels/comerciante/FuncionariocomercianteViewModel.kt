package com.example.appcartaoservidorv1.viewmodels.comerciante

import androidx.lifecycle.*
import com.example.appcartaoservidorv1.Constantes
import com.example.appcartaoservidorv1.models.Funcionario
import com.example.appcartaoservidorv1.models.auxiliares.ParBoolString
import com.example.appcartaoservidorv1.services.api.APIComerciante
import kotlinx.coroutines.launch

class FuncionariocomercianteViewModel(val matricula: String, val token: String) : ViewModel() {
    //  Contador do numero de consultas
    private var nConsulta: Int = 0

    // Verifica se uma solicitação foi enviada
    var loading = false

    //----------------------------------------------------------------------------------------------
    // Resposta da API
    private lateinit var response: List<Funcionario>
    private lateinit var responseUpdate: ParBoolString

    //----------------------------------------------------------------------------------------------
    // Status da consulta a API
    enum class ApiStatus { LOADING, ERROR, DONE }
    enum class ApiStatusLista { LOADING, ERROR, DONE }
    enum class ApiStatusUpdateRequest { LOADING, ERROR, DONE }

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    private val _statusLista = MutableLiveData<ApiStatusLista>()
    val statusLista: LiveData<ApiStatusLista>
        get() = _statusLista


    private val _statusUpdateRequest = MutableLiveData<ApiStatusUpdateRequest>()
    val statusUpdateRequest: LiveData<ApiStatusUpdateRequest>
        get() = _statusUpdateRequest

    //----------------------------------------------------------------------------------------------
    // Mensagem sobre consulta a API
    private val _mensagemAPI = MutableLiveData<String>()
    val mensagemAPI: LiveData<String>
        get() = _mensagemAPI

    //----------------------------------------------------------------------------------------------
    // Funcionarios
    private val _funcionarios = MutableLiveData<List<Funcionario>>()
    val funcionarios: LiveData<List<Funcionario>>
        get() = _funcionarios

    //----------------------------------------------------------------------------------------------
    var notify: Boolean = false
    var item: Int = 0

    //----------------------------------------------------------------------------------------------
    // Passa o gerente a ser atualizado
    fun onStatusChangeClicked(funcionario: Funcionario) {
        updateRequest(funcionario)
    }

    //----------------------------------------------------------------------------------------------
    init {
        _funcionarios.value = mutableListOf()
    }
    //----------------------------------------------------------------------------------------------

    fun consultarFuncionarios(isLista: Boolean) {
        if (isLista) {
            _statusLista.value = ApiStatusLista.LOADING
        } else {
            _status.value = ApiStatus.LOADING
        }
        viewModelScope.launch {
            try {
                response =
                    APIComerciante.APIComercianteService.nConsultarFuncionariosComerciante(
                        matricula,
                        nConsulta,
                        token
                    )
                _funcionarios.value = _funcionarios.value?.plus(response)
                if (response.isNotEmpty()) {
                    loading = false
                    nConsulta++
                }
                if (isLista) {
                    _statusLista.value = ApiStatusLista.DONE
                } else {
                    _status.value = ApiStatus.DONE
                }
            } catch (e: Exception) {
                _mensagemAPI.value = Constantes.Erro4
                loading = false
                if (isLista) {
                    _statusLista.value = ApiStatusLista.ERROR
                } else {
                    _status.value = ApiStatus.ERROR
                }
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    private fun updateItem(funcionario: Funcionario) {
        item = _funcionarios.value?.withIndex()
            ?.first { funcionario.Id == it.value.Id }!!
            .index
    }

    //----------------------------------------------------------------------------------------------
    private fun updateRequest(funcionario: Funcionario) {
        notify = true
        updateItem(funcionario)

        var isAtivo = true
        if (funcionario.Status == "Ativo")
            isAtivo = false

        _statusUpdateRequest.value = ApiStatusUpdateRequest.LOADING
        viewModelScope.launch {
            try {
                responseUpdate =
                    APIComerciante.APIComercianteService.editarFuncionarioComerciante(
                        funcionario.Matricula,
                        isAtivo,
                        token
                    )
                updateRecyclerView(responseUpdate)
                _statusUpdateRequest.value = ApiStatusUpdateRequest.DONE
            } catch (e: Exception) {
                _statusUpdateRequest.value = ApiStatusUpdateRequest.ERROR
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    private fun updateRecyclerView(response: ParBoolString) {
        if (response.b)
            _funcionarios.value?.get(item)?.Status = response.s

        _funcionarios.value = _funcionarios.value
    }


    //----------------------------------------------------------------------------------------------

    fun reloadList(isLista: Boolean) {
        nConsulta = 0

        val list: MutableList<Funcionario> = mutableListOf()
        _funcionarios.value = list

        consultarFuncionarios(isLista)
    }
    //----------------------------------------------------------------------------------------------

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
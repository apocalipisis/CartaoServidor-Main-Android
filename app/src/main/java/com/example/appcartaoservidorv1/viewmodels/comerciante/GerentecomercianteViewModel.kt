package com.example.appcartaoservidorv1.viewmodels.comerciante

import androidx.lifecycle.*
import com.example.appcartaoservidorv1.models.Gerente
import com.example.appcartaoservidorv1.services.api.APIComerciante
import com.example.appcartaoservidorv1.viewmodels.servidor.ServidorViewModel
import kotlinx.coroutines.launch

class GerentecomercianteViewModel(val matricula: String, val token: String) : ViewModel() {
    //  Contador do numero de consultas
    private var nConsulta: Int = 0

    // Verifica se uma solicitação foi enviada
    var loading = false

    // Status da consulta a API
    enum class ApiStatus { LOADING, ERROR, DONE }

    private val _status = MutableLiveData<ServidorViewModel.ApiStatus>()
    val status: LiveData<ServidorViewModel.ApiStatus>
        get() = _status

    // Mensagem sobre consulta a API
    private val _mensagemAPI = MutableLiveData<String>()
    val mensagemAPI: LiveData<String>
        get() = _mensagemAPI

    // Resposta da API
    private lateinit var response: List<Gerente>

    // Controla a navegação para uma página de detalhes
    private val _navigateToGerenteDetail = MutableLiveData<Gerente>()
    val navigateToGerenteDetail
        get() = _navigateToGerenteDetail

    // Transações
    private val _gerentes = MutableLiveData<List<Gerente>>()
    val gerentes: LiveData<List<Gerente>>
        get() = _gerentes

    // Passa o valor da transação para frente (transação detalhes)
    fun onGerenteClicked(gerente: Gerente) {
        _navigateToGerenteDetail.value = gerente
    }

    // Limpa o valor da transação
    fun onGerenteDetailNavigated() {
        _navigateToGerenteDetail.value = null
    }

    init {
        _gerentes.value = listOf()
//        reloadList()
    }

    fun consultarGerentes() {
        _status.value = ServidorViewModel.ApiStatus.LOADING
        viewModelScope.launch {
            try {
                response =
                    APIComerciante.APIComercianteService.nConsultarGerentesComerciante(matricula, nConsulta, token)
                _gerentes.value = _gerentes.value?.plus(response)
                if (response.isNotEmpty()) {
                    loading = false
                    nConsulta++
                }
                _status.value = ServidorViewModel.ApiStatus.DONE
            } catch (e: Exception) {
                _mensagemAPI.value = "Problemas no servidor, tente novamente"
                loading = false
                _status.value = ServidorViewModel.ApiStatus.ERROR
            }
        }
    }

    fun reloadList() {
        nConsulta = 0

        val list: List<Gerente> = listOf()
        _gerentes.value = list

        consultarGerentes()
    }

}

// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class GerentecomercianteViewModelFactory(
    private val matricula: String,
    private val token: String,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GerentecomercianteViewModel::class.java)) {
            return GerentecomercianteViewModel(matricula, token) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
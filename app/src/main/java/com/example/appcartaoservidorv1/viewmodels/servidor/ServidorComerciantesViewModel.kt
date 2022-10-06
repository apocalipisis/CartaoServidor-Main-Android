package com.example.appcartaoservidorv1.viewmodels.servidor

import androidx.lifecycle.*
import com.example.appcartaoservidorv1.Constantes
import com.example.appcartaoservidorv1.models.ComercianteModel
import com.example.appcartaoservidorv1.services.api.APIServidor
import kotlinx.coroutines.launch

class ServidorComerciantesViewModel(val token: String) :
    ViewModel() {
    //  Contador do numero de consultas
    private var nConsulta: Int = 0

    // Verifica se uma solicitação foi enviada
    var loading = false

    // Status da consulta a API
    enum class ApiStatus { LOADING, ERROR, DONE }
    enum class ApiStatusLista { LOADING, ERROR, DONE }

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    private val _statusLista = MutableLiveData<ApiStatusLista>()
    val statusLista: LiveData<ApiStatusLista>
        get() = _statusLista

    // Mensagem sobre consulta a API
    private val _mensagemAPI = MutableLiveData<String>()
    val mensagemAPI: LiveData<String>
        get() = _mensagemAPI

    // Resposta da API
    private lateinit var response: List<ComercianteModel>

    // Controla a navegação para uma página de detalhes
    private val _navigateToComercianteDetail = MutableLiveData<ComercianteModel>()
    val navigateToComercianteDetail
        get() = _navigateToComercianteDetail

    private val _comerciantes = MutableLiveData<List<ComercianteModel>>()
    val comerciantes: LiveData<List<ComercianteModel>>
        get() = _comerciantes

    fun onComercianteClicked(comerciante: ComercianteModel) {
        _navigateToComercianteDetail.value = comerciante
    }

    fun onComercianteDetailNavigated() {
        _navigateToComercianteDetail.value = null
    }

    init {
        _comerciantes.value = listOf()
    }

    fun consulta(isLista: Boolean) {
        if (isLista) {
            _statusLista.value = ApiStatusLista.LOADING
        } else {
            _status.value = ApiStatus.LOADING
        }
        _status.value = ApiStatus.LOADING
        viewModelScope.launch {
            try {
                response =
                    APIServidor.APIServidorService.listaComerciantes(token)
                _comerciantes.value =
                    _comerciantes.value?.plus(response.sortedBy { it.Nome })
                if (response.isNotEmpty()) {
                    nConsulta++
                    loading = false
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

    fun reloadList(isLista: Boolean) {
        nConsulta = 0

        val list: List<ComercianteModel> = listOf()
        _comerciantes.value = list

        consulta(isLista)
    }

}

// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class ServidorComerciantesViewModelFactory(
    private val token: String,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ServidorComerciantesViewModel::class.java)) {
            return ServidorComerciantesViewModel(token) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
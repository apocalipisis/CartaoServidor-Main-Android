package com.example.appcartaoservidorv1.viewmodels.comerciantefuncionario

import androidx.lifecycle.*
import com.example.appcartaoservidorv1.Constantes
import com.example.appcartaoservidorv1.models.Transacao
import com.example.appcartaoservidorv1.services.api.APIComercianteFuncionario
import com.example.appcartaoservidorv1.services.api.APIComercianteGerente
import com.example.appcartaoservidorv1.viewmodels.comerciante.HistoricovendasViewModel
import com.example.appcartaoservidorv1.viewmodels.comerciantegerente.ComerciantegerenteHistoricovendasViewModel
import kotlinx.coroutines.launch

class ComercianteFuncionarioHistoricovendasViewModel(val matricula: String, val token: String) :
    ViewModel() {
    //  Contador do numero de consultas
    var nConsulta: Int = 0

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
        _transacoes.value = listOf()
    }

    fun historicoDeVendas(isLista: Boolean) {
        if (isLista) {
            _statusLista.value = ApiStatusLista.LOADING
        } else {
            _status.value = ApiStatus.LOADING
        }
        viewModelScope.launch {
            try {
                response =
                    APIComercianteFuncionario.APIComercianteFuncionarioService.nTransacoesComercianteFuncionario(
                        matricula,
                        nConsulta,
                        token
                    )
                _transacoes.value =
                    _transacoes.value?.plus(response.sortedByDescending { it.DataVenda.time })
                if (response.isNotEmpty()) {
                    nConsulta++
                    loading = false
                }
                if (isLista){
                    _statusLista.value = ApiStatusLista.DONE
                }else{
                    _status.value = ApiStatus.DONE
                }
            } catch (e: Exception) {
                _mensagemAPI.value = Constantes.Erro4
                loading = false
                if (isLista){
                    _statusLista.value = ApiStatusLista.ERROR
                }else{
                    _status.value = ApiStatus.ERROR
                }
            }
        }
    }

    fun reloadList(isLista: Boolean) {
        nConsulta = 0

        val list: List<Transacao> = listOf()
        _transacoes.value = list

        historicoDeVendas(isLista)
    }
}


// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class ComercianteFuncionarioHistoricovendasViewModelFactory(
    private val matriculaMae: String,
    private val token: String,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ComercianteFuncionarioHistoricovendasViewModel::class.java)) {
            return ComercianteFuncionarioHistoricovendasViewModel(matriculaMae, token) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
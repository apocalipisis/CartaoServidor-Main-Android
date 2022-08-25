package com.example.appcartaoservidorv1.viewmodels.comerciantegerente

import androidx.lifecycle.*
import com.example.appcartaoservidorv1.models.DTO_InserirTransacao
import com.example.appcartaoservidorv1.services.api.APIComercianteGerente
import kotlinx.coroutines.launch

class ComerciantegerenteVendaStatusViewModel(
    val matriculaCliente: String,
    val matriculaComerciante: String,
    val matriculaVendedor: String,
    val valor: Float,
    val senha: String,
    val nomeVendedor: String,
    val token: String,
) : ViewModel() {

    // Status da consulta a API
    enum class ApiStatus { LOADING, ERROR, DONE }

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    // Mensagem sobre consulta a API
    private val _result = MutableLiveData<String>()
    val result: LiveData<String>
        get() = _result

    // Motivo (aparece em possiveis erros)
    private val _motivo = MutableLiveData<String>()
    val motivo: LiveData<String>
        get() = _motivo

    // Resposta da API
    lateinit var response: DTO_InserirTransacao

    // Avisa se a transação foi inserida ou não
    private val _sucess = MutableLiveData<Boolean>()
    val sucess: LiveData<Boolean>
        get() = _sucess

    init {
        inserirTransacao(
            matriculaCliente,
            matriculaComerciante,
            matriculaVendedor,
            valor,
            senha,
            token
        )
    }

    // Consulta a api
    private fun inserirTransacao(
        matriculaCliente: String,
        matriculaComerciante: String,
        matriculaVendedor: String,
        valor: Float,
        senha: String,
        token: String,
    ) {
        _status.value = ApiStatus.LOADING
        viewModelScope.launch {
            try {
                response = APIComercianteGerente.APIComercianteGerenteService.inserirVendaComercianteGerente(
                    matriculaCliente,
                    matriculaComerciante,
                    matriculaVendedor,
                    valor.toString(),
                    senha,
                    token,
                )
                if (response.vendaInserida) {
                    sucessResultMotivo(true, "Venda inserida", "")
                } else {
                    if (!response.bancoDeDadosOk) {
                        sucessResultMotivo(
                            false,
                            "Venda não inserida",
                            "Problemas no servidor, tente novamente"
                        )
                    } else if (!response.prefeituraAtivo) {
                        sucessResultMotivo(
                            false,
                            "Venda não inserida",
                            "O serviço prestado a essa prefeitura está inativo no momento"
                        )
                    } else if (!response.valorValido) {
                        sucessResultMotivo(false, "Venda não inserida", "Valor Inválido")
                    } else if (!response.servidorExiste) {
                        sucessResultMotivo(false, "Venda não inserida", "Servidor não Existe")
                    } else if (!response.senhaCorreta) {
                        sucessResultMotivo(false, "Venda não inserida", "Senha Incorreta")
                    } else if (!response.servidorAtivo) {
                        sucessResultMotivo(false, "Venda não inserida", "Servidor Inativo")
                    } else if (!response.temSaldo) {
                        sucessResultMotivo(false, "Venda não inserida", "Saldo Insuficiente")
                    }
                }
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _result.value = "Venda não inserida"
                sucessResultMotivo(
                    false,
                    "Venda não inserida",
                    "Problemas no servidor, tente novamente"
                )
                _status.value = ApiStatus.ERROR
            }
        }
    }

    private fun sucessResultMotivo(sucess: Boolean, result: String, motivo: String) {
        _sucess.value = sucess
        _result.value = result
        _motivo.value = motivo
    }

}

// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class ComerciantegerenteVendaStatusViewModelFactory(
    private val matriculaCliente: String,
    private val matriculaComerciante: String,
    private val matriculaVendedor: String,
    private val valor: Float,
    private val senha: String,
    private val nomeVendedor: String,
    private val token: String,
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ComerciantegerenteVendaStatusViewModel::class.java)) {
            return ComerciantegerenteVendaStatusViewModel(
                matriculaCliente,
                matriculaComerciante,
                matriculaVendedor,
                valor,
                senha,
                nomeVendedor,
                token,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
package com.example.appcartaoservidorv1.viewmodels.comerciante

import androidx.lifecycle.*
import com.example.appcartaoservidorv1.models.DTO_InserirTransacao
import com.example.appcartaoservidorv1.services.myAndroidApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StatusvendaViewModel(
    val valor: Float,
    val matricula: String,
    val matriculaComerciante: String,
    val senha: String,
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
        inserirTransacao(valor, matricula, matriculaComerciante, senha, token)
    }

    // Consulta a api
    private fun inserirTransacao(
        valor: Float,
        matricula: String,
        matriculaComerciante: String,
        senha: String,
        token: String,
    ) {
        _status.value = ApiStatus.LOADING
        viewModelScope.launch {
            try {
                response = myAndroidApi.retrofitService.InserirVenda(
                    matricula,
                    matriculaComerciante,
                    matriculaComerciante,
                    valor.toString(),
                    senha,
                    token,
                )
//                _faturamento.value = formatDinheiro(response.saldo)
//                delay(3000)
//                response = DTO_InserirTransacao(bancoDeDadosOk = true)
                if (response.vendaInserida) {
                    sucessResultMotivo(true, "Venda inserida", "")
                }
                else {
                    if (!response.bancoDeDadosOk){
                        sucessResultMotivo(false, "Venda não inserida", "Problemas no servidor, tente novamente")
                    } else if (!response.prefeituraAtivo){
                        sucessResultMotivo(false, "Venda não inserida", "O serviço prestado a essa prefeitura está inativo no momento")
                    } else if (!response.valorValido){
                        sucessResultMotivo(false, "Venda não inserida", "Valor Inválido")
                    } else if (!response.servidorExiste){
                        sucessResultMotivo(false, "Venda não inserida", "Servidor não Existe")
                    } else if (!response.senhaCorreta){
                        sucessResultMotivo(false, "Venda não inserida", "Senha Incorreta")
                    } else if (!response.servidorAtivo){
                        sucessResultMotivo(false, "Venda não inserida", "Servidor Inativo")
                    } else if (!response.temSaldo ){
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
class StatusvendaViewModelFactory(
    private val valor: Float,
    private val matricula: String,
    private val matriculaComerciante: String,
    private val senha: String,
    private val token: String,
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StatusvendaViewModel::class.java)) {
            return StatusvendaViewModel(valor, matricula, matriculaComerciante, senha, token) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
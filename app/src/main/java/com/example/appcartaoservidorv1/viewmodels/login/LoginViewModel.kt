package com.example.appcartaoservidorv1.viewmodels.login

import androidx.lifecycle.*
import com.example.appcartaoservidorv1.Constantes
import com.example.appcartaoservidorv1.models.dto.DTOLogin
import com.example.appcartaoservidorv1.services.APIAndroid
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    // Status da consulta a API
    enum class ApiStatus { LOADING, ERROR, DONE }

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    // Resposta da API
    private val _response = MutableLiveData<DTOLogin>()
    val response: LiveData<DTOLogin>
        get() = _response

    // Motivo (em caso de loginSucess = false)
    private val _mensagem = MutableLiveData<String>()
    val mensagem: LiveData<String>
        get() = _mensagem

    fun setMensagem(text: String) {
        _mensagem.value = text
    }

    // Observa e muda o estado da card
    var isCardStateErro = MutableLiveData<Boolean>()
    fun setCardStateErro(valor: Boolean) {
        isCardStateErro.value = valor
    }

    var matricula = MutableLiveData<String>()
    var senha = MutableLiveData<String>()

    init {
        isCardStateErro.value = false
    }

    fun request(matricula: String, senha: String) {
        _status.value = ApiStatus.LOADING
        viewModelScope.launch {
            try {
                _response.value = APIAndroid.APIAndroidService.verificaLogin(matricula, senha)
                setMensagem(response.value!!.s)
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                setMensagem(Constantes.Erro4)
                _status.value = ApiStatus.ERROR
            }
        }
    }
}

// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class LoginViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


package com.example.appcartaoservidorv1.viewmodels

import androidx.lifecycle.*
import com.example.appcartaoservidorv1.models.DTO_Login
import com.example.appcartaoservidorv1.services.myAndroidApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    // Status da consulta a API
    enum class ApiStatus { LOADING, ERROR, DONE }

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    // Matricula digitada pelo usuario
    var Matricula = MutableLiveData<String>()

    // Senha digitada pelo usuario
    var Senha = MutableLiveData<String>()

    // Autoriza o login
    var _login = MutableLiveData<DTO_Login>()
    val login: LiveData<DTO_Login>
        get() = _login
    // Informa a situação do login
    private val _mensagemLogin = MutableLiveData<String>()
    val mensagemLogin: LiveData<String>
        get() = _mensagemLogin

    init {
        Matricula.value = "00003"
//        Matricula.value = "00002"
    }

    fun VerificaLogin() {
        _status.value = ApiStatus.LOADING
        viewModelScope.launch {
            try {
//                _login.value = myAndroidApi.retrofitService.verificaLogin(
//                    Matricula.value.toString(),
//                    Senha.value.toString()
//                )
                _login.value = DTO_Login(true,true,  "Teste","Comerciante", true)
//                _login.value = DTO_Login(true,true,  "Teste","Servidor")
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _mensagemLogin.value = e.toString()
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


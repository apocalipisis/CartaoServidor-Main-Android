package com.example.appcartaoservidorv1.viewmodels.login

import androidx.lifecycle.*
import com.example.appcartaoservidorv1.Constantes
import com.example.appcartaoservidorv1.models.DTO_Login
import com.example.appcartaoservidorv1.services.APIAndroid
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    // Resposta da API
    private lateinit var response: DTO_Login

    // Status da consulta a API
    enum class ApiStatus { LOADING, ERROR, DONE }

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    // Status do Login
    private val _loginSucess = MutableLiveData<Boolean>()
    val loginSucess: LiveData<Boolean>
        get() = _loginSucess

    // Motivo (em caso de loginSucess = false)
    private val _motivoLoginFail = MutableLiveData<String>()
    val motivoLoginFail: LiveData<String>
        get() = _motivoLoginFail

    // Destino (em caso de loginSucess = True)
    private val _destino = MutableLiveData<String>()
    val destino: LiveData<String>
        get() = _destino

    // Matricula digitada pelo usuario
    var matricula = MutableLiveData<String>()

    // Senha digitada pelo usuario
    var senha = MutableLiveData<String>()

    //Nome do usuário
    var nome: String = ""

    //Token do usuário
    var token: String = ""

    fun getApiResponse(matricula: String, senha: String) {

        _status.value = ApiStatus.LOADING
        viewModelScope.launch {
            try {
                response = APIAndroid.APIAndroidService.verificaLogin(
                    matricula,
                    senha,
                )
                analisaResposta(response)
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _motivoLoginFail.value = Constantes.Erro1
                _status.value = ApiStatus.ERROR
            }
        }
    }

    private fun analisaResposta(response: DTO_Login) {
        if (!response.bancoDeDadosOk) {
            _loginSucess.value = false
            _motivoLoginFail.value = Constantes.Erro1
            return
        }

        if (response.bancoDeDadosOk && !response.loginAutorizado) {
            _loginSucess.value = false
            _motivoLoginFail.value = Constantes.Erro2
            return
        }

        _loginSucess.value = true
        redirectUser(response)
    }

    private fun redirectUser(response: DTO_Login) {
        nome = response.nomeUsuario
        token = response.token

        if (!response.usuarioAtivo) {
            _destino.value = "UsuarioInativo"
            return
        }

        when (response.tipoUsuario) {
            "Servidor" -> {
                _destino.value = "Servidor"
            }
            "Comerciante" -> {
                _destino.value = "Comerciante"
            }
            "ComercianteGerente" -> {
                _destino.value = "ComercianteGerente"
            }
            else -> {
                _destino.value = "NãoAutorizado"
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


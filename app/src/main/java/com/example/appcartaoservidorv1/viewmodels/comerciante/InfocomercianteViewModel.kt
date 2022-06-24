package com.example.appcartaoservidorv1.viewmodels.comerciante

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.models.Transacao
import com.example.appcartaoservidorv1.viewmodels.servidor.InfoservidorViewModel

class InfocomercianteViewModel (
    val matricula: String,
    val nome: String,
    val cnpj: String,
    val tipoUsuario: String,
    val status: String,
    val pagamentoUsoSistema: Float
) : ViewModel() {
    // pagamentoUsoSistema
    private val _pagamentoUso = MutableLiveData<String>()
    val pagamentoUso: LiveData<String>
        get() = _pagamentoUso

    init {
        _pagamentoUso.value = pagamentoUsoSistema.toString() + "%"
    }
}

// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class InfocomercianteViewModelFactory(
    private val matricula: String,
    private val nome: String,
    private val cnpj: String,
    private val tipoUsuario: String,
    private val status: String,
    private val pagamentoUsoSistema: Float
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InfocomercianteViewModel::class.java)) {
            return InfocomercianteViewModel(
                matricula,
                nome,
                cnpj,
                tipoUsuario,
                status,
                pagamentoUsoSistema,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}
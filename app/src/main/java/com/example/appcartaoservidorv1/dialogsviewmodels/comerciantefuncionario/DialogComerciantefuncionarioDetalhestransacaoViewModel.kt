package com.example.appcartaoservidorv1.dialogsviewmodels.comerciantefuncionario

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.models.Transacao
import com.example.appcartaoservidorv1.services.utilidades.dataCompletaMesExtenso
import com.example.appcartaoservidorv1.services.utilidades.formatDinheiro

class DialogComerciantefuncionarioDetalhestransacaoViewModel(val transacao: Transacao) :
    ViewModel() {
    // Variavel usada para foramtar o valor
    private val _valor = MutableLiveData<String>()
    val valor: LiveData<String>
        get() = _valor

    // Variavel usada para formatar a data em string
    private val _data = MutableLiveData<String>()
    val data: LiveData<String>
        get() = _data

    init {
        _valor.value = formatDinheiro(transacao.Valor) + " R$"
        _data.value = dataCompletaMesExtenso(transacao.DataVenda)
    }
}

// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class DialogComerciantefuncionarioDetalhestransacaoViewModelFactory(private val transacao: Transacao) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DialogComerciantefuncionarioDetalhestransacaoViewModel::class.java)) {
            return DialogComerciantefuncionarioDetalhestransacaoViewModel(transacao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
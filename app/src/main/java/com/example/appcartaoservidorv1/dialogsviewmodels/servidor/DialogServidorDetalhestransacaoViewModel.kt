package com.example.appcartaoservidorv1.dialogsviewmodels.servidor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.models.Transacao
import com.example.appcartaoservidorv1.services.utilidades.dataCompletaMesExtenso
import com.example.appcartaoservidorv1.services.utilidades.formatDinheiro

class DialogServidorDetalhestransacaoViewModel(val transacao: Transacao) : ViewModel() {
    // Variavel usada para foramtar o valor
    private val _valor = MutableLiveData<String>()
    val valor: LiveData<String>
        get() = _valor

    // Variavel usada para formatar a data em string
    private val _data = MutableLiveData<String>()
    val Data: LiveData<String>
        get() = _data

    init {
        _valor.value = formatDinheiro(transacao.Valor) + " R$"
        _data.value = dataCompletaMesExtenso(transacao.DataVenda)
    }
}

// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class DialogServidorDetalhestransacaoViewModelFactory(private val transacao: Transacao) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DialogServidorDetalhestransacaoViewModel::class.java)) {
            return DialogServidorDetalhestransacaoViewModel(transacao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
package com.example.appcartaoservidorv1.viewmodels.comerciante

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.models.Transacao
import com.example.appcartaoservidorv1.services.utilidades.dataCompletaMesExtenso
import com.example.appcartaoservidorv1.services.utilidades.formatDinheiro
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class TransacaodetalhescomercianteViewModel(val transacao: Transacao): ViewModel() {
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
class TransacaodetalhescomercianteViewModelFactory(private val transacao: Transacao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransacaodetalhescomercianteViewModel::class.java)) {
            return TransacaodetalhescomercianteViewModel(transacao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


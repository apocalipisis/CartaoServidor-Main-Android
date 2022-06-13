package com.example.appcartaoservidorv1.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.models.Transacao
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class TransacaodetalhesViewModel(val transacao: Transacao): ViewModel() {
    // Variavel usada para foramtar o valor
    private val _valor = MutableLiveData<String>()
    val valor: LiveData<String>
        get() = _valor
    // Variavel usada para formatar a data em string
    private val _data = MutableLiveData<String>()
    val Data: LiveData<String>
        get() = _data
    init {
        _valor.value = formatSaldo(transacao.Valor)
        _data.value = formatData(transacao.DataVenda)
    }
    // Função que coloca o saldo no formato adequado e transforma em string
    fun formatSaldo(saldo: Double): String {
        return DecimalFormat("#,##0.00").format(saldo)
    }
    // Função que formata a data e retorna o mês em extenso
    fun formatData(data: Date): String {
        val formatter = SimpleDateFormat("dd/MMMM/yy", Locale("pt", "BR"))
        return formatter.format(data)
    }

}

// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class TransacaodetalhesViewModelFactory(private val transacao: Transacao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransacaodetalhesViewModel::class.java)) {
            return TransacaodetalhesViewModel(transacao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


package com.example.appcartaoservidorv1.viewmodels.comerciantefuncionario

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.*

class ComercianteFuncionarioVendaSenhaViewModel(
    val matriculaCliente: String,
    val nomeCliente: String,
    val valor: Float,
    val matriculaComerciante: String,
    val nomeComerciante: String,
    val matriculaVendedor: String,
    val nomeVendedor: String,
    val numeroCartao: String,
    val token: String,
) :
    ViewModel() {
    // Mensagem com o valor da compra
    val valorStr: String = formataString(valor) + " R$"

    // Mensagem a ser exibida na tela
    private val _ConfirmStr = MutableLiveData<String>()
    val confirmStr: LiveData<String>
        get() = _ConfirmStr

    // observador se a senha está completa
    private val _senhaCompleta = MutableLiveData<String>()
    val senhaCompleta: LiveData<String>
        get() = _senhaCompleta

    //Valores da senha
    var senha1: String = ""
    var senha2: String = ""
    var senha3: String = ""
    var senha4: String = ""

    init {
        _ConfirmStr.value = "Compra em ${nomeComerciante}, valor:"
    }

    fun senhaCompleta() {
        _senhaCompleta.value = senha1 + senha2 + senha3 + senha4
    }

    // Função que formata o numero adicionando pontos(a cada mil) e virgula nas casas decimais
    private fun formataString(numb: Number): String {
        val formatter = NumberFormat.getCurrencyInstance(Locale.getDefault()) as DecimalFormat
        val symbols: DecimalFormatSymbols = formatter.decimalFormatSymbols
        symbols.currencySymbol = ""
        formatter.decimalFormatSymbols = symbols
        return formatter.format(numb)
    }
}

// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class ComercianteFuncionarioVendaSenhaViewModelFactory(
    private val matriculaCliente: String,
    private val nomeCliente: String,
    private val valor: Float,
    private val matriculaComerciante: String,
    private val nomeComerciante: String,
    private val matriculaVendedor: String,
    private val nomeVendedor: String,
    private val numeroCartao: String,
    private val token: String,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ComercianteFuncionarioVendaSenhaViewModel::class.java)) {
            return ComercianteFuncionarioVendaSenhaViewModel(
                matriculaCliente,
                nomeCliente,
                valor,
                matriculaComerciante,
                nomeComerciante,
                matriculaVendedor,
                nomeVendedor,
                numeroCartao,
                token,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
package com.example.appcartaoservidorv1.viewmodels.comerciante

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.*

class InserirsenhaViewModel(
    val matricula: String,
    val servidor: String,
    val valor: Float,
    val nomeComerciante: String
) :
    ViewModel() {
    // Mensagem com o valor da compra
    val ValorStr: String = formataString(valor) + " R$"

    // Mensagem a ser exibida na tela
    private val _ConfirmStr = MutableLiveData<String>()
    val ConfirmStr: LiveData<String>
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
        _ConfirmStr.value = "Confirme sua compra em ${nomeComerciante} no valor de:"
    }

    fun SenhaCompleta(){
        _senhaCompleta.value = senha1+senha2+senha3+senha4
    }



    // Função que formata o numero adicionando pontos(a cada mil) e virgula nas casas decimais
    private fun formataString(numb: Number): String {
        val formatter = NumberFormat.getCurrencyInstance(Locale.getDefault()) as DecimalFormat
        val symbols: DecimalFormatSymbols = formatter.decimalFormatSymbols
        symbols.setCurrencySymbol("") // Don't use null.
        formatter.decimalFormatSymbols = symbols
        return formatter.format(numb)
    }

}

// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class InserirsenhaViewModelFactory(
    private val matricula: String,
    private val servidor: String,
    private val valor: Float,
    private val nomeComerciante: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InserirsenhaViewModel::class.java)) {
            return InserirsenhaViewModel(matricula, servidor, valor, nomeComerciante) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
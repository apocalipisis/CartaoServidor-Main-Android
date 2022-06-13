package com.example.appcartaoservidorv1.viewmodels.servidor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.text.DecimalFormat

class InfoservidorViewModel(
    val matricula: String,
    val nome: String,
    val cpf: String,
    val tipoUsuario: String,
    val status: String,
    val instituto: String,
    val limiteMensal: Double
) : ViewModel() {

    // Limite Mensal
    private val _limiteMes = MutableLiveData<String>()
    val limiteMes: LiveData<String>
        get() = _limiteMes

    init {
        _limiteMes.value = formatDinheiro(limiteMensal)  + " R$"
    }

    // Função que coloca o saldo no formato adequado e transforma em string
    fun formatDinheiro(entrada: Double): String {
        return DecimalFormat("#,##0.00").format(entrada)
    }
}

// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class InfoservidorViewModelFactory(
    private val matricula: String,
    private val nome: String,
    private val cpf: String,
    private val tipoUsuario: String,
    private val status: String,
    private val instituto: String,
    private val limiteMensal: Double
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InfoservidorViewModel::class.java)) {
            return InfoservidorViewModel(
                matricula,
                nome,
                cpf,
                tipoUsuario,
                status,
                instituto,
                limiteMensal
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}
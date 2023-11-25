package com.example.appcartaoservidorv1.viewmodels.servidor

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.Constantes
import com.example.appcartaoservidorv1.models.auxiliares.TStrings
import com.example.appcartaoservidorv1.services.utilidades.formataCartaoCredito
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@OptIn(ExperimentalSerializationApi::class)
class CompraservidorViewModel(
    val matricula: String,
    val nome: String,
    val statusCartao: String,
    var numeroCartao: String,
    var token: String,
) :
    ViewModel() {
    enum class Status { ERROR, DONE }

    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status>
        get() = _status

    // QrCode
    private val _qrC = MutableLiveData<Bitmap>()
    val qrC: LiveData<Bitmap>
        get() = _qrC

    // Mensagem de erro
    private val _mensagem = MutableLiveData<String>()
    val mensagem: LiveData<String>
        get() = _mensagem

    var cartaoBloq = "Bloqueado"
    private fun isCartaoAtivo() {
        if (statusCartao == "Ativo")
            cartaoBloq = "Desbloqueado"
    }

    init {
        display()
        isCartaoAtivo()
        numeroCartao = formataCartaoCredito(numeroCartao)
    }

    private fun display() {
        val jsonQRcode = Json.encodeToString(TStrings(nome, matricula, numeroCartao))
        _qrC.value = generateQR(jsonQRcode, 512)
    }

    // Função que gera o Qr Code
    private fun generateQR(content: String?, size: Int): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val barcodeEncoder = BarcodeEncoder()
            bitmap = barcodeEncoder.encodeBitmap(
                content,
                BarcodeFormat.QR_CODE, size, size
            )
            _status.value = Status.DONE
        } catch (e: WriterException) {
            _mensagem.value = Constantes.Erro7
            _status.value = Status.ERROR
        }
        return bitmap
    }
}

// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class CompraservidorViewModelFactory(
    private val matricula: String,
    private val nome: String,
    private val statusCartao: String,
    private val numeroCartao: String,
    private val token: String,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CompraservidorViewModel::class.java)) {
            return CompraservidorViewModel(
                matricula,
                nome,
                statusCartao,
                numeroCartao,
                token
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
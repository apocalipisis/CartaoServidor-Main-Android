package com.example.appcartaoservidorv1.viewmodels.servidor

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.models.auxiliares.ParStrings
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@OptIn(ExperimentalSerializationApi::class)
class CompraservidorViewModel(val matricula: String, val nome: String) :
    ViewModel() {
    // QrCode
    private val _qrC = MutableLiveData<Bitmap>()
    val qrC: LiveData<Bitmap>
        get() = _qrC
    // Mensagem de erro
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    init {
        val jsonQRcode = Json.encodeToString(ParStrings(nome, matricula))
        _qrC.value = generateQR(jsonQRcode, 512)
    }
    // Função que gera o Qr Code
    fun generateQR(content: String?, size: Int): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val barcodeEncoder = BarcodeEncoder()
            bitmap = barcodeEncoder.encodeBitmap(
                content,
                BarcodeFormat.QR_CODE, size, size
            )
        } catch (e: WriterException) {
            _errorMessage.value = "Não foi possivel gerar o QrCode tente novamente"
        }
        return bitmap
    }
}

// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class CompraservidorViewModelFactory(
    private val matricula: String,
    private val nome: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CompraservidorViewModel::class.java)) {
            return CompraservidorViewModel(matricula, nome) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
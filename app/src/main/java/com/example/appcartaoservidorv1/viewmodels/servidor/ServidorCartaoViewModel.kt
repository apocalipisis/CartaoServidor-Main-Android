package com.example.appcartaoservidorv1.viewmodels.servidor

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.*
import com.example.appcartaoservidorv1.Constantes
import com.example.appcartaoservidorv1.models.auxiliares.TStrings
import com.example.appcartaoservidorv1.models.cartao.DTOCartaoServidor
import com.example.appcartaoservidorv1.services.api.APIServidor
import com.example.appcartaoservidorv1.services.utilidades.formataCartaoCredito
import com.example.appcartaoservidorv1.services.utilidades.formataStatusToBloqueado
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ServidorCartaoViewModel(
    val matricula: String,
    val token: String,
) : ViewModel() {
    //----------------------------------------------------------------------------------------------
    // Status criação do QRCode
    enum class StatusQRCode {  ERROR, DONE }

    private val _statusQRCode = MutableLiveData<StatusQRCode>()
    val statusQRCode: LiveData<StatusQRCode>
        get() = _statusQRCode
    //----------------------------------------------------------------------------------------------
    // Status da consulta a API Cartao Servidor
    enum class StatusRequest { LOADING, ERROR, DONE }

    private val _statusRequest = MutableLiveData<StatusRequest>()
    val statusRequest: LiveData<StatusRequest>
        get() = _statusRequest

    //----------------------------------------------------------------------------------------------
    // Status da consulta a API Cartao Servidor
    enum class ApiStatusBloquearCartao { LOADING, ERROR, DONE }

    private val _statusBloquearCartao = MutableLiveData<ApiStatusBloquearCartao>()
    val statusBloquearCartao: LiveData<ApiStatusBloquearCartao>
        get() = _statusBloquearCartao

    //----------------------------------------------------------------------------------------------
    // Status da consulta a API Cartao Servidor
    enum class ApiStatusDesbloquearCartao { LOADING, ERROR, DONE }

    private val _statusDesbloquearCartao = MutableLiveData<ApiStatusDesbloquearCartao>()
    val statusDesbloquearCartao: LiveData<ApiStatusDesbloquearCartao>
        get() = _statusDesbloquearCartao

    //----------------------------------------------------------------------------------------------
    private val _numeroCartao = MutableLiveData<String>()
    val numeroCartao: LiveData<String>
        get() = _numeroCartao

    private val _statusCartao = MutableLiveData<String>()
    val statusCartao: LiveData<String>
        get() = _statusCartao

    private val _haPendenciasCartao = MutableLiveData<Boolean>()
    val haPendenciasCartao: LiveData<Boolean>
        get() = _haPendenciasCartao

    //----------------------------------------------------------------------------------------------
    // Resposta da API Cartao Servidor
    private lateinit var response: DTOCartaoServidor

    // Resposta da API BloquearCartao
    var responseBloquearCartao: Boolean = false

    // Resposta da API DesbloquearCartao
    var responseDesbloquearCartao: Boolean = false

    //----------------------------------------------------------------------------------------------
    // Variavel que armazena os dados do cartão
    private val _cartao = MutableLiveData<DTOCartaoServidor>()
    val cartao: LiveData<DTOCartaoServidor>
        get() = _cartao

    fun setStatusCartao(str:String){
        _cartao.value?.status = str
        _cartao.value = _cartao.value
        _statusCartao.value = formataStatusToBloqueado(str)
    }
    //----------------------------------------------------------------------------------------------
    // QrCode
    private val _qrC = MutableLiveData<Bitmap>()
    val qrC: LiveData<Bitmap>
        get() = _qrC

    //----------------------------------------------------------------------------------------------
    // Mensagens
    // Mensagem de erro
    private val _mensagem = MutableLiveData<String>()
    val mensagem: LiveData<String>
        get() = _mensagem

    // Mensagem de consulta a API
    private val _mensagemAPI = MutableLiveData<String>()
    val mensagemAPI: LiveData<String>
        get() = _mensagemAPI

    //----------------------------------------------------------------------------------------------
    init {
        _numeroCartao.value = ""
        _statusCartao.value = ""
        _haPendenciasCartao.value = false
        _mensagem.value = ""
    }
    //----------------------------------------------------------------------------------------------
    private fun display(nome:String, numero:String) {
        val jsonQRcode = Json.encodeToString(TStrings(nome, matricula, numero))
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
            _statusQRCode.value = StatusQRCode.DONE
        } catch (e: WriterException) {
            _mensagem.value = Constantes.Erro7
            _statusQRCode.value = StatusQRCode.ERROR
        }
        return bitmap
    }
    //----------------------------------------------------------------------------------------------
    fun consultarCartao() {
        _statusRequest.value = StatusRequest.LOADING
        viewModelScope.launch {
            try {
                response = APIServidor.APIServidorService.consultaCartao(matricula, token)

                _numeroCartao.value = formataCartaoCredito(response.numero)
                _statusCartao.value = formataStatusToBloqueado(response.status)

                _haPendenciasCartao.value = response.haPendenciasCartao

                _cartao.value = response
                display(response.nome, response.numero)

                _statusRequest.value = StatusRequest.DONE
            } catch (e: Exception) {
                _mensagem.value = Constantes.Erro4
                _statusRequest.value = StatusRequest.ERROR
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    fun bloquearCartao() {
        _statusBloquearCartao.value = ApiStatusBloquearCartao.LOADING
        viewModelScope.launch {
            try {
                responseBloquearCartao =
                    APIServidor.APIServidorService.bloquearCartao(matricula, token)
                Log.i("Teste responseBloquearCartao", responseBloquearCartao.toString())
                _statusBloquearCartao.value = ApiStatusBloquearCartao.DONE
            } catch (e: Exception) {
                _mensagem.value = Constantes.Erro1
                _statusBloquearCartao.value = ApiStatusBloquearCartao.ERROR
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    fun desbloquearCartao() {
        _statusDesbloquearCartao.value = ApiStatusDesbloquearCartao.LOADING
        viewModelScope.launch {
            try {
                responseDesbloquearCartao =
                    APIServidor.APIServidorService.desbloquearCartao(matricula, token)
                Log.i("Teste responseDesbloquearCartao", responseDesbloquearCartao.toString())
                _statusDesbloquearCartao.value = ApiStatusDesbloquearCartao.DONE
            } catch (e: Exception) {
                _mensagem.value = Constantes.Erro1
                _statusDesbloquearCartao.value = ApiStatusDesbloquearCartao.ERROR
            }
        }
    }
    //----------------------------------------------------------------------------------------------
}

// Configura a factory do ViewModel (Usada para receber os parametros passados para o viewmodel)
class ServidorCartaoViewModelFactory(
    private val matricula: String,
    private val token: String,
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ServidorCartaoViewModel::class.java)) {
            return ServidorCartaoViewModel(matricula, token) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

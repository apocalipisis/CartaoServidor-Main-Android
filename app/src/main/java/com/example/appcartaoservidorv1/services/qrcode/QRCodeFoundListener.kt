package com.example.appcartaoservidorv1.services.qrcode

public interface QRCodeFoundListener {
    fun onQRCodeFound(qrCode: String)
    fun qrCodeNotFound()
}
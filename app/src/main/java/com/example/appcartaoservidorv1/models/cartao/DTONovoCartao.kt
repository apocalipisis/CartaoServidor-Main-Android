package com.example.appcartaoservidorv1.models.cartao

import com.squareup.moshi.Json

data class DTONovoCartao(
    @Json(name = "novoCartaoSolicitado") var novoCartaoSolicitado: Boolean,
    @Json(name = "qrCode") var qrCode: String,
)


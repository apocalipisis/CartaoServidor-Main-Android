package com.example.appcartaoservidorv1.models.cartao

import com.squareup.moshi.Json

data class DTOCartaoServidor(
    @Json(name = "nome") var nome: String,
    @Json(name = "numero") var numero: String,
    @Json(name = "status") var status: String,
    @Json(name = "via") var via: String,
    @Json(name = "atual") var atual: Boolean,
    @Json(name = "haPendenciasCartao") var haPendenciasCartao: Boolean,
)

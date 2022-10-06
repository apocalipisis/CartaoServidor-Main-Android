package com.example.appcartaoservidorv1.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class DTO_Servidor(
    @Json(name = "bancoDeDadosOk") var bancoDeDadosOk: Boolean,
    @Json(name = "saldo") var saldo: Double,
    @Json(name = "nome") var nome: String,
    @Json(name = "cpf") var cpf: String,
    @Json(name = "cnpj") var cnpj: String,
    @Json(name = "matricula") var matricula: String,
    @Json(name = "tipoUsuario") var tipoUsuario: String,
    @Json(name = "status") var status: String,
    @Json(name = "instituto") var instituto: String,
    @Json(name = "limiteMensal") var limiteMensal: Double,
    @Json(name = "statusCartao") var statusCartao: String,
    @Json(name = "numeroCartao") var numeroCartao: String,
    @Json(name = "haPendenciasCartao") var haPendenciasCartao: Boolean,
)

package com.example.appcartaoservidorv1.models

import com.squareup.moshi.Json

data class DTO_Comerciante(
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
    @Json(name = "transacaoList") var transacaoList: List<Transacao>,
)

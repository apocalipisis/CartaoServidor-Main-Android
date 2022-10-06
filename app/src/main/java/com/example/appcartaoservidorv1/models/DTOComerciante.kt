package com.example.appcartaoservidorv1.models

import com.squareup.moshi.Json

data class DTOComerciante(
    @Json(name = "bancoDeDadosOk") var bancoDeDadosOk: Boolean,
    @Json(name = "faturamento") var faturamento: Double,
    @Json(name = "nome") var nome: String,
    @Json(name = "cpf") var cpf: String,
    @Json(name = "cnpj") var cnpj: String,
    @Json(name = "matricula") var matricula: String,
    @Json(name = "tipoUsuario") var tipoUsuario: String,
    @Json(name = "status") var status: String,
    @Json(name = "pagamentoUsoDoSistema") var pagementoUsoDoSistema: Float,
)

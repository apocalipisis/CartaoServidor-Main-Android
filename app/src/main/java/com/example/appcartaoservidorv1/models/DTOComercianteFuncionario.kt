package com.example.appcartaoservidorv1.models

import com.squareup.moshi.Json

data class DTOComercianteFuncionario(
    @Json(name = "bancoDeDadosOk") var bancoDeDadosOk: Boolean,
    @Json(name = "nome") var nome: String,
    @Json(name = "cnpj") var cnpj: String,
    @Json(name = "matricula") var matricula: String,
    @Json(name = "matriculaComerciante") var matriculaComerciante: String,
    @Json(name = "nomeComerciante") var nomeComerciante: String,
    @Json(name = "tipoUsuario") var tipoUsuario: String,
    @Json(name = "totalVendas") var totalVendas: Double,
    @Json(name = "status") var status: String,
)


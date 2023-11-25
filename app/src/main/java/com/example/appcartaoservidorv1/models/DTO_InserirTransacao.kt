package com.example.appcartaoservidorv1.models

import com.squareup.moshi.Json

data class DTO_InserirTransacao (
    @Json(name = "prefeituraAtivo") var prefeituraAtivo: Boolean,
    @Json(name = "bancoDeDadosOk") var bancoDeDadosOk: Boolean,
    @Json(name = "valorValido") var valorValido: Boolean,
    @Json(name = "servidorExiste") var servidorExiste: Boolean,
    @Json(name = "senhaCorreta") var senhaCorreta: Boolean,
    @Json(name = "servidorAtivo") var servidorAtivo: Boolean,
    @Json(name = "temSaldo") var temSaldo: Boolean,
    @Json(name = "vendaInserida") var vendaInserida: Boolean,
)
package com.example.appcartaoservidorv1.models.dto

import com.squareup.moshi.Json

data class DTOLogin(
    @Json(name = "b") var b: Boolean,
    @Json(name = "s") var s: String,
    @Json(name = "tipoUsuario") var tipoUsuario: String,
    @Json(name = "nomeUsuario") var nomeUsuario: String,
    @Json(name = "usuarioAtivo") var usuarioAtivo: Boolean,
    @Json(name = "token") var token: String,
)

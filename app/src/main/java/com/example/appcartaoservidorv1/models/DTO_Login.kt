package com.example.appcartaoservidorv1.models

import com.squareup.moshi.Json

data class DTO_Login(
    @Json(name = "loginAutorizado") var loginAutorizado: Boolean,
    @Json(name = "bancoDeDadosOk") var bancoDeDadosOk: Boolean,
    @Json(name = "nomeUsuario") var nomeUsuario: String,
    @Json(name = "tipoUsuario") var tipoUsuario: String,
    @Json(name = "usuarioAtivo") var usuarioAtivo: Boolean,
)

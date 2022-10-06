package com.example.appcartaoservidorv1.models

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class ComercianteModel(
    @Json(name = "id") var Id: Long,
    @Json(name = "nome") var Nome: String,
    @Json(name = "estado") var Estado: String,
    @Json(name = "cidade") var Cidade: String,
    @Json(name = "bairro") var Bairro: String,
    @Json(name = "logradouro") var Logradouro: String,
    @Json(name = "numeroCasa") var NumeroCasa: String,
    @Json(name = "cep") var Cep: String,
): Parcelable


package com.example.appcartaoservidorv1.models

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Gerente(
    @Json(name = "id") var Id: Long,
    @Json(name = "nome") var Nome: String,
    @Json(name = "matricula") var Matricula: String,
    @Json(name = "matriculaMae") var MatriculaMae: String,
    @Json(name = "cpf") var Cpf: String,
    @Json(name = "status") var Status: String,
    @Json(name = "cnpjMae") var CnpjMae: String,
): Parcelable


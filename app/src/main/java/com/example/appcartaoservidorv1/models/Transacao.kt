package com.example.appcartaoservidorv1.models

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import java.util.*

@JsonClass(generateAdapter = true)
@Parcelize
data class Transacao(
    @Json(name = "id") var Id: Long,
    @Json(name = "nomeComprador") var NomeComprador: String,
    @Json(name = "cpfComprador") var CpfComprador: String,
    @Json(name = "matriculaComprador") var MatriculaComprador: String,
    @Json(name = "nomeVendedor") var NomeVendedor: String,
    @Json(name = "cnpjVendedor") var CnpjVendedor: String,
    @Json(name = "matriculaVendedor") var MatriculaVendedor: String,
    @Json(name = "valor") var Valor: Double,
    @Json(name = "dataVenda") var DataVenda: Date,
    @Json(name = "descrição") var Descricao: String,
): Parcelable


package com.example.appcartaoservidorv1.services.api

import com.example.appcartaoservidorv1.Constantes
import com.example.appcartaoservidorv1.models.*
import com.example.appcartaoservidorv1.models.auxiliares.ParBoolString
import com.example.appcartaoservidorv1.services.CustomDateAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Header
import retrofit2.http.POST

private const val BASE_URL = Constantes.CaminhoAPIComercianteFuncionario

// Inicializa o moshi (reponssavel por colocar os resultados de Json para <T>)
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .add(CustomDateAdapter())
    .build()

// Inicializa o retrofit (reponssavel por fazer chamadas a API)
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface IAPIComercianteFuncionario {
    // Função que consulta o faturamento e as informações de um comerciante
    @POST(Constantes.ConsultaComercianteFuncionario)
    suspend fun consultaComercianteFuncionario(
        @Header("matricula") matricula: String,
        @Header("Authorization") token: String,
    ): DTOComercianteFuncionario

    // Função que busca as ultimas N 20 transações de uma matricula
    @POST(Constantes.NTransacoesComercianteFuncionario)
    suspend fun nTransacoesComercianteFuncionario(
        @Header("matricula") matricula: String,
        @Header("nConsulta") nConsulta: Int,
        @Header("Authorization") token: String,
    ): List<Transacao>

    // Função que cadastra uma venda
    @POST(Constantes.InserirVendaComercianteFuncionario)
    suspend fun inserirVendaComercianteFuncionario(
        @Header("MatriculaCliente") MatriculaCliente: String,
        @Header("MatriculaComerciante") MatriculaComerciante: String,
        @Header("MatriculaVendedor") MatriculaVendedor: String,
        @Header("Valor") Valor: String,
        @Header("NumeroCartao") NumeroCartao: String,
        @Header("SenhaCartao") SenhaCartao: String,
        @Header("Authorization") token: String,
    ): ParBoolString

}

object APIComercianteFuncionario {
    val APIComercianteFuncionarioService: IAPIComercianteFuncionario by lazy {
        retrofit.create(IAPIComercianteFuncionario::class.java)
    }
}
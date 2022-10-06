package com.example.appcartaoservidorv1.services.api

import com.example.appcartaoservidorv1.Constantes
import com.example.appcartaoservidorv1.models.ComercianteModel
import com.example.appcartaoservidorv1.models.cartao.DTOCartaoServidor
import com.example.appcartaoservidorv1.models.cartao.DTONovoCartao
import com.example.appcartaoservidorv1.models.DTO_Servidor
import com.example.appcartaoservidorv1.models.Transacao
import com.example.appcartaoservidorv1.models.auxiliares.ParBoolString
import com.example.appcartaoservidorv1.services.CustomDateAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Header
import retrofit2.http.POST


private const val BASE_URL = Constantes.CaminhoAPIServidor

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

interface IAPIServidor {
    // Função que consulta o saldo e as informações de um servidor
    @POST(Constantes.ConsultaServidor)
    suspend fun consultaServidor(
        @Header("matricula") matricula: String,
        @Header("Authorization") token: String,
    ): DTO_Servidor

    // Função que busca as N 20 transações de uma matricula servidor
    @POST(Constantes.NTransacoesServidor)
    suspend fun nTransacoesServidor(
        @Header("matricula") matricula: String,
        @Header("nConsulta") nConsulta: Int,
        @Header("Authorization") token: String,
    ): List<Transacao>

    // Função que consulta os comerciantes cadastrados no sistema
    @POST(Constantes.ListaComerciantesServidor)
    suspend fun listaComerciantes(
        @Header("Authorization") token: String,
    ): List<ComercianteModel>

    // Função que consulta os dados de um cartão do servidor
    @POST(Constantes.ConsultaCartaoServidor)
    suspend fun consultaCartao(
        @Header("matricula") matricula: String,
        @Header("Authorization") token: String,
    ): DTOCartaoServidor

    // Função que bloquea o cartão de um servidor
    @POST(Constantes.BloquearCartaoServidor)
    suspend fun bloquearCartao(
        @Header("matricula") matricula: String,
        @Header("Authorization") token: String,
    ): Boolean

    // Função que desbloquea o cartão de um servidor
    @POST(Constantes.DesbloquearCartaoServidor)
    suspend fun desbloquearCartao(
        @Header("matricula") matricula: String,
        @Header("Authorization") token: String,
    ): Boolean

    // Função que solicita um novo cartão
    @POST(Constantes.NovoCartaoServidor)
    suspend fun solicitarNovoCartao(
        @Header("matricula") matricula: String,
        @Header("Authorization") token: String,
    ): ParBoolString

    // Função que solicita um novo cartão
    @POST(Constantes.CancelarSolicitacaoNovoCartaoServidor)
    suspend fun cancelarSolicitacaoNovoCartaoServidor(
        @Header("matricula") matricula: String,
        @Header("Authorization") token: String,
    ): ParBoolString
}

object APIServidor {
    val APIServidorService: IAPIServidor by lazy {
        retrofit.create(IAPIServidor::class.java)
    }
}
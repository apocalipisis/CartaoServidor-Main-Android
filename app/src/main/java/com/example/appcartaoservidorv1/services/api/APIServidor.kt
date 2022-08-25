package com.example.appcartaoservidorv1.services.api

import com.example.appcartaoservidorv1.Constantes
import com.example.appcartaoservidorv1.models.DTO_Servidor
import com.example.appcartaoservidorv1.models.Transacao
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
}

object APIServidor {
    val APIServidorService: IAPIServidor by lazy {
        retrofit.create(IAPIServidor::class.java)
    }
}
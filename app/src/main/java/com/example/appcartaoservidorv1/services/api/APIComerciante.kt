package com.example.appcartaoservidorv1.services.api

import com.example.appcartaoservidorv1.Constantes
import com.example.appcartaoservidorv1.models.*
import com.example.appcartaoservidorv1.models.auxiliares.ParBoolString
import com.example.appcartaoservidorv1.services.CustomDateAdapter
import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Header
import retrofit2.http.POST
import java.text.SimpleDateFormat
import java.util.*

private const val BASE_URL = Constantes.CaminhoAPIComerciante

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

interface IAPIComerciante {
    // Função que consulta o faturamento e as informações de um comerciante
    @POST(Constantes.ConsultaComerciante)
    suspend fun consultaComerciante(
        @Header("matricula") matricula: String,
        @Header("Authorization") token: String,
    ): DTO_Comerciante

    // Função que busca as N 20 transações de uma matricula servidor
    @POST(Constantes.NTransacoesComerciante)
    suspend fun nTransacoesComerciante(
        @Header("matricula") matricula: String,
        @Header("nConsulta") nConsulta: Int,
        @Header("Authorization") token: String,
    ): List<Transacao>

    // Função que consulta o saldo e as informações de um servidor
    @POST(Constantes.InserirVendaComerciante)
    suspend fun inserirVendaComerciante(
        @Header("MatriculaCliente") MatriculaCliente: String,
        @Header("MatriculaComerciante") MatriculaComerciante: String,
        @Header("MatriculaVendedor") MatriculaVendedor: String,
        @Header("Valor") Valor: String,
        @Header("SenhaCartao") SenhaCartao: String,
        @Header("Authorization") token: String,
    ): DTO_InserirTransacao

    // Função que busca os N 20 gerentes de uma matricula comerciante
    @POST(Constantes.NConsultarGerentesComerciante)
    suspend fun nConsultarGerentesComerciante(
        @Header("MatriculaMae") MatriculaMae: String,
        @Header("nConsulta") nConsulta: Int,
        @Header("Authorization") token: String,
    ): List<Gerente>

    // Função que cadastra um gerente no banco de dados
    @POST(Constantes.InserirGerenteComerciante)
    suspend fun inserirGerenteComerciante(
        @Header("Nome") Nome: String,
        @Header("Cpf") Cpf: String,
        @Header("IsAtivo") IsAtivo: Boolean,
        @Header("MatriculaMae") MatriculaMae: String,
        @Header("Authorization") token: String,
    ): ParBoolString

    // Função que edita um gerente no banco de dados
    @POST(Constantes.EditarGerenteComerciante)
    suspend fun editarGerenteComerciante(
        @Header("Matricula") Matricula: String,
        @Header("IsAtivo") IsAtivo: Boolean,
        @Header("Authorization") token: String,
    ): ParBoolString

    // Função que edita um gerente no banco de dados
    @POST(Constantes.DeletarGerenteComerciante)
    suspend fun deletarGerenteComerciante(
        @Header("Matricula") Matricula: String,
        @Header("Authorization") token: String,
    ): ParBoolString

    // Função que busca as N 20 transações de uma matricula servidor
    @POST(Constantes.NConsultarFuncionarioComerciante)
    suspend fun nConsultarFuncionariosComerciante(
        @Header("MatriculaMae") MatriculaMae: String,
        @Header("nConsulta") nConsulta: Int,
        @Header("Authorization") token: String,
    ): List<Funcionario>

    // Função que cadastra um gerente no banco de dados
    @POST(Constantes.InserirFuncionarioComerciante)
    suspend fun inserirFuncionarioComerciante(
        @Header("Nome") Nome: String,
        @Header("IsAtivo") IsAtivo: Boolean,
        @Header("MatriculaMae") MatriculaMae: String,
        @Header("Authorization") token: String,
    ): ParBoolString

    // Função que edita um funcionario no banco de dados
    @POST(Constantes.EditarFuncionarioComerciante)
    suspend fun editarFuncionarioComerciante(
        @Header("Matricula") Matricula: String,
        @Header("IsAtivo") IsAtivo: Boolean,
        @Header("Authorization") token: String,
    ): ParBoolString

    // Função que edita um gerente no banco de dados
    @POST(Constantes.DeletarFuncionarioComerciante)
    suspend fun deletarFuncionarioComerciante(
        @Header("Matricula") Matricula: String,
        @Header("Authorization") token: String,
    ): ParBoolString

}

object APIComerciante {
    val APIComercianteService: IAPIComerciante by lazy {
        retrofit.create(IAPIComerciante::class.java)
    }
}
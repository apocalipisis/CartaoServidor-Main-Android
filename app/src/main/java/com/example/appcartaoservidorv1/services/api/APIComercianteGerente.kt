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

private const val BASE_URL = Constantes.CaminhoAPIComercianteGerente

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

interface IAPIComercianteGerente {
    // Função que consulta o faturamento e as informações de um comerciante
    @POST(Constantes.ConsultaComercianteGerente)
    suspend fun consultaComercianteGerente(
        @Header("matricula") matricula: String,
        @Header("Authorization") token: String,
    ): DTOComercianteGerente

    // Função que busca as N 20 transações de uma matricula servidor
    @POST(Constantes.NTransacoesComercianteGerente)
    suspend fun nTransacoesComerciantegerente(
        @Header("matricula") matricula: String,
        @Header("nConsulta") nConsulta: Int,
        @Header("Authorization") token: String,
    ): List<Transacao>

    // Função que cadastra uma venda
    @POST(Constantes.InserirVendaComercianteGerente)
    suspend fun inserirVendaComercianteGerente(
        @Header("MatriculaCliente") MatriculaCliente: String,
        @Header("MatriculaComerciante") MatriculaComerciante: String,
        @Header("MatriculaVendedor") MatriculaVendedor: String,
        @Header("Valor") Valor: String,
        @Header("NumeroCartao") NumeroCartao: String,
        @Header("SenhaCartao") SenhaCartao: String,
        @Header("Authorization") token: String,
    ): ParBoolString

    // Função que busca as N 20 transações de uma matricula servidor
    @POST(Constantes.NConsultarFuncionarioComercianteGerente)
    suspend fun nConsultarFuncionariosComercianteGerente(
        @Header("Matricula") Matricula: String,
        @Header("nConsulta") nConsulta: Int,
        @Header("Authorization") token: String,
    ): List<Funcionario>

    // Função que cadastra um gerente no banco de dados
    @POST(Constantes.InserirFuncionarioComercianteGerente)
    suspend fun inserirFuncionarioComercianteGerente(
        @Header("Nome") Nome: String,
        @Header("Matricula") Matricula: String,
        @Header("Authorization") token: String,
    ): ParBoolString

    // Função que edita um funcionario no banco de dados
    @POST(Constantes.EditarFuncionarioComercianteGerente)
    suspend fun editarFuncionarioComercianteGerente(
        @Header("Matricula") Matricula: String,
        @Header("IsAtivo") IsAtivo: Boolean,
        @Header("Authorization") token: String,
    ): ParBoolString

    // Função que edita um gerente no banco de dados
    @POST(Constantes.DeletarFuncionarioComercianteGerente)
    suspend fun deletarFuncionarioComercianteGerente(
        @Header("Matricula") Matricula: String,
        @Header("Authorization") token: String,
    ): ParBoolString

}

object APIComercianteGerente {
    val APIComercianteGerenteService: IAPIComercianteGerente by lazy {
        retrofit.create(IAPIComercianteGerente::class.java)
    }
}
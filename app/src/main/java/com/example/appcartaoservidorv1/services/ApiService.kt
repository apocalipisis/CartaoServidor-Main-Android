package com.example.appcartaoservidorv1.services

import com.example.appcartaoservidorv1.Constantes
import com.example.appcartaoservidorv1.models.*
import com.example.appcartaoservidorv1.models.auxiliares.ParBoolString
import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Header
import retrofit2.http.POST
import java.text.SimpleDateFormat
import java.util.*


private const val BASE_URL = Constantes.CaminhoAPI

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

interface myAndroidApiService {
    // Função que verifica o status de login de um usuário
    @POST(Constantes.ConsultaLogin)
    suspend fun verificaLogin(
        @Header("matricula") matricula: String,
        @Header("senha") senha: String
    ): DTO_Login

    // Função que consulta o saldo e as informações de um servidor
    @POST(Constantes.ConsultaServidor)
    suspend fun consultaServidor(
        @Header("matricula") matricula: String,
        @Header("Authorization") token: String,
    ): DTO_Servidor

    // Função que consulta o saldo e as informações de um comerciante
    @POST(Constantes.ConsultaComerciante)
    suspend fun consultaComerciante(
        @Header("matricula") matricula: String,
        @Header("Authorization") token: String,
        ): DTO_Comerciante

    // Função que busca as N 20 transações de uma matricula servidor
    @POST(Constantes.NTransacoesServidor)
    suspend fun NTransacoesServidor(
        @Header("matricula") matricula: String,
        @Header("nConsulta") nConsulta: Int,
        @Header("Authorization") token: String,
        ): List<Transacao>

    // Função que busca as N 20 transações de uma matricula servidor
    @POST(Constantes.NTransacoesComerciante)
    suspend fun NTransacoesComerciante(
        @Header("matricula") matricula: String,
        @Header("nConsulta") nConsulta: Int,
        @Header("Authorization") token: String,
    ): List<Transacao>

    // Função que consulta o saldo e as informações de um servidor
    @POST(Constantes.InserirVenda)
    suspend fun InserirVenda(
        @Header("MatriculaCliente") MatriculaCliente: String,
        @Header("MatriculaComerciante") MatriculaComerciante: String,
        @Header("MatriculaVendedor") MatriculaVendedor: String,
        @Header("Valor") Valor: String,
        @Header("SenhaCartao") SenhaCartao: String,
        @Header("Authorization") token: String,
        ): DTO_InserirTransacao

    // Função que busca os N 20 gerentes de uma matricula comerciante
    @POST(Constantes.NConsultarGerentesComerciante)
    suspend fun nConsultarGerentes(
        @Header("MatriculaMae") MatriculaMae: String,
        @Header("nConsulta") nConsulta: Int,
        @Header("Authorization") token: String,
    ): List<Gerente>

    // Função que cadastra um gerente no banco de dados
    @POST(Constantes.InserirGerenteComerciante)
    suspend fun inserirGerente(
        @Header("Nome") Nome: String,
        @Header("Cpf") Cpf: String,
        @Header("IsAtivo") IsAtivo: Boolean,
        @Header("MatriculaMae") MatriculaMae: String,
        @Header("Authorization") token: String,
    ): ParBoolString

    // Função que edita um gerente no banco de dados
    @POST(Constantes.EditarGerenteComerciante)
    suspend fun editarGerente(
        @Header("Matricula") Matricula: String,
        @Header("IsAtivo") IsAtivo: Boolean,
        @Header("Authorization") token: String,
    ): ParBoolString

    // Função que edita um gerente no banco de dados
    @POST(Constantes.DeletarGerenteComerciante)
    suspend fun deletarGerente(
        @Header("Matricula") Matricula: String,
        @Header("Authorization") token: String,
    ): ParBoolString

    // Função que busca as N 20 transações de uma matricula servidor
    @POST(Constantes.NConsultarFuncionarioComerciante)
    suspend fun nConsultarFuncionarios(
        @Header("MatriculaMae") MatriculaMae: String,
        @Header("nConsulta") nConsulta: Int,
        @Header("Authorization") token: String,
    ): List<Funcionario>

    // Função que cadastra um gerente no banco de dados
    @POST(Constantes.InserirFuncionarioComerciante)
    suspend fun inserirFuncionario(
        @Header("Nome") Nome: String,
        @Header("IsAtivo") IsAtivo: Boolean,
        @Header("MatriculaMae") MatriculaMae: String,
        @Header("Authorization") token: String,
    ): ParBoolString

    // Função que edita um funcionario no banco de dados
    @POST(Constantes.EditarFuncionarioComerciante)
    suspend fun editarFuncionario(
        @Header("Matricula") Matricula: String,
        @Header("IsAtivo") IsAtivo: Boolean,
        @Header("Authorization") token: String,
    ): ParBoolString

    // Função que edita um gerente no banco de dados
    @POST(Constantes.DeletarFuncionarioComerciante)
    suspend fun deletarFuncionario(
        @Header("Matricula") Matricula: String,
        @Header("Authorization") token: String,
    ): ParBoolString

}

object myAndroidApi {
    val retrofitService: myAndroidApiService by lazy {
        retrofit.create(myAndroidApiService::class.java)
    }
}

class CustomDateAdapter : JsonAdapter<Date>() {
    private val dateFormat = SimpleDateFormat(SERVER_FORMAT, Locale.getDefault())

    @FromJson
    override fun fromJson(reader: JsonReader): Date? {
        return try {
            val dateAsString = reader.nextString()
            synchronized(dateFormat) {
                dateFormat.parse(dateAsString)
            }
        } catch (e: Exception) {
            null
        }
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: Date?) {
        if (value != null) {
            synchronized(dateFormat) {
                writer.value(value.toString())
            }
        }
    }

    companion object {
        const val SERVER_FORMAT = ("yyyy-MM-dd'T'HH:mm") // define your server format here
    }
}

//@Retention(AnnotationRetention.RUNTIME)
//@JsonQualifier
//annotation class WrappedRepoList
//
//@JsonClass(generateAdapter = true)
//data class TransacaoList(val items: List<Transacao>)
//
//class TransacaoListJsonAdapter {
//    @WrappedRepoList
//    @FromJson
//    fun fromJson(json: TransacaoList): List<Transacao> {
//        return json.items
//    }
//
//    @ToJson
//    fun toJson(@WrappedRepoList value: List<Transacao>): TransacaoList {
//        throw UnsupportedOperationException()
//    }
//}
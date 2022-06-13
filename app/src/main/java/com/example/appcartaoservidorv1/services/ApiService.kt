package com.example.appcartaoservidorv1.services

import com.example.appcartaoservidorv1.Constantes
import com.example.appcartaoservidorv1.models.*
import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
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
    ): DTO_Servidor

    // Função que consulta o saldo e as informações de um comerciante
    @POST(Constantes.ConsultaComerciante)
    suspend fun consultaComerciante(
        @Header("matricula") matricula: String,
    ): DTO_Comerciante

    // Função que consulta o saldo e as informações de um servidor
    @POST(Constantes.ExtratoServidor)
    suspend fun extratoServidor(
        @Header("matricula") matricula: String,
    ): List<Transacao>
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

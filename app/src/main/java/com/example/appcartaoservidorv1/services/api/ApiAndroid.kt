package com.example.appcartaoservidorv1.services

import com.example.appcartaoservidorv1.Constantes
import com.example.appcartaoservidorv1.models.DTO_Login
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

interface IAPIAndroid {
    // Função que verifica o status de login de um usuário
    @POST(Constantes.ConsultaLogin)
    suspend fun verificaLogin(
        @Header("matricula") matricula: String,
        @Header("senha") senha: String
    ): DTO_Login
}

object APIAndroid {
    val APIAndroidService: IAPIAndroid by lazy {
        retrofit.create(IAPIAndroid::class.java)
    }
}

// Adptador para os valores de datas (Coloca as datas em formato do Android)
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


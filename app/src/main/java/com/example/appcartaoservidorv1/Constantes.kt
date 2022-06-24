package com.example.appcartaoservidorv1

object Constantes {
    // API
    const val CaminhoAPI: String = "https://da1teste.herokuapp.com/ApiAndroid/"
    const val ConsultaLogin: String = "loginandroid/"
    const val ConsultaServidor: String = "InfoServidor/"
    const val ConsultaComerciante: String = "InfoComerciante/"
    const val InserirVenda: String = "InserirVendaAndroid/"
    const val NTransacoes: String = "NTransacoes/"
    // Resposta de error
    const val Erro1 = "Problemas ao tentar acessar o servidor"
    const val Erro2 = "Credenciais Incorretas"
    // Telefone
    const val Telefone: String = "5561996033619"
    const val TelefoneWhats: String = Telefone
    const val TelefoneApp: String = "+"+Telefone
    // Site
    const val Site: String = "https://da1teste.herokuapp.com/"
    // Nome Sistema
    const val NomeSistema: String = "Cartão Servidor"

    // Shared Preferences
    const val NomeArquivoSharedPreferences: String = "com.example.appcartaoservidorv1.LOGIN_FILE_KEY"
    const val NomeArquivoToken: String = "com.example.appcartaoservidorv1.TOKEN_FILE_KEY"
}
package com.example.appcartaoservidorv1

object Constantes {
    // API
    const val CaminhoAPI: String = "https://cartaoservidor.com/ApiAndroid/"
    const val ConsultaLogin: String = "LoginAndroid/"

    //----------------------------------------------------------------------------------------------
    // API Servidor
    const val CaminhoAPIServidor: String = "https://cartaoservidor.com/ApiAndroidServidor/"
    const val ConsultaServidor: String = "InfoServidor/"
    const val NTransacoesServidor: String = "NTransacoesServidor/"

    //----------------------------------------------------------------------------------------------
    // API Comerciante
    const val CaminhoAPIComerciante: String = "https://cartaoservidor.com/ApiAndroidComerciante/"

    const val ConsultaComerciante: String = "InfoComerciante/"
    const val InserirVendaComerciante: String = "InserirVendaComerciante/"

    const val NTransacoesComerciante: String = "NTransacoesComerciante/"

    const val NConsultarGerentesComerciante: String = "NConsultarGerentesComerciante/"
    const val InserirGerenteComerciante: String = "InserirGerentesComerciante/"
    const val EditarGerenteComerciante: String = "EditarGerentesComerciante/"
    const val DeletarGerenteComerciante: String = "DeletarGerentesComerciante/"

    const val NConsultarFuncionarioComerciante: String = "NConsultarFuncionariosComerciante/"
    const val InserirFuncionarioComerciante: String = "InserirFuncionarioComerciante/"
    const val EditarFuncionarioComerciante: String = "EditarFuncionarioComerciante/"
    const val DeletarFuncionarioComerciante: String = "DeletarFuncionarioComerciante/"

    //----------------------------------------------------------------------------------------------
    // API Comerciante Gerente
    const val CaminhoAPIComercianteGerente: String =
        "https://cartaoservidor.com/ApiAndroidComercianteGerente/"

    const val ConsultaComercianteGerente: String = "InfoComercianteGerente/"
    const val InserirVendaComercianteGerente: String = "InserirVendaComercianteGerente/"

    const val NTransacoesComercianteGerente: String = "NTransacoesComercianteGerente/"

    const val NConsultarFuncionarioComercianteGerente: String =
        "NConsultarFuncionariosComercianteGerente/"
    const val InserirFuncionarioComercianteGerente: String = "InserirFuncionarioComercianteGerente/"
    const val EditarFuncionarioComercianteGerente: String = "EditarFuncionarioComercianteGerente/"
    const val DeletarFuncionarioComercianteGerente: String = "DeletarFuncionarioComercianteGerente/"

    //----------------------------------------------------------------------------------------------
    // Resposta de error
    const val Erro1 = "Problemas ao tentar acessar o servidor"
    const val Erro2 = "Credenciais Incorretas"

    //----------------------------------------------------------------------------------------------
    // Telefone
    const val Telefone: String = "5561996033619"
    const val TelefoneWhats: String = Telefone
    const val TelefoneApp: String = "+$Telefone"

    //----------------------------------------------------------------------------------------------
    // Site
    const val Site: String = "https://da1teste.herokuapp.com/"

    //----------------------------------------------------------------------------------------------
    // Nome Sistema
    const val NomeSistema: String = "Cartão Servidor"

    //----------------------------------------------------------------------------------------------
    // Shared Preferences
    const val NomeArquivoSharedPreferences: String =
        "com.example.appcartaoservidorv1.LOGIN_FILE_KEY"
    const val NomeArquivoToken: String = "com.example.appcartaoservidorv1.TOKEN_FILE_KEY"
}
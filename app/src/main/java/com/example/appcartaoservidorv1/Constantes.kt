package com.example.appcartaoservidorv1

object Constantes {

    const val TempoLimite: Long = 15

    //----------------------------------------------------------------------------------------------
    // API
    const val CaminhoAPI: String = "https://cartaoservidor.com/ApiAndroid/"
    const val ConsultaLogin: String = "LoginAndroid/"

    //----------------------------------------------------------------------------------------------
    // API Servidor
    const val CaminhoAPIServidor: String = "https://cartaoservidor.com/ApiAndroidServidor/"
    const val ConsultaServidor: String = "InfoServidor/"
    const val NTransacoesServidor: String = "NTransacoesServidor/"

    const val ConsultaCartaoServidor: String = "ConsultaCartaoServidor/"
    const val BloquearCartaoServidor: String = "BloquearCartaoServidor/"
    const val DesbloquearCartaoServidor: String = "DesbloquearCartaoServidor/"
    const val NovoCartaoServidor: String = "NovoCartaoServidor/"
    const val CancelarSolicitacaoNovoCartaoServidor: String =
        "CancelarSolicitacaoNovoCartaoServidor/"

    const val ListaComerciantesServidor: String = "ListaComerciantesServidor/"

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
    // API Comerciante Funcionario
    const val CaminhoAPIComercianteFuncionario: String =
        "https://cartaoservidor.com/ApiAndroidComercianteFuncionario/"

    const val ConsultaComercianteFuncionario: String = "InfoComercianteFuncionario/"
    const val InserirVendaComercianteFuncionario: String = "InserirVendaComercianteFuncionario/"

    const val NTransacoesComercianteFuncionario: String = "NTransacoesComercianteFuncionario/"

    //----------------------------------------------------------------------------------------------
    // Resposta de error
    const val Erro1 = "Estamos enfrentando problemas internos"
    const val Erro2 = "Credenciais Incorretas"

    const val Erro3 = "Erro no sistema, aguarde e tente novamente"
    const val Erro4 = "Erro ao tentar acessar o sistema, aguarde e tente novamente"

    const val Erro5 = "Matricula invalida"
    const val Erro6 = "Digite uma senha"

    const val Erro7 = "Não foi possivel gerar o QRCode, tente novamente"

    //----------------------------------------------------------------------------------------------
    // Telefone
    const val Telefone: String = "5561996033619"
    const val TelefoneWhats: String = Telefone
    const val TelefoneApp: String = "+$Telefone"

    //----------------------------------------------------------------------------------------------
    // Site
    const val Site: String = "https://cartaoservidor.com/"

    //----------------------------------------------------------------------------------------------
    // Nome Sistema
    const val NomeSistema: String = "Cartão Servidor"

    //----------------------------------------------------------------------------------------------
    // Shared Preferences
    const val NomeArquivoSharedPreferences: String =
        "com.example.appcartaoservidorv1.LOGIN_FILE_KEY"
    const val NomeArquivoToken: String = "com.example.appcartaoservidorv1.TOKEN_FILE_KEY"
}
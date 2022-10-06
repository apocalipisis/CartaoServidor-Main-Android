package com.example.appcartaoservidorv1.services.redirecionamento

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.appcartaoservidorv1.fragments.comerciantefuncionario.*
import com.example.appcartaoservidorv1.fragments.comerciantegerente.*
import com.example.appcartaoservidorv1.models.Transacao

// ---------------------------------------ComercianteGerente-------------------------------------- //
// ---------------------------------------ComercianteGerente Page-------------------------------------- //
// Função que redireciona o usuario da página do comerciante para Login
fun fromComerciantefuncionarioToLogin(fragment: Fragment) {
    val action =
        ComercianteFuncionarioFragmentDirections.actionComercianteFuncionarioFragmentToLoginFragment()
    NavHostFragment.findNavController(fragment).navigate(action)
}

// Função que redireciona o usuario da página do ComercianteGerente para a pagina de informações
fun fromComerciantefuncionarioToInfo(
    fragment: Fragment,
    nome: String,
    matricula: String,
    tipoUsuario: String,
    status: String,
    matriculaMae: String,
    cnpj: String,
    nomeComerciante: String,
) {
    val action =
        ComercianteFuncionarioFragmentDirections.actionComercianteFuncionarioFragmentToComercianteFuncionarioInfoFragment(
            nome,
            matricula,
            tipoUsuario,
            status,
            matriculaMae,
            cnpj,
            nomeComerciante,
        )
    NavHostFragment.findNavController(fragment).navigate(action)
}

// Função que redireciona o usuario da página do comerciante para a pagina do extrato
fun fromComerciantefuncionarioToHistoricovendas(
    fragment: Fragment,
    matricula: String,
    token: String
) {
    val action =
        ComercianteFuncionarioFragmentDirections.actionComercianteFuncionarioFragmentToComercianteFuncionarioHistoricovendasFragment(
            matricula,
            token,
        )
    NavHostFragment.findNavController(fragment).navigate(action)
}

// Função que redireciona o usuario da página do comerciante gerente para a pagina Vender
fun fromComerciantefuncionarioToInserirvalor(
    fragment: Fragment,
    matriculaComerciante: String,
    nomeComerciante: String,
    matriculaVendedor: String,
    nomeVendedor: String,
    token: String,
) {
    val action =
        ComercianteFuncionarioFragmentDirections.actionComercianteFuncionarioFragmentToComercianteFuncionarioVendaValorFragment(
            matriculaComerciante,
            nomeComerciante,
            matriculaVendedor,
            nomeVendedor,
            token,
        )
    NavHostFragment.findNavController(fragment).navigate(action)
}

// --------------------------------Historico de Vendas  ------------------------------------ //
// Função que redireciona o usuario da página historico vendas para a página detalhes transação
fun fromComercianteFuncionarioHistoricovendasToDetalhes(fragment: Fragment, transacao: Transacao) {
    val action =
        ComercianteFuncionarioHistoricovendasFragmentDirections.actionComercianteFuncionarioHistoricovendasFragmentToDialogComerciantefuncionarioDetalhestransacao(
            transacao
        )
    NavHostFragment.findNavController(fragment).navigate(action)
}

// --------------------------------Inserir Valor ------------------------------------ //
// Função que redireciona o usuario para a pagina Inserir Senha
fun fromComerciantefuncionarioValorToQrcode(
    fragment: Fragment,
    valor: Float,
    matriculaComerciante: String,
    nomeComerciante: String,
    matriculaVendedor: String,
    nomeVendedor: String,
    token: String,
) {
    val action =
        ComercianteFuncionarioVendaValorFragmentDirections.actionComercianteFuncionarioVendaValorFragmentToComercianteFuncionarioVendaQrcodeFragment(
            valor,
            matriculaComerciante,
            nomeComerciante,
            matriculaVendedor,
            nomeVendedor,
            token,
        )
    NavHostFragment.findNavController(fragment).navigate(action)
}

// --------------------------------QR Code Comerciante Gerente------------------------------------ //
// Função que redireciona o usuario para a pagina Inserir Senha
fun fromComerciantefuncionarioQrcodeToInserirsenha(
    fragment: Fragment,
    matriculaCliente: String,
    nomeCliente: String,
    valor: Float,
    matriculaComerciante: String,
    nomeComerciante: String,
    matriculaVendedor: String,
    nomeVendedor: String,
    numeroCartao: String,
    token: String,
) {
    val action =
        ComercianteFuncionarioVendaQrcodeFragmentDirections.actionComercianteFuncionarioVendaQrcodeFragmentToComercianteFuncionarioVendaSenhaFragment(
            matriculaCliente,
            nomeCliente,
            valor,
            matriculaComerciante,
            nomeComerciante,
            matriculaVendedor,
            nomeVendedor,
            numeroCartao,
            token,
        )
    NavHostFragment.findNavController(fragment).navigate(action)
}

// --------------------------------Inserir senha Comerciante Gerente------------------------------------ //
// Função que redireciona o usuario da página Inserir senha para a página comerciante gerente
fun fromComerciantefuncionarioInserirsenhaToComerciantefuncionario(
    fragment: Fragment,
    matricula: String,
    nome: String,
    token: String,
) {
    val action =
        ComercianteFuncionarioVendaSenhaFragmentDirections.actionComercianteFuncionarioVendaSenhaFragmentToComercianteFuncionarioFragment(
            matricula,
            nome,
            token,
        )
    NavHostFragment.findNavController(fragment).navigate(action)
}

// Função que redireciona o usuario da página Inserir senha para a página status venda
fun fromComerciantefuncionarioInserirsenhaToStatus(
    fragment: Fragment,
    matriculaCliente: String,
    matriculaComerciante: String,
    matriculaVendedor: String,
    valor: Float,
    senha: String,
    nomeVendedor: String,
    numeroCartao: String,
    token: String,
) {
    val action =
        ComercianteFuncionarioVendaSenhaFragmentDirections.actionComercianteFuncionarioVendaSenhaFragmentToComercianteFuncionarioVendaStatusFragment(
            matriculaCliente,
            matriculaComerciante,
            matriculaVendedor,
            valor,
            senha,
            nomeVendedor,
            numeroCartao,
            token,
        )
    NavHostFragment.findNavController(fragment).navigate(action)
}

// --------------------------------Status Venda Page------------------------------------ //
// Função que redireciona o usuario da página Status venda para a página do comerciante
fun fromComercianteFuncionarioVendaStatusToComerciantefuncionario(
    fragment: Fragment,
    matricula: String,
    nome: String,
    token: String,
) {
    val action =
        ComercianteFuncionarioVendaStatusFragmentDirections.actionComercianteFuncionarioVendaStatusFragmentToComercianteFuncionarioFragment(
            matricula,
            nome,
            token,
        )
    NavHostFragment.findNavController(fragment).navigate(action)
}
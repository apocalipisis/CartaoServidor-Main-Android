package com.example.appcartaoservidorv1

import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.appcartaoservidorv1.fragments.NointernetstatusvendaFragmentDirections
import com.example.appcartaoservidorv1.fragments.comerciante.*
import com.example.appcartaoservidorv1.fragments.login.LoginFragmentDirections
import com.example.appcartaoservidorv1.fragments.servidor.ExtratoservidorFragmentDirections
import com.example.appcartaoservidorv1.fragments.servidor.ServidorFragmentDirections
import com.example.appcartaoservidorv1.models.Transacao

// ---------------------------------------No internet Page-------------------------------------- //
fun goToNointernetpage(view: View) {
    view.findNavController().navigate(R.id.nointernetFragment)
}

// ---------------------------- No internet Status Venda Page --------------------------------- //
fun goToNointernetstatusvendapage(
    fragment: Fragment,
    nome: String,
    matricula: String,
    token: String,
) {
    val action =
        StatusvendaFragmentDirections.actionStatusvendaFragmentToNointernetstatusvendaFragment3(
            matricula,
            nome,
            token,
        )
    NavHostFragment.findNavController(fragment).navigate(action)
}

fun fromNointernetstatusvendapageToComerciante(
    fragment: Fragment,
    nome: String,
    matricula: String,
    token: String,
) {
    val action =
        NointernetstatusvendaFragmentDirections.actionNointernetstatusvendaFragment3ToComercianteFragment(
            matricula,
            nome,
            token,
        )
    NavHostFragment.findNavController(fragment).navigate(action)
}

// ---------------------------------------Login-------------------------------------- //
// Função que vai para a página de login e apaga a pilha de navegação
fun goToLoginPage(view: View) {
    val navController = findNavController(view)
    val startDestination = navController.graph.startDestinationId
    val navOptions = NavOptions.Builder()
        .setPopUpTo(startDestination, true)
        .build()
    navController.navigate(startDestination, null, navOptions)
}

// Função que redireciona o usuario do login para a pagina do servidor
fun fromLoginToServidor(
    fragment: Fragment,
    nome: String,
    matricula: String,
    token: String,
) {
    val action = LoginFragmentDirections.actionLoginFragmentToServidorFragment(
        matricula,
        nome,
        token,
    )
    NavHostFragment.findNavController(fragment).navigate(action)
}

// Função que redireciona o usuario do login para a pagina do comerciante
fun fromLoginToComerciante(
    fragment: Fragment,
    nome: String,
    matricula: String,
    token: String,
) {
    val action = LoginFragmentDirections.actionLoginFragmentToComercianteFragment(
        matricula,
        nome,
        token,
    )
    NavHostFragment.findNavController(fragment).navigate(action)
}

// Função que redireciona o usuario do login para a pagina de usuário inativo
fun fromLoginToUsuarioinativo(fragment: Fragment, nome: String, matricula: String) {
    val action = LoginFragmentDirections.actionLoginFragmentToUsuarioinativoFragment(
        nome,
        matricula,
    )
    NavHostFragment.findNavController(fragment).navigate(action)
}

// Função que redireciona o usuario do login para a pagina de usuário não permitido
fun fromLoginToUsuarionaopermitido(fragment: Fragment) {
    val action = LoginFragmentDirections.actionLoginFragmentToUsuarionaopermitidoFragment()
    NavHostFragment.findNavController(fragment).navigate(action)
}

// ---------------------------------------Servidor-------------------------------------- //
// ---------------------------------------Servidor Page-------------------------------------- //
// Função que redireciona o usuario da página do servidor para a pagina de informações
fun fromServidorToInfo(
    fragment: Fragment,
    matricula: String,
    nome: String,
    cpf: String,
    tipoUsuario: String,
    status: String,
    instituto: String,
    limiteMensal: Double,
) {
    val action = ServidorFragmentDirections.actionServidorFragmentToInfoservidorFragment(
        matricula,
        nome,
        cpf,
        tipoUsuario,
        status,
        instituto,
        limiteMensal.toFloat()
    )
    NavHostFragment.findNavController(fragment).navigate(action)
}

// Função que redireciona o usuario da página do servidor para a pagina Comprar
fun fromServidorToComprar(
    fragment: Fragment,
    matricula: String,
    nome: String,
) {
    val action = ServidorFragmentDirections.actionServidorFragmentToCompraservidorFragment(
        matricula,
        nome
    )
    NavHostFragment.findNavController(fragment).navigate(action)
}

// Função que redireciona o usuario da página do servidor para a pagina do extrato
fun fromServidorToExtrato(
    fragment: Fragment,
    matricula: String,
    token: String,
) {
    val action =
        ServidorFragmentDirections.actionServidorFragmentToExtratoservidorFragment(
            matricula,
            token,
        )
    NavHostFragment.findNavController(fragment).navigate(action)
}

// Função que redireciona o usuario da página do servidor para Login
fun fromServidorToLogin(fragment: Fragment) {
    val action = ServidorFragmentDirections.actionServidorFragmentToLoginFragment()
    NavHostFragment.findNavController(fragment).navigate(action)
}

// ---------------------------------------Extrato Page-------------------------------------- //
// Função que redireciona o usuario da página extrato servidor para a página detalhes transação
fun fromExtratoToDetalhes(fragment: Fragment, transacao: Transacao) {
    val action =
        ExtratoservidorFragmentDirections.actionExtratoservidorFragmentToTransacaodetalhesFragment(
            transacao
        )

    NavHostFragment.findNavController(fragment).navigate(action)
}

// ---------------------------------------Comerciante-------------------------------------- //
// ---------------------------------------Comerciante Page-------------------------------------- //
// Função que redireciona o usuario da página do Comerciante para a pagina de informações
fun fromComercianteToInfo(
    fragment: Fragment,
    matricula: String,
    nome: String,
    cnpj: String,
    tipoUsuario: String,
    status: String,
    pagementoUsoDoSistema: Float
) {
    val action =
        ComercianteFragmentDirections.actionComercianteFragmentToInfocomercianteFragment(
            matricula,
            nome,
            cnpj,
            tipoUsuario,
            status,
            pagementoUsoDoSistema
        )
    NavHostFragment.findNavController(fragment).navigate(action)
}

// Função que redireciona o usuario da página do comerciante para a pagina Vender
fun fromComercianteToInserirvalor(
    fragment: Fragment,
    nomeComerciante: String,
    matriculaComerciante: String,
    token: String
) {
    val action = ComercianteFragmentDirections.actionComercianteFragmentToInserirvalorFragment(
        nomeComerciante,
        matriculaComerciante,
        token,
    )
    NavHostFragment.findNavController(fragment).navigate(action)
}

// Função que redireciona o usuario da página do servidor para a pagina do extrato
fun fromComercianteToHistoricovendas(fragment: Fragment, matricula: String, token: String) {
    val action =
        ComercianteFragmentDirections.actionComercianteFragmentToHistoricovendasFragment(
            matricula,
            token,
        )
    NavHostFragment.findNavController(fragment).navigate(action)
}

// Função que redireciona o usuario da página do servidor para Login
fun fromComercianteToLogin(fragment: Fragment) {
    val action = ComercianteFragmentDirections.actionComercianteFragmentToLoginFragment()
    NavHostFragment.findNavController(fragment).navigate(action)
}

// --------------------------------Historico Vendas Page------------------------------------ //
// Função que redireciona o usuario da página extrato servidor para a página detalhes transação
fun fromHistoricovendasToDetalhes(fragment: Fragment, transacao: Transacao) {
    val action =
        HistoricovendasFragmentDirections.actionHistoricovendasFragmentToTransacaodetalhescomercianteFragment(
            transacao
        )

    NavHostFragment.findNavController(fragment).navigate(action)
}

// --------------------------------Inserir Valor Page------------------------------------ //
// Função que redireciona o usuario para a pagina Inserir Senha
fun fromInserirvalorToVendacomerciante(
    fragment: Fragment,
    valor: Float,
    nomeComerciante: String,
    matriculaComerciante: String,
    token: String,
) {
    val action =
        InserirvalorFragmentDirections.actionInserirvalorFragmentToVendacomercianteFragment(
            valor,
            nomeComerciante,
            matriculaComerciante,
            token,
        )
    NavHostFragment.findNavController(fragment).navigate(action)
}

// --------------------------------Venda Comerciante Page------------------------------------ //
// Função que redireciona o usuario para a pagina Inserir Senha
fun fromVendacomercianteToInserirsenha(
    fragment: Fragment,
    nomeServidor: String,
    matricula: String,
    valor: Float,
    nomeComerciante: String,
    matriculaComerciante: String,
    token: String,
) {
    val action =
        VendercomercianteFragmentDirections.actionVendacomercianteFragmentToInserirsenhaFragment(
            matricula,
            nomeServidor,
            valor,
            nomeComerciante,
            matriculaComerciante,
            token,
        )
    NavHostFragment.findNavController(fragment).navigate(action)
}

// --------------------------------Inserir Senha Page------------------------------------ //
// Função que redireciona o usuario da página Inserir senha para a página status venda
fun fromInserirsenhaToStatusvenda(
    fragment: Fragment,
    valor: Float,
    matricula: String,
    matriculaComerciante: String,
    senha: String,
    nomeComerciante: String,
    token: String,
) {
    val action =
        InserirsenhaFragmentDirections.actionInserirsenhaFragmentToStatusvendaFragment(
            valor,
            matricula,
            matriculaComerciante,
            senha,
            nomeComerciante,
            token,
        )
    NavHostFragment.findNavController(fragment).navigate(action)
}

// Função que redireciona o usuario da página Inserir senha para a página comerciante
fun fromInserirsenhaToComerciante(
    fragment: Fragment,
    matriculaComerciante: String,
    nome: String,
    token: String,
) {
    val action =
        InserirsenhaFragmentDirections.actionInserirsenhaFragmentToComercianteFragment(
            nome,
            matriculaComerciante,
            token,
        )
    NavHostFragment.findNavController(fragment).navigate(action)
}

// --------------------------------Status Venda Page------------------------------------ //
// Função que redireciona o usuario da página Status venda para a página status venda

fun fromStatusvendaToComerciante(
    fragment: Fragment,
    nome: String,
    matriculaComerciante: String,
    token: String,
) {
    val action =
        StatusvendaFragmentDirections.actionStatusvendaFragmentToComercianteFragment(
            matriculaComerciante,
            nome,
            token,
        )
    NavHostFragment.findNavController(fragment).navigate(action)
}




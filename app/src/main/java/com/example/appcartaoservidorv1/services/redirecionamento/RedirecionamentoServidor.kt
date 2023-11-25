package com.example.appcartaoservidorv1.services.redirecionamento

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.dialogs.servidor.DialogServidorNovoCartaoDirections
import com.example.appcartaoservidorv1.fragments.servidor.*
import com.example.appcartaoservidorv1.models.ComercianteModel
import com.example.appcartaoservidorv1.models.Transacao

// ---------------------------------------Servidor-------------------------------------- //
// ---------------------------------------Servidor Page-------------------------------------- //
// Função que redireciona o usuario da página do servidor para a pagina de cartão

// Função que redireciona o usuario da página do servidor para a pagina Comprar
fun fromServidorToComprar(
    fragment: Fragment,
    matricula: String,
    nome: String,
    statusCartao: String,
    numeroCartao: String,
    token: String,
) {

    val nav = fragment.findNavController()

    val action = ServidorDirections.actionServidorFragmentToCompraservidorFragment(
        matricula,
        nome,
        statusCartao,
        numeroCartao,
        token,
    )

    if (nav.currentDestination?.id == R.id.servidorFragment)
        nav.navigate(action)
}


fun fromServidorToCartao(
    fragment: Fragment,
    matricula: String,
    token: String,
) {

    val nav = fragment.findNavController()

    val action = ServidorDirections.actionServidorFragmentToServidorCartaoFragment(
        matricula,
        token,
    )

    if (nav.currentDestination?.id == R.id.servidorFragment)
        nav.navigate(action)
}

// Função que redireciona o usuario da página do servidor para a pagina de dividas do cartão
fun fromServidorToComerciantes(
    fragment: Fragment,
    token: String,
) {

    val nav = fragment.findNavController()

    val action = ServidorDirections.actionServidorFragmentToServidorComerciantesFragment(
        token,
    )

    if (nav.currentDestination?.id == R.id.servidorFragment)
        nav.navigate(action)

}

// ---------------------------------------Compra Servidor-------------------------------------- //
fun fromComprarToCartao(
    fragment: Fragment,
    matricula: String,
    token: String,
) {

    val nav = fragment.findNavController()

    val action = ComprarDirections.actionCompraservidorFragmentToServidorCartaoFragment(
        matricula,
        token,
    )

    if (nav.currentDestination?.id == R.id.compraservidorFragment)
        nav.navigate(action)
}

// --------------------------------------- Cartão Page -------------------------------------- //
fun fromCartaoToDetalhesNovoCartao(
    fragment: Fragment,
    matricula: String,
    token: String,
) {

    val nav = fragment.findNavController()

    val action =
        CartaoDirections.actionServidorCartaoFragmentToSolicitacaoNovoCartaoDetalhes(
            matricula,
            token,
        )

    if (nav.currentDestination?.id == R.id.servidorCartaoFragment)
        nav.navigate(action)
}

fun fromCartaoToNovoCartaoDialog(
    fragment: Fragment,
    matricula: String,
    token: String,
) {

    val nav = fragment.findNavController()

    val action =
        CartaoDirections.actionServidorCartaoFragmentToDialogServidorNovoCartao(
            matricula,
            token,
        )

    if (nav.currentDestination?.id == R.id.servidorCartaoFragment)
        nav.navigate(action)
}

// ------------------------------------- Novo Cartão Dialog ---------------------------------- //
fun fromNovocartaoDialogToNovocartaoFragment(
    fragment: Fragment,
    matricula: String,
    token: String,
) {

    val nav = fragment.findNavController()

    val action =
        DialogServidorNovoCartaoDirections.actionDialogServidorNovoCartaoToSolicitacaoNovoCartaoDetalhes(
            matricula,
            token,
        )

    if (nav.currentDestination?.id == R.id.dialogServidorNovoCartao)
        nav.navigate(action)
}

// -------------------------------- Detalhes solicitação Cartão ------------------------------- //
fun fromSolicitacaodetalhesToCancelarsolicitacaoDialog(
    fragment: Fragment,
    matricula: String,
    token: String,
) {
    val action =
        CartaoSolicitacaoDetalhesDirections.actionServidorCartaoSolicitacaodetalhesFragmentToDialogServidorCartaoCacelarsolicitacao(
            matricula,
            token,
        )
    NavHostFragment.findNavController(fragment).navigate(action)
}

// ------------------------------------ Lista Comerciantes ----------------------------------- //
fun fromComercianteToDetalhes(fragment: Fragment, comerciante: ComercianteModel) {
    val action =
        ComerciantesDirections.actionServidorComerciantesFragmentToDialogServidorComerciantesdetalhes2(
            comerciante
        )

    NavHostFragment.findNavController(fragment).navigate(action)
}

// ---------------------------------------Extrato Page-------------------------------------- //
// Função que redireciona o usuario da página extrato servidor para a página detalhes transação
fun fromExtratoToDetalhes(fragment: Fragment, transacao: Transacao) {
    val action =
        ExtratoDirections.actionExtratoservidorFragmentToDialogServidorDetalhestransacao(
            transacao
        )

    NavHostFragment.findNavController(fragment).navigate(action)
}
package com.example.appcartaoservidorv1.services.redirecionamento

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.fragments.comerciantegerente.ComerciantegerenteFuncionarioFragmentDirections
import com.example.appcartaoservidorv1.fragments.comerciantegerente.ComerciantegerentehistoricovendasFragmentDirections
import com.example.appcartaoservidorv1.models.Transacao

// -------------------------------- Funcionarios ------------------------------------ //
fun fromFuncionariosToCriarDialog(
    fragment: Fragment,
    matricula: String,
    token: String
) {

    val nav = fragment.findNavController()

    val action =
        ComerciantegerenteFuncionarioFragmentDirections.actionComerciantegerenteFuncionarioFragmentToDialogComerciantegerenteFuncionarioCriar(
            matricula,
            token
        )

    if (nav.currentDestination?.id == R.id.comerciantegerenteFuncionarioFragment)
        nav.navigate(action)
}

// Função que abre o dialog de criar um gerente
fun fromFuncionariosToDeletarDialog(fragment: Fragment, matricula: String, token: String) {

    val nav = fragment.findNavController()

    val action =
        ComerciantegerenteFuncionarioFragmentDirections.actionComerciantegerenteFuncionarioFragmentToDialogComerciantegerenteFuncionarioDeletar(
            matricula,
            token
        )

    if (nav.currentDestination?.id == R.id.comerciantegerenteFuncionarioFragment)
        nav.navigate(action)
}


// --------------------------------Historico de Vendas Comerciante Gerente ------------------------------------ //
// Função que redireciona o usuario da página extrato servidor para a página detalhes transação
fun fromHistoricovendasGerenteComercianteToDetalhes(fragment: Fragment, transacao: Transacao) {
    val action =
        ComerciantegerentehistoricovendasFragmentDirections.actionComerciantegerentehistoricovendasFragmentToDialogComerciantegerenteDetalhestransacao(
            transacao
        )
    NavHostFragment.findNavController(fragment).navigate(action)
}
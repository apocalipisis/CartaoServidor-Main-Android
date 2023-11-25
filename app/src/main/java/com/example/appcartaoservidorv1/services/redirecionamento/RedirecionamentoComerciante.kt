package com.example.appcartaoservidorv1.services.redirecionamento

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.dialogs.comerciante.DialogComercianteFuncionariosDirections
import com.example.appcartaoservidorv1.fragments.comerciante.ComercianteDirections
import com.example.appcartaoservidorv1.fragments.comerciante.FuncionariosDirections
import com.example.appcartaoservidorv1.fragments.comerciante.GerentesDirections
import com.example.appcartaoservidorv1.fragments.comerciante.HistoricoVendasDirections
import com.example.appcartaoservidorv1.models.Transacao

// --------------------------------Home------------------------------------ //
fun fromComercianteToColaboradores(fragment: Fragment, matricula: String, token: String) {

    val nav = fragment.findNavController()

    val action =
        ComercianteDirections.actionComercianteFragmentToDialogComercianteFuncionarios(
            matricula,
            token
        )

    if (nav.currentDestination?.id == R.id.comercianteFragment)
        nav.navigate(action)
}

// --------------------------------Funcionarios------------------------------------ //
// Função que abre o dialog de criar um gerente
fun fromColaboradoresToGerente(fragment: Fragment, matricula: String, token: String) {

    val nav = fragment.findNavController()

    val action =
        DialogComercianteFuncionariosDirections.actionDialogComercianteFuncionariosToComercianteGerentes(
            matricula,
            token
        )

    if (nav.currentDestination?.id == R.id.dialogComercianteFuncionarios)
        nav.navigate(action)
}

// Função que abre o dialog de criar um gerente
fun fromColaboradoresToFuncionario(fragment: Fragment, matricula: String, token: String) {

    val nav = fragment.findNavController()

    val action =
        DialogComercianteFuncionariosDirections.actionDialogComercianteFuncionariosToComercianteFuncionarios(
            matricula,
            token
        )

    if (nav.currentDestination?.id == R.id.dialogComercianteFuncionarios)
        nav.navigate(action)
}

// --------------------------------Gerentes------------------------------------ //
// Função que abre o dialog de criar um gerente
fun fromGerentesToCriarGerente(fragment: Fragment, matricula: String, token: String) {

    val nav = fragment.findNavController()

    val action =
        GerentesDirections.actionComercianteGerentesToComercianteGerentesCriar(
            matricula,
            token
        )

    if (nav.currentDestination?.id == R.id.comerciante_gerentes)
        nav.navigate(action)
}

// Função que abre o dialog de criar um gerente
fun fromGerentesToDeletarGerente(fragment: Fragment, matricula: String, token: String) {

    val nav = fragment.findNavController()

    val action =
        GerentesDirections.actionComercianteGerentesToDialogDeletargerentecomerciante(
            matricula,
            token
        )

    if (nav.currentDestination?.id == R.id.comerciante_gerentes)
        nav.navigate(action)
}

// --------------------------------Funcionarios------------------------------------ //
// Função que abre o dialog de criar um gerente
fun fromFuncionariosToCriarFuncionario(fragment: Fragment, matricula: String, token: String) {

    val nav = fragment.findNavController()

    val action =
        FuncionariosDirections.actionFuncionariocomercianteFragmentToDialogCriarfuncionariocomerciante(
            matricula,
            token
        )

    if (nav.currentDestination?.id == R.id.comerciante_funcionarios)
        nav.navigate(action)
}

// Função que abre o dialog de criar um gerente
fun fromFuncionariosToDeletarFuncionario(fragment: Fragment, matricula: String, token: String) {

    val nav = fragment.findNavController()

    val action =
        FuncionariosDirections.actionFuncionariocomercianteFragmentToDialogDeletarfuncionariocomerciante(
            matricula,
            token
        )

    if (nav.currentDestination?.id == R.id.comerciante_funcionarios)
        nav.navigate(action)
}

// --------------------------------Historico Vendas Page------------------------------------ //
// Função que redireciona o usuario da página extrato servidor para a página detalhes transação
fun fromHistoricovendasToDetalhes(fragment: Fragment, transacao: Transacao) {

    val nav = fragment.findNavController()

    val action =
        HistoricoVendasDirections.actionHistoricovendasFragmentToDialogComercianteDetalhestransacao(
            transacao
        )

    if (nav.currentDestination?.id == R.id.historicovendasFragment)
        nav.navigate(action)
}

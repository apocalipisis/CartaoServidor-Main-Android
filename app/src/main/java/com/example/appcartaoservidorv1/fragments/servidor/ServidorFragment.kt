package com.example.appcartaoservidorv1.fragments.servidor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.FragmentServidorBinding
import com.example.appcartaoservidorv1.models.Transacao
import com.example.appcartaoservidorv1.viewmodels.servidor.ServidorViewModel
import com.example.appcartaoservidorv1.viewmodels.servidor.ServidorViewModelFactory

class ServidorFragment : Fragment() {
    // Variavel responsavel pelo binding
    lateinit var binding: FragmentServidorBinding

    // Variavel que representa o viewModel
    private lateinit var viewModel: ServidorViewModel
    private lateinit var viewModelFactory: ServidorViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla o layout do fragmento
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_servidor, container, false)
        // Recupera as variaveis passada para a view
        var args = ServidorFragmentArgs.fromBundle(requireArguments())
        // Configura o btn sair
        binding.btnSair.setOnClickListener { onSair() }
        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory = ServidorViewModelFactory(args.matricula, args.nome)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ServidorViewModel::class.java)
        // Faz o binding com o viewModel
        binding.viewModel = viewModel

        // ClickListener para o botão comprar
        binding.btnComprar.setOnClickListener {
            goToComprarPage(viewModel.response.matricula, args.nome)
//            Toast.makeText(context, "Btn comprar Clickado", Toast.LENGTH_LONG).show()
        }

        // ClickListener para o botão extrato
        binding.btnExtrato.setOnClickListener {
            goToExtratoPage(
                args.matricula,
                viewModel.response.transacaoList.toTypedArray()
            )
        }
        // ClickListener para o botão informações
        binding.btnInfo.setOnClickListener {
            goToInfoPage(
                viewModel.response.matricula,
                viewModel.response.nome,
                viewModel.response.cpf,
                viewModel.response.tipoUsuario,
                viewModel.response.status,
                viewModel.response.instituto,
                viewModel.response.limiteMensal
            )
        }

        // Coloca a barra de atualização como visivel
        viewModel.status.observe(
            viewLifecycleOwner,
            Observer<ServidorViewModel.ApiStatus> { status ->
                when (status) {
                    ServidorViewModel.ApiStatus.LOADING -> {
                        mostrarBarra()
                        escondeBtns()
                    }
                    ServidorViewModel.ApiStatus.DONE -> {
                        esconderBarra()
                        habilitaBtns()
                        escondeMensagem()
                        escondeBtnRefresh()
                    }
                    ServidorViewModel.ApiStatus.ERROR -> {
                        esconderBarra()
                        desabilitaBtns()
                        mostraMensagem()
                        mostraBtnRefresh()
                    }
                }
            })


        // Configura o ciclo de vida
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }


    // Função que executa o logout de um usuário
    private fun onSair() {
        val action = ServidorFragmentDirections.actionServidorFragmentToLoginFragment()
        NavHostFragment.findNavController(this).navigate(action)
    }

    // Função que redireciona o usuario para a pagina extrato
    private fun goToExtratoPage(matricula: String, list: Array<Transacao>) {
        val action =
            ServidorFragmentDirections.actionServidorFragmentToExtratoservidorFragment(
                matricula,
                list
            )
        NavHostFragment.findNavController(this).navigate(action)
    }

    // Função que redireciona o usuario para a pagina informações
    private fun goToInfoPage(
        matricula: String,
        nome: String,
        cpf: String,
        tipoUsuario: String,
        status: String,
        instituto: String,
        limiteMensal: Double
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
        NavHostFragment.findNavController(this).navigate(action)
    }

    // Função que redireciona o usuario para a pagina Comprar
    private fun goToComprarPage(
        matricula: String,
        nome: String
    ) {
        val action = ServidorFragmentDirections.actionServidorFragmentToCompraservidorFragment(
            matricula,
            nome
        )
        NavHostFragment.findNavController(this).navigate(action)
    }

    // Mostra a barra de loading e esconde os demais campos
    private fun mostrarBarra() {
        binding.Bar.visibility = View.VISIBLE
        binding.saldo.visibility = View.GONE
        binding.saldoLabel.visibility = View.GONE
    }

    // Esconde a barra de loading e mostra os demais campos
    private fun esconderBarra() {
        binding.Bar.visibility = View.GONE
        binding.saldo.visibility = View.VISIBLE
        binding.saldoLabel.visibility = View.VISIBLE
    }

    // Habilita os btns
    private fun habilitaBtns() {
        binding.btnExtrato.isEnabled = true
        binding.btnInfo.isEnabled = true
        binding.btnComprar.isEnabled = true
    }

    // Desabilita os btns
    private fun desabilitaBtns() {
        binding.btnExtrato.isEnabled = false
        binding.btnInfo.isEnabled = false
        binding.btnComprar.isEnabled = false
    }

    // Mostra mensagem sobre consulta a API
    private fun mostraMensagem() {
        binding.mensagem.visibility = View.VISIBLE
        binding.saldo.visibility = View.GONE
        binding.saldoLabel.visibility = View.GONE
        binding.saldoMes.visibility = View.GONE
    }

    // Esconde mensagem sobre consulta a API
    private fun escondeMensagem() {
        binding.mensagem.visibility = View.GONE
        binding.saldo.visibility = View.VISIBLE
        binding.saldoLabel.visibility = View.VISIBLE
        binding.saldoMes.visibility = View.VISIBLE
    }

    // Mostra btn refresh
    private fun mostraBtnRefresh() {
        binding.btnRefresh.visibility = View.VISIBLE
        binding.btnExtrato.visibility = View.GONE
        binding.btnInfo.visibility = View.GONE
        binding.btnComprar.visibility = View.GONE
    }

    // Esconde btn refresh
    private fun escondeBtnRefresh() {
        binding.btnRefresh.visibility = View.GONE
        binding.btnExtrato.visibility = View.VISIBLE
        binding.btnInfo.visibility = View.VISIBLE
        binding.btnComprar.visibility = View.VISIBLE
    }

    // Esconde todos os btn
    private fun escondeBtns() {
        binding.btnRefresh.visibility = View.GONE
        binding.btnExtrato.visibility = View.GONE
        binding.btnInfo.visibility = View.GONE
        binding.btnComprar.visibility = View.GONE
    }
}
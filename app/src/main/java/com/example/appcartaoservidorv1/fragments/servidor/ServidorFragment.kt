package com.example.appcartaoservidorv1.fragments.servidor

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.appcartaoservidorv1.*
import com.example.appcartaoservidorv1.databinding.FragmentServidorBinding
import com.example.appcartaoservidorv1.services.utilidades.*
import com.example.appcartaoservidorv1.viewmodels.servidor.ServidorViewModel
import com.example.appcartaoservidorv1.viewmodels.servidor.ServidorViewModelFactory

class ServidorFragment : BaseFragment() {
    // Variavel responsavel pelo binding
    lateinit var binding: FragmentServidorBinding

    // Variavel que representa o viewModel
    private lateinit var viewModel: ServidorViewModel
    private lateinit var viewModelFactory: ServidorViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_servidor, container, false)
        // Recupera as variaveis passada para a view
        val args = ServidorFragmentArgs.fromBundle(requireArguments())
        // Configura o btn sair
        binding.btnSair.setOnClickListener { fromServidorToLogin(this) }
        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory = ServidorViewModelFactory(args.matricula, args.nome, args.token)
        viewModel = ViewModelProvider(this, viewModelFactory)[ServidorViewModel::class.java]
        // Faz o binding com o viewModel
        binding.viewModel = viewModel


        // ClickListener para o botão comprar
        binding.btnComprar.setOnClickListener {
            fromServidorToComprar(this, viewModel.response.matricula, args.nome)
        }

        val appContext = this.requireContext()
        // ClickListener para o botão extrato
        binding.btnExtrato.setOnClickListener {
            if (isNetworkAvailable(appContext)) {
                fromServidorToExtrato(
                    this,
                    viewModel.matricula,
                    viewModel.token,
                )
            } else {
                goToNointernetpage(binding.root)
            }
        }

        // ClickListener para o botão informações
        binding.btnInfo.setOnClickListener {
            fromServidorToInfo(
                this,
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
            viewLifecycleOwner
        ) { status ->
            when (status) {
                ServidorViewModel.ApiStatus.LOADING -> {
                    estadoCarregando()
                }
                ServidorViewModel.ApiStatus.DONE -> {
                    estadoOk()
                }
                ServidorViewModel.ApiStatus.ERROR -> {
                    estadoErro()
                }
            }
        }

        Log.i("Token",viewModel.token)

        // Configura o ciclo de vida
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }



    // Configuração da View para quando tiver carregando a resposta
    private fun estadoCarregando() {
        // Views
        binding.Bar.visibility = View.VISIBLE
        binding.saldo.visibility = View.GONE
        binding.saldoLabel.visibility = View.GONE
        binding.saldoMes.visibility = View.GONE
        binding.mensagem.visibility = View.GONE

        // Btns
        binding.btnRefresh.visibility = View.GONE
        binding.btnExtrato.visibility = View.GONE
        binding.btnInfo.visibility = View.GONE
        binding.btnComprar.visibility = View.GONE
    }

    // Configuração da View para quando tiver erro na resposta
    private fun estadoErro() {
        // Views
        binding.Bar.visibility = View.GONE
        binding.saldo.visibility = View.GONE
        binding.saldoLabel.visibility = View.GONE
        binding.saldoMes.visibility = View.GONE
        binding.mensagem.visibility = View.VISIBLE

        // Btns
        binding.btnRefresh.visibility = View.VISIBLE
        binding.btnExtrato.visibility = View.GONE
        binding.btnInfo.visibility = View.GONE
        binding.btnComprar.visibility = View.GONE
    }

    // Configuração da View para quando tiver uma resposta OK
    private fun estadoOk() {
        // Views
        binding.Bar.visibility = View.GONE
        binding.saldo.visibility = View.VISIBLE
        binding.saldoLabel.visibility = View.VISIBLE
        binding.saldoMes.visibility = View.VISIBLE
        binding.mensagem.visibility = View.GONE

        // Btns
        binding.btnRefresh.visibility = View.GONE
        binding.btnExtrato.visibility = View.VISIBLE
        binding.btnInfo.visibility = View.VISIBLE
        binding.btnComprar.visibility = View.VISIBLE
    }
}
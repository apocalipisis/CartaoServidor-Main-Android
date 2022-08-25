package com.example.appcartaoservidorv1.fragments.comerciantegerente

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.FragmentComerciantegerenteBinding
import com.example.appcartaoservidorv1.services.utilidades.*
import com.example.appcartaoservidorv1.viewmodels.comerciantegerente.ComerciantegerenteViewModel
import com.example.appcartaoservidorv1.viewmodels.comerciantegerente.ComerciantegerenteViewModelFactory

class ComerciantegerenteFragment : BaseFragment() {

    // Variavel responsavel pelo binding
    lateinit var binding: FragmentComerciantegerenteBinding
    lateinit var args: ComerciantegerenteFragmentArgs

    // Variavel que representa o viewModel
    private lateinit var viewModel: ComerciantegerenteViewModel
    private lateinit var viewModelFactory: ComerciantegerenteViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_comerciantegerente,
            container,
            false
        )
        // Recupera as variaveis passada para a view
        args = ComerciantegerenteFragmentArgs.fromBundle(requireArguments())
        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory = ComerciantegerenteViewModelFactory(args.matricula, args.nome, args.token)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[ComerciantegerenteViewModel::class.java]
        // Faz o binding com o viewModel
        binding.viewModel = viewModel
        // Armazena o contexto em uma variavel
        val appContext = this.requireContext()

        // ClickListener para o botão adicionarVenda
        binding.btnVender.setOnClickListener {
            fromComerciantegerenteToInserirvalor(
                this,
                viewModel.response.matriculaComerciante,
                viewModel.response.nomeComerciante,
                viewModel.matricula,
                viewModel.nome,
                viewModel.token,
            )
        }

        // ClickListener para o botão Historico
        binding.btnHistorico.setOnClickListener {
            fromComerciantegerenteToHistoricovendas(
                this,
                viewModel.matricula,
                viewModel.token
            )
        }


        // ClickListener para o botão funcionarios
        binding.btnFuncionarios.setOnClickListener {
            fromComerciantegerenteToFuncionario(this, viewModel.matricula, viewModel.token)
        }

        // ClickListener para o botão informações
        binding.btnInfo.setOnClickListener {
            fromComerciantegereteToInfo(
                this,
                viewModel.response.nome,
                viewModel.response.matricula,
                viewModel.response.tipoUsuario,
                viewModel.response.status,
                viewModel.response.cpf,
                viewModel.response.matriculaComerciante,
                viewModel.response.cnpj
            )
        }

        // Configura o btn sair
        binding.btnSair.setOnClickListener { fromComerciantegerenteToLogin(this) }

        // Coloca a barra de atualização como visivel
        viewModel.status.observe(viewLifecycleOwner) { status ->
            when (status) {
                ComerciantegerenteViewModel.ApiStatus.LOADING -> {
                    estadoCarregando()
                }
                ComerciantegerenteViewModel.ApiStatus.DONE -> {
                    estadoOk()
                }
                ComerciantegerenteViewModel.ApiStatus.ERROR -> {
                    estadoErro()
                }
            }
        }

        // Configura o ciclo de vida
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.consultaComerciante(viewModel.matricula)
    }

    // Configuração da View para quando tiver carregando a resposta
    private fun estadoCarregando() {
        // Views
        binding.Bar.visibility = View.VISIBLE
        binding.faturamento.visibility = View.GONE
        binding.faturamentoLabel.visibility = View.GONE
        binding.saldoMes.visibility = View.GONE
        binding.mensagem.visibility = View.GONE

        // Btns
        binding.btnRefresh.visibility = View.GONE
        binding.btnHistorico.visibility = View.GONE
        binding.btnInfo.visibility = View.GONE
        binding.btnVender.visibility = View.GONE
    }

    // Configuração da View para quando tiver erro na resposta
    private fun estadoErro() {
        // Views
        binding.Bar.visibility = View.GONE
        binding.faturamento.visibility = View.GONE
        binding.faturamentoLabel.visibility = View.GONE
        binding.saldoMes.visibility = View.GONE
        binding.mensagem.visibility = View.VISIBLE

        // Btns
        binding.btnRefresh.visibility = View.VISIBLE
        binding.btnHistorico.visibility = View.GONE
        binding.btnInfo.visibility = View.GONE
        binding.btnVender.visibility = View.GONE
    }

    // Configuração da View para quando tiver uma resposta OK
    private fun estadoOk() {
        // Views
        binding.Bar.visibility = View.GONE
        binding.faturamento.visibility = View.VISIBLE
        binding.faturamentoLabel.visibility = View.VISIBLE
        binding.saldoMes.visibility = View.VISIBLE
        binding.mensagem.visibility = View.GONE

        // Btns
        binding.btnRefresh.visibility = View.GONE
        binding.btnHistorico.visibility = View.VISIBLE
        binding.btnInfo.visibility = View.VISIBLE
        binding.btnVender.visibility = View.VISIBLE
    }

}
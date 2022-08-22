package com.example.appcartaoservidorv1.fragments.comerciante

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.*
import com.example.appcartaoservidorv1.databinding.FragmentComercianteBinding
import com.example.appcartaoservidorv1.services.utilidades.*
import com.example.appcartaoservidorv1.viewmodels.comerciante.ComercianteViewModel
import com.example.appcartaoservidorv1.viewmodels.comerciante.ComercianteViewModelFactory


class ComercianteFragment : BaseFragment() {
    // Variavel responsavel pelo binding
    lateinit var binding: FragmentComercianteBinding
    lateinit var args: ComercianteFragmentArgs

    // Variavel que representa o viewModel
    private lateinit var viewModel: ComercianteViewModel
    private lateinit var viewModelFactory: ComercianteViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_comerciante, container, false)
        // Recupera as variaveis passada para a view
        args = ComercianteFragmentArgs.fromBundle(requireArguments())
        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory = ComercianteViewModelFactory(args.matricula, args.nome, args.token)
        viewModel = ViewModelProvider(this, viewModelFactory)[ComercianteViewModel::class.java]
        // Faz o binding com o viewModel
        binding.viewModel = viewModel
        // Armazena o contexto em uma variavel
        val appContext = this.requireContext()

        // ClickListener para o botão adicionarVenda
        binding.btnVender.setOnClickListener {
            if (isNetworkAvailable(appContext)) {
                fromComercianteToInserirvalor(
                    this,
                    viewModel.nome,
                    viewModel.matricula,
                    viewModel.token
                )
            } else {
                goToNointernetpage(binding.root)
            }
        }

        // ClickListener para o botão Historico
        binding.btnHistorico.setOnClickListener {
            if (isNetworkAvailable(appContext)) {
                fromComercianteToHistoricovendas(
                    this,
                    viewModel.matricula,
                    viewModel.token
                )
            } else {
                goToNointernetpage(binding.root)
            }
        }


        // ClickListener para o botão funcionarios
        binding.btnFuncionarios.setOnClickListener {
            if (isNetworkAvailable(appContext)) {
                fromComercianteToFuncionarios(this, args.matricula, args.token)
            } else {
                goToNointernetpage(binding.root)
            }
        }

        // ClickListener para o botão informações
        binding.btnInfo.setOnClickListener {
            fromComercianteToInfo(
                this,
                viewModel.response.matricula,
                viewModel.response.nome,
                viewModel.response.cnpj,
                viewModel.response.tipoUsuario,
                viewModel.response.status,
                viewModel.response.pagementoUsoDoSistema
            )
        }

        // Configura o btn sair
        binding.btnSair.setOnClickListener { fromComercianteToLogin(this) }



        // Coloca a barra de atualização como visivel
        viewModel.status.observe(viewLifecycleOwner) { status ->
            when (status) {
                ComercianteViewModel.ApiStatus.LOADING -> {
                    estadoCarregando()
                }
                ComercianteViewModel.ApiStatus.DONE -> {
                    estadoOk()
                }
                ComercianteViewModel.ApiStatus.ERROR -> {
                    estadoErro()
                }
            }
        }

        // Configura o ciclo de vida
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
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
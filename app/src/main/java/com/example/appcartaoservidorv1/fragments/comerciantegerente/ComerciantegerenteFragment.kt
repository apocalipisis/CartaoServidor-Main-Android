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
import com.google.android.material.color.MaterialColors

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
                viewModel.response.cnpj,
                viewModel.response.nomeComerciante,
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
    //----------------------------------------------------------------------------------------------
    private fun botoes(isVisible: Boolean) {
        if (isVisible) {
            binding.Botoes.visibility = View.VISIBLE
            binding.Vender.visibility = View.VISIBLE
        } else {
            binding.Botoes.visibility = View.GONE
            binding.Vender.visibility = View.GONE
        }
    }
    //----------------------------------------------------------------------------------------------
    private fun botaoRefresh(isVisible: Boolean) {
        if (isVisible) {
            binding.Refresh.visibility = View.VISIBLE
        } else {
            binding.Refresh.visibility = View.GONE
        }
    }
    //----------------------------------------------------------------------------------------------
    private fun mensagemFaturamento(isVisible: Boolean) {
        if (isVisible) {
            binding.MensagemFaturamento.visibility = View.VISIBLE
        } else {
            binding.MensagemFaturamento.visibility = View.GONE
        }
    }

    //----------------------------------------------------------------------------------------------
    private fun valores(isVisible: Boolean) {
        if (isVisible) {
            binding.Valores.visibility = View.VISIBLE
        } else {
            binding.Valores.visibility = View.GONE
        }
    }

    //----------------------------------------------------------------------------------------------
    private fun barra(isVisible: Boolean) {
        if (isVisible) {
            binding.Bar.visibility = View.VISIBLE
        } else {
            binding.Bar.visibility = View.GONE
        }
    }

    //----------------------------------------------------------------------------------------------
    private fun mensagemErro(isVisible: Boolean) {
        if (isVisible) {
            binding.MensagemErro.visibility = View.VISIBLE
        } else {
            binding.MensagemErro.visibility = View.GONE
        }
    }

    //----------------------------------------------------------------------------------------------
    private fun cardEstado(isErro: Boolean) {
        if (isErro) {
            binding.Card.setCardBackgroundColor(
                MaterialColors.getColor(
                    binding.root,
                    com.google.android.material.R.attr.colorError
                )
            )
        } else {
            binding.Card.setCardBackgroundColor(
                MaterialColors.getColor(
                    binding.root,
                    com.google.android.material.R.attr.colorSurface
                )
            )
        }
    }
    //----------------------------------------------------------------------------------------------

    // Configuração da View para quando tiver carregando a resposta
    private fun estadoCarregando() {
        cardEstado(false)

        botoes(false)
        botaoRefresh(false)
        mensagemFaturamento(false)
        valores(false)
        barra(true)
        mensagemErro(false)
    }

    // Configuração da View para quando tiver erro na resposta
    private fun estadoErro() {
        cardEstado(true)

        botoes(false)
        botaoRefresh(true)
        mensagemFaturamento(false)
        valores(false)
        barra(false)
        mensagemErro(true)
    }

    // Configuração da View para quando tiver uma resposta OK
    private fun estadoOk() {
        cardEstado(false)

        botoes(true)
        botaoRefresh(false)
        mensagemFaturamento(true)
        valores(true)
        barra(false)
        mensagemErro(false)
    }
}
package com.example.appcartaoservidorv1.fragments.servidor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.FragmentServidorBinding
import com.example.appcartaoservidorv1.services.redirecionamento.fromServidorToCartao
import com.example.appcartaoservidorv1.services.redirecionamento.fromServidorToComerciantes
import com.example.appcartaoservidorv1.services.redirecionamento.fromServidorToComprar
import com.example.appcartaoservidorv1.services.utilidades.BaseFragment
import com.example.appcartaoservidorv1.services.utilidades.fromServidorToExtrato
import com.example.appcartaoservidorv1.services.utilidades.fromServidorToInfo
import com.example.appcartaoservidorv1.services.utilidades.fromServidorToLogin
import com.example.appcartaoservidorv1.viewmodels.servidor.ServidorViewModel
import com.example.appcartaoservidorv1.viewmodels.servidor.ServidorViewModelFactory
import com.google.android.material.color.MaterialColors

class Servidor : BaseFragment() {
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
        val args = ServidorArgs.fromBundle(requireArguments())
        // Configura o btn sair
        binding.btnSair.setOnClickListener { fromServidorToLogin(this) }
        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory = ServidorViewModelFactory(args.matricula, args.nome, args.token)
        viewModel = ViewModelProvider(this, viewModelFactory)[ServidorViewModel::class.java]
        // Faz o binding com o viewModel
        binding.viewModel = viewModel


        // ClickListener para o botão comprar
        binding.btnComprar.setOnClickListener {
            fromServidorToComprar(
                this,
                viewModel.response.matricula,
                viewModel.nome,
                viewModel.response.statusCartao,
                viewModel.response.numeroCartao,
                viewModel.token,
            )
        }

        // ClickListener para o botão extrato
        binding.btnExtrato.setOnClickListener {
            fromServidorToExtrato(
                this,
                viewModel.matricula,
                viewModel.token,
            )
        }

        // ClickListener para o botão comercios
        binding.btnComercios.setOnClickListener {
            fromServidorToComerciantes(
                this,
                viewModel.token,
            )
        }

        // ClickListener para o botão cartão
        binding.btnCartao.setOnClickListener {
            fromServidorToCartao(
                this,
                viewModel.matricula,
                viewModel.token,
            )
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

        viewModel.consultaServidor(args.matricula)

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
                else -> {
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
        viewModel.consultaServidor(viewModel.matricula)
    }

    //----------------------------------------------------------------------------------------------
    private fun botoes(isVisible: Boolean) {
        if (isVisible) {
            binding.Botoes.visibility = View.VISIBLE
            binding.Comprar.visibility = View.VISIBLE
        } else {
            binding.Botoes.visibility = View.GONE
            binding.Comprar.visibility = View.GONE
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
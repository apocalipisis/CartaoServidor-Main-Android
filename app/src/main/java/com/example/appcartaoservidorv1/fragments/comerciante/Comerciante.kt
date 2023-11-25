package com.example.appcartaoservidorv1.fragments.comerciante

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.FragmentComercianteBinding
import com.example.appcartaoservidorv1.services.redirecionamento.fromComercianteToColaboradores
import com.example.appcartaoservidorv1.services.utilidades.*
import com.example.appcartaoservidorv1.viewmodels.comerciante.ComercianteViewModel
import com.example.appcartaoservidorv1.viewmodels.comerciante.ComercianteViewModelFactory
import com.google.android.material.color.MaterialColors


class Comerciante : BaseFragment() {
    // Variavel responsavel pelo binding
    lateinit var binding: FragmentComercianteBinding
    lateinit var args: ComercianteArgs

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
        args = ComercianteArgs.fromBundle(requireArguments())
        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory = ComercianteViewModelFactory(args.matricula, args.nome, args.token)
        viewModel = ViewModelProvider(this, viewModelFactory)[ComercianteViewModel::class.java]
        // Faz o binding com o viewModel
        binding.viewModel = viewModel

        // ClickListener para o botão adicionarVenda
        binding.btnVender.setOnClickListener {
            fromComercianteToInserirvalor(
                this,
                viewModel.nome,
                viewModel.matricula,
                viewModel.token
            )
        }

        // ClickListener para o botão Historico
        binding.btnHistorico.setOnClickListener {
            fromComercianteToHistoricovendas(
                this,
                viewModel.matricula,
                viewModel.token
            )
        }

        // ClickListener para o botão funcionarios
        binding.btnFuncionarios.setOnClickListener {
//            Log.i("Teste This.currentDestination", this.findNavController().currentDestination?.id.toString())
//            Log.i("Teste This.id", this.id.toString())
            fromComercianteToColaboradores(this, viewModel.matricula, viewModel.token)
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
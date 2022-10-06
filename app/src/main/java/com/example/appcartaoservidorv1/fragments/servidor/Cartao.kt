package com.example.appcartaoservidorv1.fragments.servidor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.FragmentServidorCartaoBinding
import com.example.appcartaoservidorv1.services.redirecionamento.fromCartaoToDetalhesNovoCartao
import com.example.appcartaoservidorv1.services.redirecionamento.fromCartaoToNovoCartaoDialog
import com.example.appcartaoservidorv1.services.utilidades.BaseFragment
import com.example.appcartaoservidorv1.viewmodels.servidor.ServidorCartaoViewModel
import com.example.appcartaoservidorv1.viewmodels.servidor.ServidorCartaoViewModelFactory
import com.google.android.material.color.MaterialColors

class Cartao : BaseFragment() {

    lateinit var binding: FragmentServidorCartaoBinding
    lateinit var args: CartaoArgs

    private lateinit var viewModel: ServidorCartaoViewModel
    private lateinit var viewModelFactory: ServidorCartaoViewModelFactory
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_servidor_cartao, container, false)
        // Recupera as variaveis passada para a view
        args = CartaoArgs.fromBundle(
            requireArguments()
        )
        // Inicializa as variaveis do ViewModel
        viewModelFactory = ServidorCartaoViewModelFactory(args.matricula, args.token)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[ServidorCartaoViewModel::class.java]
        // Faz o binding com o viewModel
        binding.viewModel = viewModel

        // ClickListener para o botão Bloquear
//        binding.btnBloquear.setOnClickListener {
//            viewModel.bloquearCartao()
//        }

        // ClickListener para o botão Desbloquear
        binding.btnBloquear.setOnClickListener {
            if (viewModel.cartao.value?.status == "Ativo") {
                viewModel.bloquearCartao()
            } else {
                viewModel.desbloquearCartao()
            }
        }

        // ClickListener para o botão novo cartão
        binding.btnNovoCartao.setOnClickListener {
            fromCartaoToNovoCartaoDialog(this, viewModel.matricula, viewModel.token)
        }

        // ClickListener para o botão detalhes
        binding.btnDetalhes.setOnClickListener {
            fromCartaoToDetalhesNovoCartao(this, viewModel.matricula, viewModel.token)
        }
        //------------------------------------------------------------------------------------------
        // Mostra o QRCode
        viewModel.qrC.observe(viewLifecycleOwner) {
            it?.let {
                binding.qRCode.setImageBitmap(it)
            }
        }

        viewModel.haPendenciasCartao.observe(viewLifecycleOwner) {
            if (it) {
                novoCartao(true)
                btnNovoCartaoContainer(false)
            } else {
                novoCartao(false)
                btnNovoCartaoContainer(true)
            }
        }

        viewModel.cartao.observe(viewLifecycleOwner) {
            if (it.status == "Ativo") {
                btnBloquearIconLock()
            } else {
                btnBloquearIconLockOpen()
            }
        }

        //------------------------------------------------------------------------------------------
        // Monitora o estado da consulta a API para os dados do cartão
        viewModel.statusRequest.observe(viewLifecycleOwner) { status ->
            when (status) {
                ServidorCartaoViewModel.StatusRequest.LOADING -> {
                    estadoCarregando()
                }
                ServidorCartaoViewModel.StatusRequest.DONE -> {
                    estadoOk()
                }
                else -> {
                    estadoErro()
                }
            }
        }
        //------------------------------------------------------------------------------------------

        // Monitora o estado da consulta a API para bloquear o cartão
        viewModel.statusBloquearCartao.observe(viewLifecycleOwner) { status ->
            when (status) {
                ServidorCartaoViewModel.ApiStatusBloquearCartao.LOADING -> {
                    barLinear(true)
                }
                ServidorCartaoViewModel.ApiStatusBloquearCartao.DONE -> {
                    barLinear(false)
                    if (viewModel.responseBloquearCartao) {
                        viewModel.setStatusCartao("Inativo")
                    }
                }
                else -> {
                    barLinear(false)
                }
            }
        }
        //------------------------------------------------------------------------------------------

        // Monitora o estado da consulta a API para desbloquear o cartão
        viewModel.statusDesbloquearCartao.observe(viewLifecycleOwner) { status ->
            when (status) {
                ServidorCartaoViewModel.ApiStatusDesbloquearCartao.LOADING -> {
                    barLinear(true)
                }
                ServidorCartaoViewModel.ApiStatusDesbloquearCartao.DONE -> {
                    barLinear(false)
                    if (viewModel.responseDesbloquearCartao) {
                        viewModel.setStatusCartao("Ativo")
                    }
                }
                else -> {
                    barLinear(false)
                }
            }
        }
        //------------------------------------------------------------------------------------------

        // Botão Voltar
        binding.btnVoltar.setOnClickListener {
            findNavController().navigateUp()
        }

        // Configura o ciclo de vida
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.consultarCartao()
    }

    //----------------------------------------------------------------------------------------------
    private fun cardError(isErro: Boolean) {
        val colorError = MaterialColors.getColor(
            binding.root, com.google.android.material.R.attr.colorError
        )
        val colorSurface = MaterialColors.getColor(
            binding.root, com.google.android.material.R.attr.colorSurface
        )

        if (isErro) {
            binding.card.setCardBackgroundColor(colorError)
        } else {
            binding.card.setCardBackgroundColor(colorSurface)
        }
    }

    //----------------------------------------------------------------------------------------------
    private fun bar(isVisible: Boolean) {
        if (isVisible) {
            binding.bar.visibility = View.VISIBLE
        } else {
            binding.bar.visibility = View.GONE
        }
    }

    //----------------------------------------------------------------------------------------------
    private fun qRCode(isVisible: Boolean) {
        if (isVisible) {
            binding.qRCode.visibility = View.VISIBLE
        } else {
            binding.qRCode.visibility = View.GONE
        }
    }

    //----------------------------------------------------------------------------------------------
    private fun mensagem(isVisible: Boolean) {
        if (isVisible) {
            binding.mensagem.visibility = View.VISIBLE
        } else {
            binding.mensagem.visibility = View.GONE
        }
    }

    //----------------------------------------------------------------------------------------------
    private fun info(isVisible: Boolean) {
        if (isVisible) {
            binding.info.visibility = View.VISIBLE
        } else {
            binding.info.visibility = View.GONE
        }
    }

    //----------------------------------------------------------------------------------------------
    private fun novoCartao(isVisible: Boolean) {
        if (isVisible) {
            binding.novoCartao.visibility = View.VISIBLE
        } else {
            binding.novoCartao.visibility = View.GONE
        }
    }

    //----------------------------------------------------------------------------------------------
    private fun btnNovoCartaoContainer(isVisible: Boolean) {
        if (isVisible) {
            binding.btnNovoCartaoContainer.visibility = View.VISIBLE
        } else {
            binding.btnNovoCartaoContainer.visibility = View.GONE
        }
    }

    //----------------------------------------------------------------------------------------------
    private fun barLinear(isVisible: Boolean) {
        if (isVisible) {
            binding.barLinear.visibility = View.VISIBLE
        } else {
            binding.barLinear.visibility = View.INVISIBLE
        }
    }

    //----------------------------------------------------------------------------------------------
    private fun btnBloquearIconLock() {
        val icon = R.drawable.ic_outline_lock_24
        val context = binding.btnBloquear.context
        binding.btnBloquear.icon = AppCompatResources.getDrawable(context, icon)
        binding.btnBloquearText.text = "Bloquear"
    }

    //----------------------------------------------------------------------------------------------
    private fun btnBloquearIconLockOpen() {
        val icon = R.drawable.ic_outline_lock_open_24
        val context = binding.btnBloquear.context
        binding.btnBloquear.icon = AppCompatResources.getDrawable(context, icon)
        binding.btnBloquearText.text = "Desbloquear"

    }
    //----------------------------------------------------------------------------------------------

    private fun estadoCarregando() {
        bar(true)
        qRCode(false)
        mensagem(false)
        cardError(false)
        info(false)
    }

    private fun estadoOk() {
        bar(false)
        qRCode(true)
        mensagem(false)
        cardError(false)
        info(true)
    }

    private fun estadoErro() {
        bar(false)
        qRCode(false)
        mensagem(true)
        cardError(true)
        info(false)
    }
}
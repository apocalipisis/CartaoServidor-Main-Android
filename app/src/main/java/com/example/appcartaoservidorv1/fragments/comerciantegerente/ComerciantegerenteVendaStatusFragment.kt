package com.example.appcartaoservidorv1.fragments.comerciantegerente

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.FragmentComerciantegerenteVendaStatusBinding
import com.example.appcartaoservidorv1.services.utilidades.*
import com.example.appcartaoservidorv1.viewmodels.comerciantegerente.ComerciantegerenteVendaStatusViewModel
import com.example.appcartaoservidorv1.viewmodels.comerciantegerente.ComerciantegerenteVendaStatusViewModelFactory

class ComerciantegerenteVendaStatusFragment : BaseFragment() {
    // Variavel responsavel pelo binding
    lateinit var binding: FragmentComerciantegerenteVendaStatusBinding
    lateinit var args: ComerciantegerenteVendaStatusFragmentArgs

    // Variavel que representa o viewModel
    private lateinit var viewModel: ComerciantegerenteVendaStatusViewModel
    private lateinit var viewModelFactory: ComerciantegerenteVendaStatusViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_comerciantegerente_venda_status,
                container,
                false
            )
        // Recupera as variaveis passada para a view
        args = ComerciantegerenteVendaStatusFragmentArgs.fromBundle(requireArguments())
        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory = ComerciantegerenteVendaStatusViewModelFactory(
            args.matriculaCliente,
            args.matriculaComerciante,
            args.matriculaVendedor,
            args.valor,
            args.senha,
            args.nomeVendedor,
            args.numeroCartao,
            args.token,
        )
        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[ComerciantegerenteVendaStatusViewModel::class.java]
        // Faz o binding com o viewModel
        binding.viewModel = viewModel

        // Armazena o fragment em uma variavel
        val fragment = this

        binding.btnHome.setOnClickListener {
            fromComerciantegerenteVendaStatusToComerciantegerente(
                fragment,
                viewModel.matriculaVendedor,
                viewModel.nomeVendedor,
                viewModel.token
            )
        }

        // Botão Voltar
        binding.btnVoltar.setOnClickListener {
            fromComerciantegerenteVendaStatusToComerciantegerente(
                fragment,
                viewModel.matriculaVendedor,
                viewModel.nomeVendedor,
                viewModel.token
            )
        }

        // Coloca a barra de atualização como visivel
        viewModel.status.observe(viewLifecycleOwner) { status ->
            when (status) {
                ComerciantegerenteVendaStatusViewModel.ApiStatus.LOADING -> {
                    estadoCarregando()
                }
                ComerciantegerenteVendaStatusViewModel.ApiStatus.DONE -> {
                    if (viewModel.response.b) {
                        estadoOk()
                        imageSucess()
                    } else {
                        estadoOk()
                        imageFail()
                    }
                }
                else -> {
                    estadoErro()
                }
            }
        }

        // Configura o botão de voltar para ao pressionar voltar para a página login
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                fromComerciantegerenteVendaStatusToComerciantegerente(
                    fragment,
                    viewModel.matriculaVendedor,
                    viewModel.nomeVendedor,
                    viewModel.token
                )

            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback
        )

        // Configura o ciclo de vida
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
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
    private fun resultados() {
        binding.Resultados.visibility = View.VISIBLE
    }

    //----------------------------------------------------------------------------------------------
    private fun imageSucess() {
        binding.iconSucesso.visibility = View.VISIBLE
    }

    //----------------------------------------------------------------------------------------------
    private fun imageFail() {
        binding.iconError.visibility = View.VISIBLE
    }

    //----------------------------------------------------------------------------------------------
    private fun estadoCarregando() {
        barra(true)
    }

    private fun estadoOk() {
        barra(false)
        resultados()
    }

    private fun estadoErro() {
        resultados()
        imageFail()
        barra(false)
    }
}
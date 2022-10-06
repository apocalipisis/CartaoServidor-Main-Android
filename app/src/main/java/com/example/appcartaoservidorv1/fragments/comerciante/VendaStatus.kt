package com.example.appcartaoservidorv1.fragments.comerciante

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.FragmentComercianteVendaStatusBinding
import com.example.appcartaoservidorv1.services.utilidades.BaseFragment
import com.example.appcartaoservidorv1.services.utilidades.fromStatusvendaToComerciante
import com.example.appcartaoservidorv1.viewmodels.comerciante.StatusvendaViewModel
import com.example.appcartaoservidorv1.viewmodels.comerciante.StatusvendaViewModelFactory

class VendaStatus : BaseFragment() {
    // Variavel responsavel pelo binding
    lateinit var binding: FragmentComercianteVendaStatusBinding
    lateinit var args: VendaStatusArgs

    // Variavel que representa o viewModel
    private lateinit var viewModel: StatusvendaViewModel
    private lateinit var viewModelFactory: StatusvendaViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_comerciante_venda_status,
                container,
                false
            )
        // Recupera as variaveis passada para a view
        args = VendaStatusArgs.fromBundle(requireArguments())
        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory = StatusvendaViewModelFactory(
            args.valor,
            args.matricula,
            args.matriculaComerciante,
            args.senha,
            args.numeroCartao,
            args.token,
        )
        viewModel = ViewModelProvider(this, viewModelFactory)[StatusvendaViewModel::class.java]
        // Faz o binding com o viewModel
        binding.viewModel = viewModel

        // Armazena o fragment em uma variavel
        val fragment = this

        binding.btnHome.setOnClickListener {
            fromStatusvendaToComerciante(
                fragment,
                args.nomeComerciante,
                viewModel.matriculaComerciante,
                viewModel.token
            )
        }

        // Botão Voltar
        binding.btnVoltar.setOnClickListener {
            fromStatusvendaToComerciante(
                fragment,
                args.nomeComerciante,
                viewModel.matriculaComerciante,
                viewModel.token
            )
        }

        // Coloca a barra de atualização como visivel
        viewModel.status.observe(viewLifecycleOwner) { status ->
            when (status) {
                StatusvendaViewModel.ApiStatus.LOADING -> {
                    estadoCarregando()
                }
                StatusvendaViewModel.ApiStatus.DONE -> {
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
                fromStatusvendaToComerciante(
                    fragment,
                    args.nomeComerciante,
                    viewModel.matriculaComerciante,
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
package com.example.appcartaoservidorv1.fragments.comerciante

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.*
import com.example.appcartaoservidorv1.databinding.FragmentStatusvendaBinding
import com.example.appcartaoservidorv1.viewmodels.comerciante.StatusvendaViewModel
import com.example.appcartaoservidorv1.viewmodels.comerciante.StatusvendaViewModelFactory

class StatusvendaFragment : Fragment() {
    // Variavel responsavel pelo binding
    lateinit var binding: FragmentStatusvendaBinding
    lateinit var args: StatusvendaFragmentArgs

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
            DataBindingUtil.inflate(inflater, R.layout.fragment_statusvenda, container, false)
        // Recupera as variaveis passada para a view
        args = StatusvendaFragmentArgs.fromBundle(requireArguments())
        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory = StatusvendaViewModelFactory(
            args.valor,
            args.matricula,
            args.matriculaComerciante,
            args.senha,
            args.token,
        )
        viewModel = ViewModelProvider(this, viewModelFactory)[StatusvendaViewModel::class.java]
        // Faz o binding com o viewModel
        binding.viewModel = viewModel

        // Armazena o fragment em uma variavel
        val fragment = this
        // Armazena o contexto em uma variavel
        val appContext = this.requireContext()

        binding.btnHome.setOnClickListener {
            if (isNetworkAvailable(appContext)) {
                fromStatusvendaToComerciante(
                    fragment,
                    args.nomeComerciante,
                    viewModel.matriculaComerciante,
                    viewModel.token
                )
            } else {
                goToNointernetstatusvendapage(
                    fragment,
                    args.nomeComerciante,
                    viewModel.matriculaComerciante,
                    viewModel.token
                )
            }
        }

        // Coloca a barra de atualização como visivel
        viewModel.status.observe(viewLifecycleOwner) { status ->
            when (status) {
                StatusvendaViewModel.ApiStatus.LOADING -> {
                    mostrarBarra()
                }
                StatusvendaViewModel.ApiStatus.DONE -> {
                    esconderBarra()
                    if (viewModel.sucess.value == true) {
                        sucessAnimation()

                    } else {
                        failAnimation()
                    }
                }
                StatusvendaViewModel.ApiStatus.ERROR -> {
                    esconderBarra()
                    failAnimation()
                }
            }
        }

        // Configura o botão de voltar para ao pressionar voltar para a página login
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isNetworkAvailable(appContext)) {
                    fromStatusvendaToComerciante(
                        fragment,
                        args.nomeComerciante,
                        viewModel.matriculaComerciante,
                        viewModel.token
                    )
                } else {
                    goToNointernetstatusvendapage(
                        fragment,
                        args.nomeComerciante,
                        viewModel.matriculaComerciante,
                        viewModel.token
                    )
                }
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

    // Mostra a barra de loading
    private fun mostrarBarra() {
        binding.Bar.visibility = View.VISIBLE
    }

    // Esconde a barra de loading
    private fun esconderBarra() {
        binding.Bar.visibility = View.GONE
    }

    // Mostra a animação de sucesso
    private fun sucessAnimation() {
        binding.ImageStatusSucess.visibility = View.VISIBLE
        binding.ImageStatusSucess.animate().apply {
            duration = 1000
            rotationYBy(360f)
        }.withEndAction {
            binding.result.visibility = View.VISIBLE
            binding.btnHome.visibility = View.VISIBLE
        }
    }

    private fun failAnimation() {
        binding.ImageStatusError.visibility = View.VISIBLE
        binding.ImageStatusError.animate().apply {
            duration = 1000
            rotationYBy(360f)
        }.withEndAction {
            binding.result.visibility = View.VISIBLE
            binding.motivo.visibility = View.VISIBLE
            binding.btnHome.visibility = View.VISIBLE
        }
    }
}
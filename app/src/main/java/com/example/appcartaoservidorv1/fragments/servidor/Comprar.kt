package com.example.appcartaoservidorv1.fragments.servidor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.FragmentServidorComprarBinding
import com.example.appcartaoservidorv1.services.redirecionamento.fromComprarToCartao
import com.example.appcartaoservidorv1.services.utilidades.BaseFragment
import com.example.appcartaoservidorv1.viewmodels.servidor.CompraservidorViewModel
import com.example.appcartaoservidorv1.viewmodels.servidor.CompraservidorViewModelFactory

class Comprar : BaseFragment() {
    // Variavel responsavel pelo binding
    lateinit var binding: FragmentServidorComprarBinding
    lateinit var args: ComprarArgs

    // Variavel que representa o viewModel
    private lateinit var viewModel: CompraservidorViewModel
    private lateinit var viewModelFactory: CompraservidorViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_servidor_comprar, container, false)
        // Recupera as variaveis passada para a view
        args = ComprarArgs.fromBundle(requireArguments())
        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory = CompraservidorViewModelFactory(
            args.matricula,
            args.nome,
            args.statusCartao,
            args.numeroCartao,
            args.token
        )
        viewModel = ViewModelProvider(this, viewModelFactory)[CompraservidorViewModel::class.java]
        // Faz o binding com o viewModel
        binding.viewModel = viewModel

        // Mostra o QRCode
        viewModel.qrC.observe(viewLifecycleOwner) {
            it?.let {
                binding.QRImage.setImageBitmap(it)
            }
        }


        // Coloca a barra de atualização como visivel
        viewModel.status.observe(viewLifecycleOwner) { status ->
            when (status) {
                CompraservidorViewModel.Status.DONE -> {
                    mensagem1(true)
                    mensagem2(false)
                }
                CompraservidorViewModel.Status.ERROR -> {
                    mensagem1(false)
                    mensagem2(true)
                }
                else -> {
                    mensagem1(false)
                    mensagem2(true)
                }
            }
        }


        binding.btnCartao.setOnClickListener {
            fromComprarToCartao(this, viewModel.matricula, viewModel.token)
        }

        binding.btnVoltar.setOnClickListener {
            findNavController().navigateUp()
        }

        // Configura o ciclo de vida
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    private fun mensagem1(isVisible: Boolean) {
        if (isVisible) {
            binding.mensagem1.visibility = View.VISIBLE
        } else {
            binding.mensagem1.visibility = View.GONE
        }
    }

    private fun mensagem2(isVisible: Boolean) {
        if (isVisible) {
            binding.mensagem2.visibility = View.VISIBLE
        } else {
            binding.mensagem2.visibility = View.GONE
        }
    }
}
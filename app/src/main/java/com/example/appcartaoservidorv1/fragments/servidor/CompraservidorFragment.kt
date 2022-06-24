package com.example.appcartaoservidorv1.fragments.servidor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.FragmentCompraservidorBinding
import com.example.appcartaoservidorv1.viewmodels.servidor.CompraservidorViewModel
import com.example.appcartaoservidorv1.viewmodels.servidor.CompraservidorViewModelFactory

class CompraservidorFragment : Fragment() {
    // Variavel responsavel pelo binding
    lateinit var binding: FragmentCompraservidorBinding
    lateinit var args: CompraservidorFragmentArgs

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
            DataBindingUtil.inflate(inflater, R.layout.fragment_compraservidor, container, false)
        // Recupera as variaveis passada para a view
        args = CompraservidorFragmentArgs.fromBundle(requireArguments())
        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory = CompraservidorViewModelFactory(args.matricula, args.nome)
        viewModel = ViewModelProvider(this, viewModelFactory)[CompraservidorViewModel::class.java]
        // Faz o binding com o viewModel
        binding.viewModel = viewModel
        // Coloca um observer no QRCode e coloca na tela
        viewModel.qrC.observe(viewLifecycleOwner) {
            it?.let {
                binding.QRImage.setImageBitmap(it)
            }
        }
        // Coloca um observer na mensagem de erro e coloca ela na tela
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            it?.let {
                binding.Mensagem.text = it
            }
        }
        // Configura o ciclo de vida
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
}
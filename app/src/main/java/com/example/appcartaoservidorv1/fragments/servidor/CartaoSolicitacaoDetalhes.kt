package com.example.appcartaoservidorv1.fragments.servidor

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.FragmentServidorCartaoSolicitacaodetalhesBinding
import com.example.appcartaoservidorv1.services.redirecionamento.fromSolicitacaodetalhesToCancelarsolicitacaoDialog
import com.example.appcartaoservidorv1.services.utilidades.BaseFragment
import com.example.appcartaoservidorv1.viewmodels.servidor.ServidorCartaoSolicitacaodetalhesViewModel
import com.example.appcartaoservidorv1.viewmodels.servidor.ServidorCartaoSolicitacaodetalhesViewModelFactory

class CartaoSolicitacaoDetalhes : BaseFragment() {

    lateinit var binding: FragmentServidorCartaoSolicitacaodetalhesBinding
    lateinit var args: CartaoSolicitacaoDetalhesArgs

    private lateinit var viewModel: ServidorCartaoSolicitacaodetalhesViewModel
    private lateinit var viewModelFactory: ServidorCartaoSolicitacaodetalhesViewModelFactory
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_servidor_cartao_solicitacaodetalhes,
                container,
                false
            )
        // Recupera as variaveis passada para a view
        args = CartaoSolicitacaoDetalhesArgs.fromBundle(
            requireArguments()
        )
        // Inicializa as variaveis do ViewModel
        viewModelFactory =
            ServidorCartaoSolicitacaodetalhesViewModelFactory(args.matricula, args.token)
        viewModel =
            ViewModelProvider(
                this,
                viewModelFactory
            )[ServidorCartaoSolicitacaodetalhesViewModel::class.java]
        // Faz o binding com o viewModel
        binding.viewModel = viewModel

        // ClickListener para o botão pagar depois
        binding.btnVoltar.setOnClickListener {
            findNavController().navigateUp()
        }

        // ClickListener para o botão de cancelar a solicitação
        binding.btnCancelar.setOnClickListener {
            fromSolicitacaodetalhesToCancelarsolicitacaoDialog(
                this,
                viewModel.matricula,
                viewModel.token
            )
        }

        // Escuta se o dialog de deletar foi fechado
        setFragmentResultListener("dialogDeletarSolicitacaoNovoCartao") { _, bundle ->
            val solicitacaoDeletada = bundle.getBoolean("bundleDialogDeletarSolicitacaoNovoCartao")
            Log.i("Teste", "Escutando o Bundle")
            if (solicitacaoDeletada) {
                Log.i("Teste", "Navegando uma pagina atras")
                findNavController().navigateUp()
                findNavController().navigateUp()
            }
        }

        // Configura o ciclo de vida
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

}
package com.example.appcartaoservidorv1.fragments.servidor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.appcartaoservidorv1.adapters.ExtratoservidorAdapter
import com.example.appcartaoservidorv1.adapters.Transacao_Listener
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.FragmentExtratoservidorBinding
import com.example.appcartaoservidorv1.viewmodels.servidor.ExtratoservidorViewModel
import com.example.appcartaoservidorv1.viewmodels.servidor.ExtratoservidorViewModelFactory

class ExtratoservidorFragment : Fragment() {

    // Variavel responsavel pelo binding
    lateinit var binding: FragmentExtratoservidorBinding
    lateinit var args: ExtratoservidorFragmentArgs

    // Variavel que representa o viewModel
    private lateinit var viewModel: ExtratoservidorViewModel
    private lateinit var viewModelFactory: ExtratoservidorViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla o layout do fragmento
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_extratoservidor, container, false)
        // Recupera as variaveis passada para a view
        args = ExtratoservidorFragmentArgs.fromBundle(requireArguments())
        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory = ExtratoservidorViewModelFactory(args.matricula, args.list)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(ExtratoservidorViewModel::class.java)
        // Faz o binding com o viewModel
        binding.viewModel = viewModel
        // Lida com a pesquisa
//        binding.Pesquisa.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                TODO("Not yet implemented")
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                TODO("Not yet implemented")
//            }
//
//        })

        // Chama o adapter
        val adapter = ExtratoservidorAdapter(Transacao_Listener { transacao ->
            viewModel.onTransacaoClicked(transacao)
        })
        binding.ListaExtrato.adapter = adapter

        // Coloca um observer nas transações e atualiza o recycler view
        viewModel.transacoes.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.addHeaderAndSubmitList(it)
            }
        })
        // Coloca um observer para navegar para a página de detalhes
        viewModel.navigateToTransacaoDetail.observe(viewLifecycleOwner, Observer { transacao ->
            transacao?.let {
                this.findNavController().navigate(
                    ExtratoservidorFragmentDirections.actionExtratoservidorFragmentToTransacaodetalhesFragment(
                        transacao
                    )
                )
                viewModel.onTransacaoDetailNavigated()
            }
        })

//        // Coloca a barra de atualização como visivel
//        viewModel.status.observe(
//            viewLifecycleOwner,
//            Observer<ServidorViewModel.ApiStatus> { status ->
//                when (status) {
//                    ServidorViewModel.ApiStatus.LOADING -> {
//                        Toast.makeText(context, "Consultando", Toast.LENGTH_LONG).show()
//                    }
//                    ServidorViewModel.ApiStatus.DONE -> {
//                        Toast.makeText(context, "Consulta terminada", Toast.LENGTH_LONG).show()
//
//                    }
//                    ServidorViewModel.ApiStatus.ERROR -> {
//                        Toast.makeText(context, "Erro", Toast.LENGTH_LONG).show()
//
//                    }
//                }
//            })

        // Configura o ciclo de vida
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
}
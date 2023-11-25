package com.example.appcartaoservidorv1.fragments.servidor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.adapters.ExtratoservidorAdapter
import com.example.appcartaoservidorv1.adapters.Transacao_Listener
import com.example.appcartaoservidorv1.databinding.FragmentServidorExtratoBinding
import com.example.appcartaoservidorv1.services.redirecionamento.fromExtratoToDetalhes
import com.example.appcartaoservidorv1.services.utilidades.BaseFragment
import com.example.appcartaoservidorv1.viewmodels.servidor.ExtratoservidorViewModel
import com.example.appcartaoservidorv1.viewmodels.servidor.ExtratoservidorViewModelFactory

class Extrato : BaseFragment() {

    // Variavel responsavel pelo binding
    lateinit var binding: FragmentServidorExtratoBinding
    lateinit var args: ExtratoArgs

    // Variavel que representa o viewModel
    private lateinit var viewModel: ExtratoservidorViewModel
    private lateinit var viewModelFactory: ExtratoservidorViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_servidor_extrato, container, false)
        // Recupera as variaveis passada para a view
        args = ExtratoArgs.fromBundle(requireArguments())
        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory = ExtratoservidorViewModelFactory(args.matricula, args.token)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[ExtratoservidorViewModel::class.java]
        // Faz o binding com o viewModel
        binding.viewModel = viewModel
        // Chama o adapter
        val adapter = ExtratoservidorAdapter(Transacao_Listener { transacao ->
            viewModel.onTransacaoClicked(transacao)
        })
        binding.Lista.adapter = adapter

        // Coloca um observer nas transações e atualiza o recycler view
        viewModel.transacoes.observe(viewLifecycleOwner) {
            adapter.addHeaderAndSubmitList(it)
        }
        // Coloca um observer para navegar para a página de detalhes
        viewModel.navigateToTransacaoDetail.observe(viewLifecycleOwner) { transacao ->
            transacao?.let {
                fromExtratoToDetalhes(this, transacao)
                viewModel.onTransacaoDetailNavigated()
            }
        }

        val layoutManager = LinearLayoutManager(this.requireContext())
        binding.Lista.layoutManager = layoutManager
        // Adi
        binding.Lista.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) { //check for scroll down
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val pastVisiblesItems = layoutManager.findFirstVisibleItemPosition()
                    if (!viewModel.loading) {
                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                            viewModel.loading = true
                            viewModel.consultaExtrato(true)
                        }
                    }
                }
            }
        })

        // Coloca a barra de atualização como visivel
        viewModel.statusLista.observe(viewLifecycleOwner) { status ->
            when (status) {
                ExtratoservidorViewModel.ApiStatusLista.LOADING -> {
                    estadoCarregandoLista()
                }
                ExtratoservidorViewModel.ApiStatusLista.DONE -> {
                    estadoOkLista()
                }
                else -> {
                    estadoErro()
                }
            }
        }

        // Coloca a barra de atualização como visivel
        viewModel.status.observe(viewLifecycleOwner) { status ->
            when (status) {
                ExtratoservidorViewModel.ApiStatus.LOADING -> {
                    estadoCarregando()
                }
                ExtratoservidorViewModel.ApiStatus.DONE -> {
                    estadoOk()
                }
                else -> {
                    estadoErro()
                }
            }
        }

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
        viewModel.reloadList(false)
    }

    //----------------------------------------------------------------------------------------------
    private fun barraLista(isVisible: Boolean) {
        if (isVisible) {
            binding.BarList.visibility = View.VISIBLE
        } else {
            binding.BarList.visibility = View.GONE
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
    private fun lista(isVisible: Boolean) {
        if (isVisible) {
            binding.Lista.visibility = View.VISIBLE
        } else {
            binding.Lista.visibility = View.GONE
        }
    }

    //----------------------------------------------------------------------------------------------
    private fun mensagem(isVisible: Boolean) {
        if (isVisible) {
            binding.Mensagem.visibility = View.VISIBLE
        } else {
            binding.Mensagem.visibility = View.GONE
        }
    }

    //----------------------------------------------------------------------------------------------
    private fun estadoCarregando() {
        barra(true)
        lista(false)
        mensagem(false)
    }

    private fun estadoOk() {
        barra(false)
        lista(true)
        mensagem(false)
    }

    private fun estadoErro() {
        barra(false)
        lista(false)
        mensagem(true)
        barraLista(false)
    }

    private fun estadoCarregandoLista() {
        barraLista(true)
    }

    private fun estadoOkLista() {
        barraLista(false)
    }
    //----------------------------------------------------------------------------------------------
}
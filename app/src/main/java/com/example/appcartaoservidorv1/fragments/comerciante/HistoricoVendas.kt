package com.example.appcartaoservidorv1.fragments.comerciante

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
import com.example.appcartaoservidorv1.adapters.HistoricocomercianteAdpter
import com.example.appcartaoservidorv1.adapters.Transacao_Listener
import com.example.appcartaoservidorv1.databinding.FragmentComercianteHistoricovendasBinding
import com.example.appcartaoservidorv1.services.redirecionamento.fromHistoricovendasToDetalhes
import com.example.appcartaoservidorv1.services.utilidades.BaseFragment
import com.example.appcartaoservidorv1.viewmodels.comerciante.HistoricovendasViewModel
import com.example.appcartaoservidorv1.viewmodels.comerciante.HistoricovendasViewModelFactory


class HistoricoVendas : BaseFragment() {
    // Variavel responsavel pelo binding
    lateinit var binding: FragmentComercianteHistoricovendasBinding
    lateinit var args: HistoricoVendasArgs

    // Variavel que representa o viewModel
    private lateinit var viewModel: HistoricovendasViewModel
    private lateinit var viewModelFactory: HistoricovendasViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_comerciante_historicovendas,
                container,
                false
            )
        // Recupera as variaveis passada para a view
        args = HistoricoVendasArgs.fromBundle(requireArguments())
        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory = HistoricovendasViewModelFactory(args.matricula, args.token)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[HistoricovendasViewModel::class.java]
        // Faz o binding com o viewModel
        binding.viewModel = viewModel
        // Chama o adapter
        val adapter = HistoricocomercianteAdpter(Transacao_Listener { transacao ->
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
                fromHistoricovendasToDetalhes(this, transacao)
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
                            viewModel.historicoDeVendas(true)
                        }
                    }
                }
            }
        })

        // Coloca a barra de atualização como visivel
        viewModel.statusLista.observe(viewLifecycleOwner) { status ->
            when (status) {
                HistoricovendasViewModel.ApiStatusLista.LOADING -> {
                    estadoCarregandoLista()
                }
                HistoricovendasViewModel.ApiStatusLista.DONE -> {
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
                HistoricovendasViewModel.ApiStatus.LOADING -> {
                    estadoCarregando()
                }
                HistoricovendasViewModel.ApiStatus.DONE -> {
                    estadoOk()
                }
                else -> {
                    estadoErro()
                }
            }
        }

        binding.Lista.isMotionEventSplittingEnabled = false

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
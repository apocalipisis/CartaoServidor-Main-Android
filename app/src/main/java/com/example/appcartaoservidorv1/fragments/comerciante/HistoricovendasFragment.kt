package com.example.appcartaoservidorv1.fragments.comerciante

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.adapters.HistoricocomercianteAdpter
import com.example.appcartaoservidorv1.adapters.Transacao_Listener
import com.example.appcartaoservidorv1.databinding.FragmentHistoricovendasBinding
import com.example.appcartaoservidorv1.services.utilidades.BaseFragment
import com.example.appcartaoservidorv1.services.utilidades.fromHistoricovendasToDetalhes
import com.example.appcartaoservidorv1.viewmodels.comerciante.HistoricovendasViewModel
import com.example.appcartaoservidorv1.viewmodels.comerciante.HistoricovendasViewModelFactory
import com.example.appcartaoservidorv1.viewmodels.servidor.ServidorViewModel


class HistoricovendasFragment : BaseFragment() {
    // Variavel responsavel pelo binding
    lateinit var binding: FragmentHistoricovendasBinding
    lateinit var args: HistoricovendasFragmentArgs

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
            DataBindingUtil.inflate(inflater, R.layout.fragment_historicovendas, container, false)
        // Recupera as variaveis passada para a view
        args = HistoricovendasFragmentArgs.fromBundle(requireArguments())
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
        binding.ListaExtrato.adapter = adapter

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
        binding.ListaExtrato.layoutManager = layoutManager
        // Adi
        binding.ListaExtrato.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) { //check for scroll down
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val pastVisiblesItems = layoutManager.findFirstVisibleItemPosition()
                    if (!viewModel.loading) {
                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                            viewModel.loading = true
                            viewModel.HistoricoDeVendas()
                        }
                    }
                }
            }
        })

        // Coloca a barra de atualização como visivel
        viewModel.status.observe(viewLifecycleOwner) { status ->
            when (status) {
                ServidorViewModel.ApiStatus.LOADING -> {
                    estadoCarregando()
                }
                ServidorViewModel.ApiStatus.DONE -> {
                    estadoOk()
                }
                ServidorViewModel.ApiStatus.ERROR -> {
                    estadoErro()
                }
            }
        }

        // Configura o ciclo de vida
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    private fun estadoCarregando() {
        binding.Image.visibility = View.INVISIBLE
        binding.Bar.visibility = View.VISIBLE
        binding.Menssagem.visibility = View.GONE

    }

    private fun estadoOk() {
        binding.Image.visibility = View.VISIBLE
        binding.Bar.visibility = View.INVISIBLE
        binding.Menssagem.visibility = View.GONE
    }

    private fun estadoErro() {
        binding.Image.visibility = View.VISIBLE
        binding.Bar.visibility = View.INVISIBLE
        binding.Menssagem.visibility = View.VISIBLE
        binding.ListaExtrato.visibility = View.GONE
    }
}
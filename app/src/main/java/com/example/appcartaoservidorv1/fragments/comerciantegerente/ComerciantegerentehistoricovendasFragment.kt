package com.example.appcartaoservidorv1.fragments.comerciantegerente

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.adapters.AdapterComerciantegerenteHistoricoVendas
import com.example.appcartaoservidorv1.adapters.Transacao_Listener
import com.example.appcartaoservidorv1.databinding.FragmentComerciantegerenteHistoricovendasBinding
import com.example.appcartaoservidorv1.services.utilidades.BaseFragment
import com.example.appcartaoservidorv1.services.utilidades.fromHistoricovendasGerenteComercianteToDetalhes
import com.example.appcartaoservidorv1.viewmodels.comerciantegerente.ComerciantegerenteHistoricovendasViewModel
import com.example.appcartaoservidorv1.viewmodels.comerciantegerente.ComerciantegerenteHistoricovendasViewModelFactory

class ComerciantegerentehistoricovendasFragment : BaseFragment() {
    // Variavel responsavel pelo binding
    lateinit var binding: FragmentComerciantegerenteHistoricovendasBinding
    lateinit var args: ComerciantegerentehistoricovendasFragmentArgs

    // Variavel que representa o viewModel
    private lateinit var viewModel: ComerciantegerenteHistoricovendasViewModel
    private lateinit var viewModelFactory: ComerciantegerenteHistoricovendasViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_comerciantegerente_historicovendas,
                container,
                false
            )
        // Recupera as variaveis passada para a view
        args = ComerciantegerentehistoricovendasFragmentArgs.fromBundle(requireArguments())
        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory =
            ComerciantegerenteHistoricovendasViewModelFactory(args.matricula, args.token)
        viewModel =
            ViewModelProvider(
                this,
                viewModelFactory
            )[ComerciantegerenteHistoricovendasViewModel::class.java]
        // Faz o binding com o viewModel
        binding.viewModel = viewModel
        // Chama o adapter
        val adapter = AdapterComerciantegerenteHistoricoVendas(Transacao_Listener { transacao ->
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
                fromHistoricovendasGerenteComercianteToDetalhes(this, transacao)
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
                            viewModel.historicoDeVendas()
                        }
                    }
                }
            }
        })

        // Coloca a barra de atualização como visivel
        viewModel.status.observe(viewLifecycleOwner) { status ->
            when (status) {
                ComerciantegerenteHistoricovendasViewModel.ApiStatus.LOADING -> {
                    estadoCarregando()
                }
                ComerciantegerenteHistoricovendasViewModel.ApiStatus.DONE -> {
                    estadoOk()
                }
                ComerciantegerenteHistoricovendasViewModel.ApiStatus.ERROR -> {
                    estadoErro()
                }
            }
        }

        // Configura o ciclo de vida
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }


    override fun onResume() {
        super.onResume()

        viewModel.reloadList()
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
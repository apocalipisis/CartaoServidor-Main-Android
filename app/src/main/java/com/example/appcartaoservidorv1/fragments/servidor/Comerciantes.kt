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
import com.example.appcartaoservidorv1.adapters.AdapterServidorComerciantes
import com.example.appcartaoservidorv1.adapters.ComercianteModelListener
import com.example.appcartaoservidorv1.databinding.FragmentServidorComerciantesBinding
import com.example.appcartaoservidorv1.services.redirecionamento.fromComercianteToDetalhes
import com.example.appcartaoservidorv1.services.utilidades.BaseFragment
import com.example.appcartaoservidorv1.viewmodels.servidor.ExtratoservidorViewModel
import com.example.appcartaoservidorv1.viewmodels.servidor.ServidorComerciantesViewModel
import com.example.appcartaoservidorv1.viewmodels.servidor.ServidorComerciantesViewModelFactory

class Comerciantes : BaseFragment() {

    // Variavel responsavel pelo binding
    lateinit var binding: FragmentServidorComerciantesBinding
    lateinit var args: ComerciantesArgs

    // Variavel que representa o viewModel
    private lateinit var viewModel: ServidorComerciantesViewModel
    private lateinit var viewModelFactory: ServidorComerciantesViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_servidor_comerciantes,
                container,
                false
            )
        // Recupera as variaveis passada para a view
        args = ComerciantesArgs.fromBundle(requireArguments())
        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory = ServidorComerciantesViewModelFactory(args.token)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[ServidorComerciantesViewModel::class.java]
        // Faz o binding com o viewModel
        binding.viewModel = viewModel
        // Chama o adapter
        val adapter = AdapterServidorComerciantes(ComercianteModelListener { comerciante ->
            viewModel.onComercianteClicked(comerciante)
        })
        binding.Lista.adapter = adapter

        // Coloca um observer nas transações e atualiza o recycler view
        viewModel.comerciantes.observe(viewLifecycleOwner) {
            adapter.addHeaderAndSubmitList(it)
        }
        // Coloca um observer para navegar para a página de detalhes
        viewModel.navigateToComercianteDetail.observe(viewLifecycleOwner) { comerciante ->
            comerciante?.let {
                fromComercianteToDetalhes(this, comerciante)
                viewModel.onComercianteDetailNavigated()
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
                            viewModel.consulta(true)
                        }
                    }
                }
            }
        })


        // Coloca a barra de atualização como visivel
        viewModel.statusLista.observe(viewLifecycleOwner) { status ->
            when (status) {
                ServidorComerciantesViewModel.ApiStatusLista.LOADING -> {
                    estadoCarregandoLista()
                }
                ServidorComerciantesViewModel.ApiStatusLista.DONE -> {
                    estadoOkLista()
                }
                else -> {
                    estadoErro()
                }
            }
        }

        viewModel.status.observe(viewLifecycleOwner) { status ->
            when (status) {
                ServidorComerciantesViewModel.ApiStatus.LOADING -> {
                    estadoCarregando()
                }
                ServidorComerciantesViewModel.ApiStatus.DONE -> {
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
    }

    private fun estadoCarregandoLista() {
        barraLista(true)
    }

    private fun estadoOkLista() {
        barraLista(false)
    }
    //----------------------------------------------------------------------------------------------
}
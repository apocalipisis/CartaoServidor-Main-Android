package com.example.appcartaoservidorv1.fragments.comerciante

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.adapters.GerenteListener
import com.example.appcartaoservidorv1.adapters.GerentesComercianteAdapter
import com.example.appcartaoservidorv1.databinding.FragmentGerentecomercianteBinding
import com.example.appcartaoservidorv1.services.utilidades.BaseFragment
import com.example.appcartaoservidorv1.services.utilidades.fromEscolhasgerentecomercianteToDialog
import com.example.appcartaoservidorv1.services.utilidades.fromGerenteToDetalhes
import com.example.appcartaoservidorv1.viewmodels.comerciante.GerentecomercianteViewModel
import com.example.appcartaoservidorv1.viewmodels.comerciante.GerentecomercianteViewModelFactory
import com.example.appcartaoservidorv1.viewmodels.servidor.ServidorViewModel

class GerentecomercianteFragment : BaseFragment() {
    // Variavel responsavel pelo binding
    lateinit var binding: FragmentGerentecomercianteBinding
    lateinit var args: HistoricovendasFragmentArgs

    // Variavel que representa o viewModel
    private lateinit var viewModel: GerentecomercianteViewModel
    private lateinit var viewModelFactory: GerentecomercianteViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_gerentecomerciante,
                container,
                false
            )
        // Recupera as variaveis passada para a view
        args = HistoricovendasFragmentArgs.fromBundle(requireArguments())
        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory = GerentecomercianteViewModelFactory(args.matricula, args.token)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[GerentecomercianteViewModel::class.java]
        // Faz o binding com o viewModel
        binding.viewModel = viewModel

        // Btn que traz o dialog para criar um novo perfil
        binding.btnCriar.setOnClickListener { fromEscolhasgerentecomercianteToDialog(this, args.matricula, args.token) }
        // binding.btnCriar.setOnClickListener { fromEscolhasgerentecomercianteToCriarGerenteComerciante(this) }

        // Chama o adapter
        val adapter = GerentesComercianteAdapter(GerenteListener { gerente ->
            viewModel.onGerenteClicked(gerente)
        })
        binding.ListaGerentes.adapter = adapter

        // Coloca um observer na consulta aos gerentes e atualiza o recycler view
        viewModel.gerentes.observe(viewLifecycleOwner) {
            adapter.addHeaderAndSubmitList(it)
        }

        // Coloca um observer para navegar para a página de detalhes
        viewModel.navigateToGerenteDetail.observe(viewLifecycleOwner) { gerente ->
            gerente?.let {
                fromGerenteToDetalhes(this, gerente, args.token)
                viewModel.onGerenteDetailNavigated()
            }
        }

        // Use the Kotlin extension in the fragment-ktx artifact
        setFragmentResultListener("dialog") { _,_ ->
            viewModel.reloadList()
        }

        val layoutManager = LinearLayoutManager(this.requireContext())
        binding.ListaGerentes.layoutManager = layoutManager
        // Adi
        binding.ListaGerentes.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) { //check for scroll down
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val pastVisiblesItems = layoutManager.findFirstVisibleItemPosition()
                    if (!viewModel.loading) {
                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                            viewModel.loading = true
                            viewModel.consultarGerentes()
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
        binding.ListaGerentes.visibility = View.GONE
    }

}
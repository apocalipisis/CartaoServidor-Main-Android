package com.example.appcartaoservidorv1.fragments.comerciante

import android.os.Bundle
import android.util.Log
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
import com.example.appcartaoservidorv1.adapters.FuncionarioComercianteAdapter
import com.example.appcartaoservidorv1.adapters.FuncionarioListener
import com.example.appcartaoservidorv1.databinding.FragmentFuncionariocomercianteBinding
import com.example.appcartaoservidorv1.services.utilidades.BaseFragment
import com.example.appcartaoservidorv1.services.utilidades.fromFuncionarioToCriarFuncionario
import com.example.appcartaoservidorv1.services.utilidades.fromFuncionarioToDetalhes
import com.example.appcartaoservidorv1.viewmodels.comerciante.FuncionariocomercianteViewModel
import com.example.appcartaoservidorv1.viewmodels.comerciante.FuncionariocomercianteViewModelModelFactory
import com.example.appcartaoservidorv1.viewmodels.servidor.ServidorViewModel

class FuncionariocomercianteFragment : BaseFragment() {
    // Variavel responsavel pelo binding
    lateinit var binding: FragmentFuncionariocomercianteBinding
    lateinit var args: HistoricovendasFragmentArgs

    // Variavel que representa o viewModel
    private lateinit var viewModel: FuncionariocomercianteViewModel
    private lateinit var viewModelFactory: FuncionariocomercianteViewModelModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_funcionariocomerciante,
                container,
                false
            )
        // Recupera as variaveis passada para a view
        args = HistoricovendasFragmentArgs.fromBundle(requireArguments())
        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory = FuncionariocomercianteViewModelModelFactory(args.matricula, args.token)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[FuncionariocomercianteViewModel::class.java]
        // Faz o binding com o viewModel
        binding.viewModel = viewModel

        // Btn que traz o dialog para criar um novo perfil
         binding.btnCriar.setOnClickListener { fromFuncionarioToCriarFuncionario(this, viewModel.matricula, viewModel.token) }
        // binding.btnCriar.setOnClickListener { fromEscolhasgerentecomercianteToCriarGerenteComerciante(this) }

        // Chama o adapter
        val adapter = FuncionarioComercianteAdapter(FuncionarioListener { funcionario ->
            viewModel.onFuncionarioClicked(funcionario)
        })
        binding.ListaFuncionarios.adapter = adapter

        // Coloca um observer na consulta aos gerentes e atualiza o recycler view
        viewModel.funcionarios.observe(viewLifecycleOwner) {
            adapter.addHeaderAndSubmitList(it)
        }

        // Coloca um observer para navegar para a página de detalhes
        viewModel.navigateToFuncionarioDetail.observe(viewLifecycleOwner) { funcionario ->
            funcionario?.let {
                fromFuncionarioToDetalhes(this, funcionario, viewModel.token)
                viewModel.onFuncionarioDetailNavigated()
            }
        }

        // Escuta mudanças no dialog de criar o funcionario
        setFragmentResultListener("DialogCriarfuncionariocomerciante") { _,_ ->
            viewModel.reloadList()
        }

        val layoutManager = LinearLayoutManager(this.requireContext())
        binding.ListaFuncionarios.layoutManager = layoutManager
        // Adi
        binding.ListaFuncionarios.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) { //check for scroll down
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val pastVisiblesItems = layoutManager.findFirstVisibleItemPosition()
                    if (!viewModel.loading) {
                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                            viewModel.loading = true
                            viewModel.consultarFuncionarios()
                        }
                    }
                }
            }
        })

        // Coloca a barra de atualização como visivel
        viewModel.status.observe(viewLifecycleOwner) { status ->
            when (status) {
                FuncionariocomercianteViewModel.ApiStatus.LOADING -> {
                    estadoCarregando()
                }
                FuncionariocomercianteViewModel.ApiStatus.DONE -> {
                    estadoOk()
                }
                FuncionariocomercianteViewModel.ApiStatus.ERROR -> {
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
        binding.ListaFuncionarios.visibility = View.GONE
    }

}
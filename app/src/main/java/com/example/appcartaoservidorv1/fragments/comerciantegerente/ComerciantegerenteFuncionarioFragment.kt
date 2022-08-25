package com.example.appcartaoservidorv1.fragments.comerciantegerente

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.adapters.FuncionarioComercianteAdapter
import com.example.appcartaoservidorv1.adapters.FuncionarioListener
import com.example.appcartaoservidorv1.databinding.FragmentComerciantegerenteFuncionarioBinding
import com.example.appcartaoservidorv1.services.utilidades.BaseFragment
import com.example.appcartaoservidorv1.services.utilidades.fromComerciantegerenteFuncionarioToCriarFuncionario
import com.example.appcartaoservidorv1.services.utilidades.fromComerciantegerenteFuncionarioToDetalhes
import com.example.appcartaoservidorv1.viewmodels.comerciantegerente.ComerciantegerenteFuncionarioViewModel
import com.example.appcartaoservidorv1.viewmodels.comerciantegerente.ComerciantegerenteFuncionarioViewModelFactory

class ComerciantegerenteFuncionarioFragment : BaseFragment() {
    // Variavel responsavel pelo binding
    lateinit var binding: FragmentComerciantegerenteFuncionarioBinding
    lateinit var args: ComerciantegerenteFuncionarioFragmentArgs

    // Variavel que representa o viewModel
    private lateinit var viewModel: ComerciantegerenteFuncionarioViewModel
    private lateinit var viewModelFactory: ComerciantegerenteFuncionarioViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_comerciantegerente_funcionario,
                container,
                false
            )
        // Recupera as variaveis passada para a view
        args = ComerciantegerenteFuncionarioFragmentArgs.fromBundle(requireArguments())
        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory = ComerciantegerenteFuncionarioViewModelFactory(args.matricula, args.token)
        viewModel =
            ViewModelProvider(
                this,
                viewModelFactory
            )[ComerciantegerenteFuncionarioViewModel::class.java]
        // Faz o binding com o viewModel
        binding.viewModel = viewModel

        // Btn que traz o dialog para criar um novo perfil
        binding.btnCriar.setOnClickListener {
            fromComerciantegerenteFuncionarioToCriarFuncionario(
                this,
                viewModel.matricula,
                viewModel.token
            )
        }

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
                fromComerciantegerenteFuncionarioToDetalhes(this, funcionario, viewModel.token)
                viewModel.onFuncionarioDetailNavigated()
            }
        }

        // Escuta mudanças no dialog de criar o funcionario
        setFragmentResultListener("DialogCriarfuncionarioComerciantegerente") { _, _ ->
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
                ComerciantegerenteFuncionarioViewModel.ApiStatus.LOADING -> {
                    estadoCarregando()
                }
                ComerciantegerenteFuncionarioViewModel.ApiStatus.DONE -> {
                    estadoOk()
                }
                ComerciantegerenteFuncionarioViewModel.ApiStatus.ERROR -> {
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
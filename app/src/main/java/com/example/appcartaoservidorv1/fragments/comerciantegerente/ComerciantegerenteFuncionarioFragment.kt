package com.example.appcartaoservidorv1.fragments.comerciantegerente

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.adapters.FuncionarioComercianteAdapter
import com.example.appcartaoservidorv1.adapters.FuncionarioListener
import com.example.appcartaoservidorv1.databinding.FragmentComerciantegerenteFuncionarioBinding
import com.example.appcartaoservidorv1.services.redirecionamento.fromFuncionariosToCriarDialog
import com.example.appcartaoservidorv1.services.redirecionamento.fromFuncionariosToDeletarDialog
import com.example.appcartaoservidorv1.services.utilidades.BaseFragment
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
        //------------------------------------------------------------------------------------------

        // Btn que traz o dialog para criar um novo perfil
        binding.btnCriar.setOnClickListener {
            fromFuncionariosToCriarDialog(
                this,
                viewModel.matricula,
                viewModel.token
            )
        }
        //------------------------------------------------------------------------------------------
        // Botão Voltar
        binding.btnVoltar.setOnClickListener {
            findNavController().navigateUp()
        }
        //------------------------------------------------------------------------------------------
        // Chama o adapter
        val adapter = FuncionarioComercianteAdapter(FuncionarioListener { funcionario ->
            viewModel.onStatusChangeClicked(funcionario)
        }, FuncionarioListener { funcionario ->
            fromFuncionariosToDeletarDialog(
                this,
                funcionario.Matricula,
                viewModel.token
            )
        })
        binding.Lista.adapter = adapter
        //------------------------------------------------------------------------------------------
        // Coloca um observer na consulta aos gerentes e atualiza o recycler view
        viewModel.funcionarios.observe(viewLifecycleOwner) {
            adapter.addHeaderAndSubmitList(it)
            if (viewModel.notify) {
                adapter.notifyItemChanged(viewModel.item + 1)
                viewModel.notify = false
            }
        }
        //------------------------------------------------------------------------------------------
        // Escuta mudanças no dialog de criar o gerente
        setFragmentResultListener("DialogCriarFuncionarioGerenteComerciante") { _, _ ->
            viewModel.reloadList(false)
        }

        // Escuta mudanças no dialog de deletar o gerente
        setFragmentResultListener("DialogDeletarFuncionarioGerenteComerciante") { _, _ ->
            viewModel.reloadList(false)
        }
        //------------------------------------------------------------------------------------------
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
                            viewModel.consultarFuncionarios(true)
                        }
                    }
                }
            }
        })
        //------------------------------------------------------------------------------------------

        // Coloca a barra de atualização como visivel
        viewModel.status.observe(viewLifecycleOwner) { status ->
            when (status) {
                ComerciantegerenteFuncionarioViewModel.ApiStatus.LOADING -> {
                    estadoCarregando()
                }
                ComerciantegerenteFuncionarioViewModel.ApiStatus.DONE -> {
                    estadoOk()
                }
                else -> {
                    estadoErro()
                }
            }
        }
        //------------------------------------------------------------------------------------------
        // Coloca a barra de atualização como visivel
        viewModel.statusUpdateRequest.observe(viewLifecycleOwner) { status ->
            when (status) {
                ComerciantegerenteFuncionarioViewModel.ApiStatusUpdateRequest.LOADING -> {
                    barraStatus(true)
                }
                ComerciantegerenteFuncionarioViewModel.ApiStatusUpdateRequest.DONE -> {
                    barraStatus(false)
                }
                ComerciantegerenteFuncionarioViewModel.ApiStatusUpdateRequest.ERROR -> {
                    barraStatus(false)
                }
                else -> {

                }
            }
        }
        //------------------------------------------------------------------------------------------

        // Configura o ciclo de vida
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    // ---------------------------------------------------------------------------------------------
    override fun onResume() {
        super.onResume()
        viewModel.reloadList(false)
    }

    // ---------------------------------------------------------------------------------------------
    private fun barraStatus(isVisible: Boolean) {
        if (isVisible) {
            binding.barraStatus.visibility = View.VISIBLE
        } else {
            binding.barraStatus.visibility = View.INVISIBLE
        }
    }
    // ---------------------------------------------------------------------------------------------

    private fun estadoCarregando() {

    }

    private fun estadoOk() {

    }

    private fun estadoErro() {

        binding.Lista.visibility = View.GONE
    }
}
package com.example.appcartaoservidorv1.fragments.comerciante

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.adapters.GerenteListener
import com.example.appcartaoservidorv1.adapters.GerentesComercianteAdapter
import com.example.appcartaoservidorv1.databinding.FragmentComercianteGerentesBinding
import com.example.appcartaoservidorv1.services.redirecionamento.fromGerentesToCriarGerente
import com.example.appcartaoservidorv1.services.redirecionamento.fromGerentesToDeletarGerente
import com.example.appcartaoservidorv1.viewmodels.comerciante.GerentecomercianteViewModel
import com.example.appcartaoservidorv1.viewmodels.comerciante.GerentecomercianteViewModelFactory


class Gerentes : Fragment() {
    // Variavel responsavel pelo binding
    lateinit var binding: FragmentComercianteGerentesBinding
    lateinit var args: GerentesArgs

    // Variavel que representa o viewModel
    private lateinit var viewModel: GerentecomercianteViewModel
    private lateinit var viewModelFactory: GerentecomercianteViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_comerciante_gerentes, container, false
        )
        // Recupera as variaveis passada para a view
        args = GerentesArgs.fromBundle(requireArguments())
        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory = GerentecomercianteViewModelFactory(args.matricula, args.token)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[GerentecomercianteViewModel::class.java]
        // Faz o binding com o viewModel
        binding.viewModel = viewModel
        //------------------------------------------------------------------------------------------
        // Btn que traz o dialog para criar um novo perfil
        binding.btnCriar.setOnClickListener {
            fromGerentesToCriarGerente(
                this, args.matricula, args.token
            )
        }
        //------------------------------------------------------------------------------------------
        // Botão Voltar
        binding.btnVoltar.setOnClickListener {
            findNavController().navigateUp()
        }
        //------------------------------------------------------------------------------------------
        // Chama o adapter
        val adapter = GerentesComercianteAdapter(GerenteListener { gerente ->
            viewModel.onStatusChangeClicked(gerente)
        }, GerenteListener { gerente ->
            fromGerentesToDeletarGerente(
                this, gerente.Matricula, viewModel.token
            )
        })
        binding.Lista.adapter = adapter
        //------------------------------------------------------------------------------------------
        // Coloca um observer na consulta aos gerentes e atualiza o recycler view
        viewModel.gerentes.observe(viewLifecycleOwner) {
            adapter.addHeaderAndSubmitList(it)
            if (viewModel.notify) {
                adapter.notifyItemChanged(viewModel.item + 1)
                viewModel.notify = false
            }
        }
        //------------------------------------------------------------------------------------------
        // Escuta mudanças no dialog de criar o gerente
        setFragmentResultListener("DialogCriarGerente") { _, _ ->
            viewModel.reloadList(false)
        }

        // Escuta mudanças no dialog de deletar o gerente
        setFragmentResultListener("DialogDeletarGerente") { _, _ ->
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
                            viewModel.consultarGerentes(true)
                        }
                    }
                }
            }
        })
        //------------------------------------------------------------------------------------------
        // Coloca a barra de atualização como visivel
        viewModel.statusLista.observe(viewLifecycleOwner) { status ->
            when (status) {
                GerentecomercianteViewModel.ApiStatusLista.LOADING -> {
                    estadoCarregandoLista()
                }
                GerentecomercianteViewModel.ApiStatusLista.DONE -> {
                    estadoOkLista()
                }
                else -> {
                    estadoErro()
                }
            }
        }
        //------------------------------------------------------------------------------------------
        // Coloca a barra de atualização como visivel
        viewModel.status.observe(viewLifecycleOwner) { status ->
            when (status) {
                GerentecomercianteViewModel.ApiStatus.LOADING -> {
                    estadoCarregando()
                }
                GerentecomercianteViewModel.ApiStatus.DONE -> {
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
                GerentecomercianteViewModel.ApiStatusUpdateRequest.LOADING -> {
                    barraStatus(true)
                }
                GerentecomercianteViewModel.ApiStatusUpdateRequest.DONE -> {
                    barraStatus(false)
                }
                GerentecomercianteViewModel.ApiStatusUpdateRequest.ERROR -> {
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

    //----------------------------------------------------------------------------------------------
    private fun barraLista(isVisible: Boolean) {
        if (isVisible) {
            binding.BarList.visibility = View.VISIBLE
        } else {
            binding.BarList.visibility = View.GONE
        }
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

    override fun onResume() {
        super.onResume()
        viewModel.reloadList(false)
    }

    private fun estadoCarregando() {
        //TODO implementar barra de loading
    }

    private fun estadoOk() {
        //TODO Vê se vai precisar ficar mudando de estado
    }

    private fun estadoErro() {

        //TODO Vê se vai precisar ficar mudando de estado
        binding.Lista.visibility = View.GONE
    }

    private fun estadoCarregandoLista() {
        barraLista(true)
    }

    private fun estadoOkLista() {
        barraLista(false)
    }

}
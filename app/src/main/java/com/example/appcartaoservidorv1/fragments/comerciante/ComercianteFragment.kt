package com.example.appcartaoservidorv1.fragments.comerciante

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.FragmentComercianteBinding
import com.example.appcartaoservidorv1.fragments.servidor.CompraservidorFragmentArgs
import com.example.appcartaoservidorv1.fragments.servidor.ServidorFragmentDirections
import com.example.appcartaoservidorv1.viewmodels.comerciante.ComercianteViewModel
import com.example.appcartaoservidorv1.viewmodels.comerciante.ComercianteViewModelFactory
import com.example.appcartaoservidorv1.viewmodels.servidor.CompraservidorViewModel
import com.example.appcartaoservidorv1.viewmodels.servidor.CompraservidorViewModelFactory
import com.example.appcartaoservidorv1.viewmodels.servidor.ServidorViewModel

class ComercianteFragment : Fragment() {
    // Variavel responsavel pelo binding
    lateinit var binding: FragmentComercianteBinding
    lateinit var args: ComercianteFragmentArgs

    // Variavel que representa o viewModel
    private lateinit var viewModel: ComercianteViewModel
    private lateinit var viewModelFactory: ComercianteViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla o layout do fragmento
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_comerciante, container, false)
        // Recupera as variaveis passada para a view
        args = ComercianteFragmentArgs.fromBundle(requireArguments())
        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory = ComercianteViewModelFactory(args.matricula, args.nome)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ComercianteViewModel::class.java)
        // Faz o binding com o viewModel
        binding.viewModel = viewModel

        // ClickListener para o botão adicionarVenda
        binding.btnVender.setOnClickListener {
            goToInserirValorPage()
//            Toast.makeText(context, "Btn comprar Clickado", Toast.LENGTH_LONG).show()
        }

        // Configura o btn sair
        binding.btnSair.setOnClickListener { onSair() }

        // Coloca a barra de atualização como visivel
        viewModel.status.observe(
            viewLifecycleOwner,
            Observer<ComercianteViewModel.ApiStatus> { status ->
                when (status) {
                    ComercianteViewModel.ApiStatus.LOADING -> {
                        mostrarBarra()
                        escondeBtns()
                    }
                    ComercianteViewModel.ApiStatus.DONE -> {
                        esconderBarra()
                        habilitaBtns()
                        escondeMensagem()
                        escondeBtnRefresh()
                    }
                    ComercianteViewModel.ApiStatus.ERROR -> {
                        esconderBarra()
                        desabilitaBtns()
                        mostraMensagem()
                        mostraBtnRefresh()
                    }
                }
            })

        // Configura o ciclo de vida
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }


    // Função que executa o logout de um usuário
    private fun onSair() {
        val action = ComercianteFragmentDirections.actionComercianteFragmentToLoginFragment()
        NavHostFragment.findNavController(this).navigate(action)
    }

    // Função que redireciona o usuario para a pagina Vender
    private fun goToInserirValorPage() {
        val action = ComercianteFragmentDirections.actionComercianteFragmentToInserirvalorFragment()
        NavHostFragment.findNavController(this).navigate(action)
    }

    // Mostra a barra de loading e esconde os demais campos
    private fun mostrarBarra() {
        binding.Bar.visibility = View.VISIBLE
        binding.faturamento.visibility = View.GONE
        binding.faturamentoLabel.visibility = View.GONE
    }

    // Esconde a barra de loading e mostra os demais campos
    private fun esconderBarra() {
        binding.Bar.visibility = View.GONE
        binding.faturamento.visibility = View.VISIBLE
        binding.faturamentoLabel.visibility = View.VISIBLE
    }

    // Habilita os btns
    private fun habilitaBtns() {
        binding.btnHistorico.isEnabled = true
        binding.btnInfo.isEnabled = true
        binding.btnVender.isEnabled = true
    }

    // Desabilita os btns
    private fun desabilitaBtns() {
        binding.btnHistorico.isEnabled = false
        binding.btnInfo.isEnabled = false
        binding.btnVender.isEnabled = false
    }

    // Mostra mensagem sobre consulta a API
    private fun mostraMensagem() {
        binding.mensagem.visibility = View.VISIBLE
        binding.faturamento.visibility = View.GONE
        binding.faturamentoLabel.visibility = View.GONE
        binding.saldoMes.visibility = View.GONE
    }

    // Esconde mensagem sobre consulta a API
    private fun escondeMensagem() {
        binding.mensagem.visibility = View.GONE
        binding.faturamento.visibility = View.VISIBLE
        binding.faturamentoLabel.visibility = View.VISIBLE
        binding.saldoMes.visibility = View.VISIBLE
    }

    // Mostra btn refresh
    private fun mostraBtnRefresh() {
        binding.btnRefresh.visibility = View.VISIBLE
        binding.btnHistorico.visibility = View.GONE
        binding.btnInfo.visibility = View.GONE
        binding.btnVender.visibility = View.GONE
    }

    // Esconde btn refresh
    private fun escondeBtnRefresh() {
        binding.btnRefresh.visibility = View.GONE
        binding.btnHistorico.visibility = View.VISIBLE
        binding.btnInfo.visibility = View.VISIBLE
        binding.btnVender.visibility = View.VISIBLE
    }

    // Esconde todos os btn
    private fun escondeBtns() {
        binding.btnRefresh.visibility = View.GONE
        binding.btnHistorico.visibility = View.GONE
        binding.btnInfo.visibility = View.GONE
        binding.btnVender.visibility = View.GONE
    }


}
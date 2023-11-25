package com.example.appcartaoservidorv1.fragments.comerciantegerente

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.FragmentComerciantegerenteVendaSenhaBinding
import com.example.appcartaoservidorv1.services.utilidades.BaseFragment
import com.example.appcartaoservidorv1.services.utilidades.fromComerciantegerenteInserirsenhaToComerciantegerente
import com.example.appcartaoservidorv1.services.utilidades.fromComerciantegerenteInserirsenhaToStatus
import com.example.appcartaoservidorv1.viewmodels.comerciantegerente.ComerciantegerenteVendaSenhaViewModel
import com.example.appcartaoservidorv1.viewmodels.comerciantegerente.ComerciantegerenteVendaSenhaViewModelFactory

class ComerciantegerenteVendaSenhaFragment : BaseFragment() {
    // Variavel responsavel pelo binding
    lateinit var binding: FragmentComerciantegerenteVendaSenhaBinding
    lateinit var args: ComerciantegerenteVendaSenhaFragmentArgs

    // Variavel que representa o viewModel
    private lateinit var viewModel: ComerciantegerenteVendaSenhaViewModel
    private lateinit var viewModelFactory: ComerciantegerenteVendaSenhaViewModelFactory

    // Variavel que diz se pode mudar o campo de inserir a senha
    private var canChange: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_comerciantegerente_venda_senha,
                container,
                false
            )
        // Recupera as variaveis passada para a view
        args = ComerciantegerenteVendaSenhaFragmentArgs.fromBundle(requireArguments())
        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory = ComerciantegerenteVendaSenhaViewModelFactory(
            args.matriculaCliente,
            args.nomeCliente,
            args.valor,
            args.matriculaComerciante,
            args.nomeComerciante,
            args.matriculaVendedor,
            args.nomeVendedor,
            args.numeroCartao,
            args.token,
        )
        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[ComerciantegerenteVendaSenhaViewModel::class.java]
        // Faz o binding com o viewModel
        binding.viewModel = viewModel
        // Configuração da tela inicial
        binding.btnAvancar.isEnabled = false
        binding.senha1.requestFocus()

        // ClickListener para o botão avançar
        binding.btnAvancar.setOnClickListener {
            viewModel.senhaCompleta.value?.let { it1 ->
                fromComerciantegerenteInserirsenhaToStatus(
                    this,
                    viewModel.matriculaCliente,
                    viewModel.matriculaComerciante,
                    viewModel.matriculaVendedor,
                    viewModel.valor,
                    it1,
                    viewModel.nomeVendedor,
                    viewModel.numeroCartao,
                    viewModel.token,
                )
            }
        }

        // Botão Voltar
        binding.btnVoltar.setOnClickListener {
            findNavController().navigateUp()
        }

        // ClickListener para o botão Corrigir
        binding.btnCorrige.setOnClickListener {
            onPressCorrigeBtn()
        }

        // ClickListener para o botão Cancelar
        binding.btnCancela.setOnClickListener {
            fromComerciantegerenteInserirsenhaToComerciantegerente(
                this,
                viewModel.matriculaVendedor,
                viewModel.nomeVendedor,
                viewModel.token
            )
        }

        // Obserca se a senha contem os 4 digitos e permite avançar
        viewModel.senhaCompleta.observe(viewLifecycleOwner) { senhaCompleta ->
            if (senhaCompleta.length == 4) {
//                hideKeyboard()
                binding.btnAvancar.isEnabled = true
                view?.clearFocus()
            } else {
                binding.btnAvancar.isEnabled = false
            }
        }

        // Observa mudanças na entrada do 1 digito
        binding.senha1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (canChange) {
                    binding.senha2.requestFocus()
                }
                canChange = false
                viewModel.senhaCompleta()
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (start < count) {
                    canChange = true
                }
            }
        })

        // Observa mudanças na entrada do 2 digito
        binding.senha2.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (canChange) {
                    binding.senha3.requestFocus()
                }
                canChange = false
                viewModel.senhaCompleta()
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (start < count) {
                    canChange = true
                }
            }
        })

        // Observa mudanças na entrada do 3 digito
        binding.senha3.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (canChange) {
                    binding.senha4.requestFocus()
                }
                canChange = false
                viewModel.senhaCompleta()
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (start < count) {
                    canChange = true
                }
            }
        })

        // Observa mudanças na entrada do 4 digito
        binding.senha4.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (canChange) {
                    view?.clearFocus()
                }
                canChange = false
                viewModel.senhaCompleta()
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (start < count) {
                    canChange = true
                }
            }
        })

        // Configura o ciclo de vida
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }


    // Layout para quando o botão corrigir for apertado
    private fun onPressCorrigeBtn() {
        viewModel.senha1 = ""
        viewModel.senha2 = ""
        viewModel.senha3 = ""
        viewModel.senha4 = ""

        binding.senha1.setText("")
        binding.senha2.setText("")
        binding.senha3.setText("")
        binding.senha4.setText("")

        binding.senha1.requestFocus()
//        showKeyboard()
    }

//    // Esconde o teclado quando a view for destruida
//    override fun onDestroyView() {
//        super.onDestroyView()
//        hideKeyboard()
//    }

    // Essas funções não podem ir para as funções comuns (por algum motiva lá não funcionam)
    // Função que mostra o teclado
    private fun showKeyboard() {
        val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    // Função que esconde o teclado
    private fun hideKeyboard() {
        val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

}
package com.example.appcartaoservidorv1.fragments.comerciante

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.*
import com.example.appcartaoservidorv1.databinding.FragmentInserirsenhaBinding
import com.example.appcartaoservidorv1.services.utilidades.*
import com.example.appcartaoservidorv1.viewmodels.comerciante.InserirsenhaViewModel
import com.example.appcartaoservidorv1.viewmodels.comerciante.InserirsenhaViewModelFactory

class InserirsenhaFragment : BaseFragment() {
    // Variavel responsavel pelo binding
    lateinit var binding: FragmentInserirsenhaBinding
    lateinit var args: InserirsenhaFragmentArgs

    // Variavel que representa o viewModel
    private lateinit var viewModel: InserirsenhaViewModel
    private lateinit var viewModelFactory: InserirsenhaViewModelFactory

    // Variavel que diz se pode mudar o campo de inserir a senha
    private var canChange: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_inserirsenha, container, false)
        // Recupera as variaveis passada para a view
        args = InserirsenhaFragmentArgs.fromBundle(requireArguments())
        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory = InserirsenhaViewModelFactory(
            args.matricula,
            args.nome,
            args.valor,
            args.nomeComerciante,
            args.matriculaComerciante,
            args.token,
        )
        viewModel = ViewModelProvider(this, viewModelFactory)[InserirsenhaViewModel::class.java]
        // Faz o binding com o viewModel
        binding.viewModel = viewModel
        // Configuração da tela inicial
        binding.btnAvancar.isEnabled = false
        binding.senha1.requestFocus()
//        showKeyboard()

        // Armazena o contexto em uma variavel
        val appContext = this.requireContext()

        // ClickListener para o botão avançar
        binding.btnAvancar.setOnClickListener {
            viewModel.senhaCompleta.value?.let { it1 ->
                fromInserirsenhaToStatusvenda(
                    this, viewModel.valor, viewModel.matricula, viewModel.matriculaComerciante,
                    it1, viewModel.nomeComerciante, viewModel.token
                )
            }
        }


        // ClickListener para o botão Corrigir
        binding.btnCorrige.setOnClickListener {
            onPressCorrigeBtn()
        }

        // ClickListener para o botão Cancelar
        binding.btnCancela.setOnClickListener {
            fromInserirsenhaToComerciante(
                this,
                viewModel.nomeComerciante,
                viewModel.matriculaComerciante,
                viewModel.token
            )
        }

        // Obserca se a senha contem os 4 digitos e permite avançar
        viewModel.senhaCompleta.observe(viewLifecycleOwner) { senhaCompleta ->
            Log.i("Senha", senhaCompleta)
            if (senhaCompleta.length == 4) {
                hideKeyboard()
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
package com.example.appcartaoservidorv1.fragments.comerciante

import android.content.Context
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
import com.example.appcartaoservidorv1.databinding.FragmentComercianteVendaValorBinding
import com.example.appcartaoservidorv1.services.utilidades.BaseFragment
import com.example.appcartaoservidorv1.services.utilidades.fromInserirvalorToVendacomerciante
import com.example.appcartaoservidorv1.viewmodels.comerciante.InserirvalorViewModel
import com.example.appcartaoservidorv1.viewmodels.comerciante.InserirvalorViewModelFactory
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.*


class VendaValor : BaseFragment() {
    // Variavel responsavel pelo binding
    lateinit var binding: FragmentComercianteVendaValorBinding
    lateinit var args: VendaValorArgs

    // Variavel que representa o viewModel
    private lateinit var viewModel: InserirvalorViewModel
    private lateinit var viewModelFactory: InserirvalorViewModelFactory

    private var current: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_comerciante_venda_valor,
                container,
                false
            )
        // Recupera as variaveis passada para a view
        args = VendaValorArgs.fromBundle(requireArguments())
        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory =
            InserirvalorViewModelFactory(
                args.nomeComerciante,
                args.matriculaComerciante,
                args.token
            )
        viewModel = ViewModelProvider(this, viewModelFactory)[InserirvalorViewModel::class.java]
        // Faz o binding com o viewModel
        binding.viewModel = viewModel

        // Valor inicial
        binding.Valor.setText(getString(R.string.ZeroReais))

        viewModel.valor.observe(viewLifecycleOwner) {
            binding.btnAvancar.isEnabled = it > 0.0
        }

        // Ação do botão avançar
        binding.btnAvancar.setOnClickListener {
            viewModel.valor.value?.let { it1 ->
                fromInserirvalorToVendacomerciante(
                    this,
                    it1.toFloat(),
                    viewModel.nomeComerciante,
                    viewModel.matriculaComerciante,
                    viewModel.token
                )
            }
        }

        // Botão Voltar
        binding.btnVoltar.setOnClickListener {
            findNavController().navigateUp()
        }

        // Foca no campo para digitar o valor e abre o teclado
        showSoftKeyboard(binding.Valor)
        // Formata a entrada do valor em "tempo real"
        binding.Valor.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
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
                if (s.toString() != current) {
                    binding.Valor.removeTextChangedListener(this)
//                    val cleanString: String = s.replace("""[$,.]""".toRegex(), "")
                    val cleanString: String = s.toString()
                        .replace("\\s".toRegex(), "")
                        .replace(".", "")
                        .replace(",", "")


                    val parsed = try {
                        cleanString.toDouble()
                    } catch (e: Exception) {
                        0.0
                    }

                    val formatter =
                        NumberFormat.getCurrencyInstance(Locale.getDefault()) as DecimalFormat
                    val symbols: DecimalFormatSymbols = formatter.decimalFormatSymbols
                    symbols.currencySymbol = "" // Don't use null.

                    formatter.decimalFormatSymbols = symbols

                    val formatted = formatter.format((parsed / 100))
                    viewModel.valor.value = (parsed / 100)

//                    binding.btnAvancar.isEnabled = parsed > 0.0

                    current = formatted
                    binding.Valor.setText(formatted)
                    binding.Valor.setSelection(formatted.length)

                    binding.Valor.addTextChangedListener(this)
                }
            }
        })
        // Configura o ciclo de vida
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    // Função que mostra o teclado
    private fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    // Função que esconde o teclado
//    private fun hideKeyboard(view: View) {
//        if (view.requestFocus()) {
//            val imm =
//                view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//            imm.hideSoftInputFromWindow(view, InputMethodManager.HIDE_IMPLICIT_ONLY)
//        }
//    }
}


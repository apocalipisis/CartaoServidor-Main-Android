package com.example.appcartaoservidorv1.fragments.comerciantegerente

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
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.FragmentComerciantegerenteVendaValorBinding
import com.example.appcartaoservidorv1.services.utilidades.BaseFragment
import com.example.appcartaoservidorv1.services.utilidades.fromComerciantegerenteValorToQrcode
import com.example.appcartaoservidorv1.viewmodels.comerciantegerente.ComerciantegerenteVendaValorViewModel
import com.example.appcartaoservidorv1.viewmodels.comerciantegerente.ComerciantegerenteVendaValorViewModelFactory
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.*

class ComerciantegerenteVendaValorFragment: BaseFragment() {
    // Variavel responsavel pelo binding
    lateinit var binding: FragmentComerciantegerenteVendaValorBinding
    lateinit var args: ComerciantegerenteVendaValorFragmentArgs

    // Variavel que representa o viewModel
    private lateinit var viewModel: ComerciantegerenteVendaValorViewModel
    private lateinit var viewModelFactory: ComerciantegerenteVendaValorViewModelFactory

    private var current: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_comerciantegerente_venda_valor, container, false)
        // Recupera as variaveis passada para a view
        args = ComerciantegerenteVendaValorFragmentArgs.fromBundle(requireArguments())
        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory =
            ComerciantegerenteVendaValorViewModelFactory(
                args.matriculaComerciante,
                args.nomeComerciante,
                args.matriculaVendedor,
                args.nomeVendedor,
                args.token,
            )
        viewModel = ViewModelProvider(this, viewModelFactory)[ComerciantegerenteVendaValorViewModel::class.java]
        // Faz o binding com o viewModel
        binding.viewModel = viewModel

        // Valor inicial
        binding.Valor.setText("0,00")

        viewModel.valor.observe(viewLifecycleOwner) {
            binding.btnAvancar.isEnabled = it > 0.0
        }

        // Ação do botão avançar
        binding.btnAvancar.setOnClickListener {
            viewModel.valor.value?.let { it1 ->
                fromComerciantegerenteValorToQrcode(
                    this,
                    it1.toFloat(),
                    viewModel.matriculaComerciante,
                    viewModel.nomeComerciante,
                    viewModel.matriculaVendedor,
                    viewModel.nomeVendedor,
                    viewModel.token
                )
            }
        }

        // Necessario para o teclado do campo onde coloca o valor só tenha numeros
        binding.Valor.transformationMethod = null

        // Foca no campo para digitar o valor e abre o teclado
        binding.Valor.requestFocus()
//        showKeyboard()

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
                    Log.i("TesteEntrada", s.toString())
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

//    // Esconde o teclado quando a view for destruida
//    override fun onDestroyView() {
//        super.onDestroyView()
//        binding.Valor.requestFocus()
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

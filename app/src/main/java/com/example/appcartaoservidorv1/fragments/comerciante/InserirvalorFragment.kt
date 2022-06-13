package com.example.appcartaoservidorv1.fragments.comerciante

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.FragmentInserirvalorBinding
import com.example.appcartaoservidorv1.viewmodels.comerciante.InserirvalorViewModel
import com.example.appcartaoservidorv1.viewmodels.comerciante.InserirvalorViewModelFactory
import com.google.android.material.textfield.TextInputEditText
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.*

class InserirvalorFragment : Fragment() {
    // Variavel responsavel pelo binding
    lateinit var binding: FragmentInserirvalorBinding

    // Variavel que representa o viewModel
    private lateinit var viewModel: InserirvalorViewModel
    private lateinit var viewModelFactory: InserirvalorViewModelFactory


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla o layout do fragmento
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_inserirvalor, container, false)
        // Recupera as variaveis passada para a view
        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory = InserirvalorViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(InserirvalorViewModel::class.java)
        // Faz o binding com o viewModel
        binding.viewModel = viewModel

        // Necessario para o teclado do campo onde coloca o valor só tenha numeros
        binding.Valor.transformationMethod = null
        // Foca no campo para digitar o valor e abre o teclado
        binding.Valor.requestFocus()
        showKeyboard()
        // Desativa o botão de ir para a proxima página

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
                val strLimpa = limpaStrInput(s.toString())
                when (strLimpa.length) {
                    0 -> {
                        novoValor("0,00")
                        binding.btnAvancar.isEnabled = false
                    }
                    1 -> {
                        val str = formataString(("0.0${strLimpa}").toFloat())
                        novoValor(str)
                        binding.btnAvancar.isEnabled = true
                    }
                    2 -> {
                        val str = formataString(("0.${strLimpa}").toFloat())
                        novoValor(str)
                        binding.btnAvancar.isEnabled = true
                    }
                    else -> {
                        val str = StringBuilder(strLimpa).insert(strLimpa.length - 2, '.')
                        val strIn = formataString(str.toString().toFloat())
                        novoValor(strIn)
                        binding.btnAvancar.isEnabled = true
                    }
                }
            }
        })
        // Configura o ciclo de vida
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.Valor.requestFocus()
        hideKeyboard()
    }

    private fun limpaStrInput(str: String): String {
        return str.replace(".", "").replace(",", "").trimStart('0')
    }

    private fun novoValor(str: String) {
        viewModel.Valor.value = str

        binding.Valor.requestFocus();
        binding.Valor.getText()?.let { binding.Valor.setSelection(it.length) }
    }

    private fun formataString(numb: Number): String {
        val formatter = NumberFormat.getCurrencyInstance(Locale.getDefault()) as DecimalFormat
        val symbols: DecimalFormatSymbols = formatter.decimalFormatSymbols
        symbols.setCurrencySymbol("") // Don't use null.

        formatter.decimalFormatSymbols = symbols

        return formatter.format(numb)
    }

    fun showKeyboard() {
        val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    fun hideKeyboard() {
        val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}

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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.FragmentCompraservidorBinding
import com.example.appcartaoservidorv1.databinding.FragmentInserirsenhaBinding
import com.example.appcartaoservidorv1.fragments.servidor.CompraservidorFragmentArgs
import com.example.appcartaoservidorv1.viewmodels.comerciante.InserirsenhaViewModel
import com.example.appcartaoservidorv1.viewmodels.comerciante.InserirsenhaViewModelFactory
import com.example.appcartaoservidorv1.viewmodels.servidor.CompraservidorViewModel
import com.example.appcartaoservidorv1.viewmodels.servidor.CompraservidorViewModelFactory
import com.example.appcartaoservidorv1.viewmodels.servidor.ServidorViewModel

class InserirsenhaFragment : Fragment() {
    // Variavel responsavel pelo binding
    lateinit var binding: FragmentInserirsenhaBinding
    lateinit var args: InserirsenhaFragmentArgs

    // Variavel que representa o viewModel
    private lateinit var viewModel: InserirsenhaViewModel
    private lateinit var viewModelFactory: InserirsenhaViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
            args.nomeComerciante
        )
        viewModel = ViewModelProvider(this, viewModelFactory).get(InserirsenhaViewModel::class.java)
        // Faz o binding com o viewModel
        binding.viewModel = viewModel

        binding.btnAvancar.isEnabled = false
        binding.senha1.requestFocus()
        showKeyboard()

        viewModel.senhaCompleta.observe(viewLifecycleOwner, Observer { senhaCompleta ->
            Log.i("Senha", senhaCompleta)
            if (senhaCompleta.length == 4) {
                hideKeyboard()
                binding.btnAvancar.isEnabled = true
                binding.btnAvancar.requestFocus()
            }
        })

//        binding.senha1.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable) {
//                viewModel.SenhaCompleta()
//            }
//
//            override fun beforeTextChanged(
//                s: CharSequence, start: Int,
//                count: Int, after: Int
//            ) {
//            }
//
//            override fun onTextChanged(
//                s: CharSequence, start: Int,
//                before: Int, count: Int
//            ) {
//                if (start < count) {
//                    binding.senha2.requestFocus()
//                }
//            }
//        })
//
//        binding.senha2.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable) {
//                viewModel.SenhaCompleta()
//            }
//
//            override fun beforeTextChanged(
//                s: CharSequence, start: Int,
//                count: Int, after: Int
//            ) {
//            }
//
//            override fun onTextChanged(
//                s: CharSequence, start: Int,
//                before: Int, count: Int
//            ) {
//                if (start < count) {
//                    binding.senha3.requestFocus()
//                }
//            }
//        })
//
//        binding.senha3.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable) {
//                viewModel.SenhaCompleta()
//            }
//
//            override fun beforeTextChanged(
//                s: CharSequence, start: Int,
//                count: Int, after: Int
//            ) {
//            }
//
//            override fun onTextChanged(
//                s: CharSequence, start: Int,
//                before: Int, count: Int
//            ) {
//                if (start < count) {
//                    binding.senha4.requestFocus()
//                }
//            }
//        })

//        binding.senha4.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable) {
//                viewModel.SenhaCompleta()
//            }
//
//            override fun beforeTextChanged(
//                s: CharSequence, start: Int,
//                count: Int, after: Int
//            ) {
//            }
//
//            override fun onTextChanged(
//                s: CharSequence, start: Int,
//                before: Int, count: Int
//            ) {
//            }
//        })

        // Configura o ciclo de vida
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideKeyboard()
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
package com.example.appcartaoservidorv1.dialogs

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.DialogCriargerentecomercianteBinding
import com.example.appcartaoservidorv1.databinding.DialogEditargerentecomercianteBinding
import com.example.appcartaoservidorv1.dialogsviewmodels.CriargerentecomercianteViewModel
import com.example.appcartaoservidorv1.dialogsviewmodels.CriargerentecomercianteViewModelFactory
import com.example.appcartaoservidorv1.dialogsviewmodels.DialogEditargerentecomercianteViewModel
import com.example.appcartaoservidorv1.dialogsviewmodels.DialogEditargerentecomercianteViewModelFactory
import com.example.appcartaoservidorv1.models.auxiliares.ParBoolString
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DialogEditargerentecomerciante : BottomSheetDialogFragment() {
    // Variavel responsavel pelo binding
    lateinit var binding: DialogEditargerentecomercianteBinding
    lateinit var args: DialogEditargerentecomercianteArgs

    // Variavel que representa o viewModel
    private lateinit var viewModel: DialogEditargerentecomercianteViewModel
    private lateinit var viewModelFactory: DialogEditargerentecomercianteViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.dialog_editargerentecomerciante,
                container,
                false
            )

        // Recupera as variaveis passada para a view
        args = DialogEditargerentecomercianteArgs.fromBundle(requireArguments())

        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory = DialogEditargerentecomercianteViewModelFactory(args.matricula, args.token)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[DialogEditargerentecomercianteViewModel::class.java]
        // Faz o binding com o viewModel
        binding.viewModel = viewModel

        // Fecha o Dialog
        binding.btnSair.setOnClickListener { dialog?.dismiss() }

        // Criar gerente
        binding.btnSalvar.setOnClickListener {
            viewModel.editarGerente(
                viewModel.matricula,
                binding.radioAtivo.isChecked,
                viewModel.token,
            )
        }

        // Coloca a barra de atualização como visivel
        viewModel.status.observe(viewLifecycleOwner) { status ->
            when (status) {
                DialogEditargerentecomercianteViewModel.ApiStatus.LOADING -> {
                    estadoCarregando()
                }
                DialogEditargerentecomercianteViewModel.ApiStatus.DONE -> {
                    if (viewModel.response.value!!.b){
                        enviaDadosParaFragment(viewModel.response.value!!)
                    }
                    else{
                        // Mostra animação que não deu certo
                    }
                    estadoResultados(viewModel.response.value!!.b)
                }
                DialogEditargerentecomercianteViewModel.ApiStatus.ERROR -> {
                    estadoResultados(viewModel.response.value!!.b)
                }
            }
        }

        // Configura o ciclo de vida
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    private fun enviaDadosParaFragment(result: ParBoolString) {
        val bundle = bundleOf("resultBollean" to result.b)
        bundle.putString("resultString", result.s)
        setFragmentResult("dialogEditarComerciante", bundle)
    }

    private fun estadoCarregando() {
        binding.Bar.visibility = View.VISIBLE
        binding.EstadoNormal.visibility = View.GONE
        binding.resultados.visibility = View.GONE
    }

    private fun estadoResultados(usuarioEditado: Boolean?) {
        binding.Bar.visibility = View.GONE

        binding.btnSalvar.visibility = View.GONE

        binding.EstadoNormal.visibility = View.GONE
        binding.resultados.visibility = View.VISIBLE
        binding.result.visibility = View.INVISIBLE

        if (usuarioEditado!!) {
            binding.ImageStatusSucess.visibility = View.VISIBLE
            binding.ImageStatusSucess.animate().apply {
                duration = 1000
                rotationYBy(360f)
            }.withEndAction {
                binding.usuarioInserido.visibility = View.VISIBLE
                binding.result.visibility = View.VISIBLE
                binding.result.text = "Gerente editado"
            }
        } else {
            binding.ImageStatusError.visibility = View.VISIBLE
            binding.ImageStatusError.animate().apply {
                duration = 1000
                rotationYBy(360f)
            }.withEndAction {
                binding.usuarioNaoInserido.visibility = View.VISIBLE
                binding.result.visibility = View.VISIBLE
            }
        }
    }


}
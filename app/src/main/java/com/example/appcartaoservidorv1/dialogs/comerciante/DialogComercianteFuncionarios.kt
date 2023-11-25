package com.example.appcartaoservidorv1.dialogs.comerciante

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.DialogComercianteFuncionariosBinding
import com.example.appcartaoservidorv1.dialogsviewmodels.comerciante.DialogComercianteFuncionariosViewModel
import com.example.appcartaoservidorv1.dialogsviewmodels.comerciante.DialogComercianteFuncionariosViewModelFactory
import com.example.appcartaoservidorv1.services.redirecionamento.fromColaboradoresToFuncionario
import com.example.appcartaoservidorv1.services.redirecionamento.fromColaboradoresToGerente
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DialogComercianteFuncionarios : BottomSheetDialogFragment() {

    // Variavel responsavel pelo binding
    lateinit var binding: DialogComercianteFuncionariosBinding
    lateinit var args: DialogComercianteFuncionariosArgs

    // Variavel que representa o viewModel
    private lateinit var viewModel: DialogComercianteFuncionariosViewModel
    private lateinit var viewModelFactory: DialogComercianteFuncionariosViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.dialog_comerciante_funcionarios,
                container,
                false
            )

        // Recupera as variaveis passada para a view
        args = DialogComercianteFuncionariosArgs.fromBundle(requireArguments())

        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory =
            DialogComercianteFuncionariosViewModelFactory(args.matricula, args.token)
        viewModel =
            ViewModelProvider(
                this,
                viewModelFactory
            )[DialogComercianteFuncionariosViewModel::class.java]
        // Faz o binding com o viewModel
        binding.viewModel = viewModel

        //------------------------------------------------------------------------------------------
        // Botão Gerente
        binding.btnGerente.setOnClickListener {
            fromColaboradoresToGerente(this, viewModel.matricula, viewModel.token)
        }

        //------------------------------------------------------------------------------------------
        // Botão Funcionario
        binding.btnFuncionario.setOnClickListener {
            fromColaboradoresToFuncionario(this, viewModel.matricula, viewModel.token)
        }

        //------------------------------------------------------------------------------------------
        // Botão Voltar
        binding.btnVoltar.setOnClickListener {
            dialog?.dismiss()
        }

        //------------------------------------------------------------------------------------------
        // Configura o ciclo de vida
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
}
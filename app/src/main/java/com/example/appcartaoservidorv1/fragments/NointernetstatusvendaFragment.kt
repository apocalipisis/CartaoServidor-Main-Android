package com.example.appcartaoservidorv1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.FragmentNointernetstatusvendaBinding
import com.example.appcartaoservidorv1.fromNointernetstatusvendapageToComerciante
import com.example.appcartaoservidorv1.isNetworkAvailable
import com.example.appcartaoservidorv1.viewmodels.NointernetstatusvendaViewModel
import com.example.appcartaoservidorv1.viewmodels.NointernetstatusvendaViewModelFactory

class NointernetstatusvendaFragment : Fragment() {
    // Variavel responsavel pelo binding
    lateinit var binding: FragmentNointernetstatusvendaBinding

    // Variavel que representa o viewModel
    private lateinit var viewModel: NointernetstatusvendaViewModel
    private lateinit var viewModelFactory: NointernetstatusvendaViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_nointernetstatusvenda, container, false)
        // Recupera as variaveis passada para a view
        val args = NointernetstatusvendaFragmentArgs.fromBundle(requireArguments())
        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory = NointernetstatusvendaViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[NointernetstatusvendaViewModel::class.java]

        val appContex = this.requireContext()
        val fragment = this

        // Click listener para o btn Refresh
        binding.btnRefresh.setOnClickListener {
            if (isNetworkAvailable(appContex)) {
                fromNointernetstatusvendapageToComerciante(fragment, args.nome, args.matricula, args.token)
            }else{
                failAnimation()
            }
        }

        // Configura o botão de voltar para ao pressionar voltar para a página login
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isNetworkAvailable(appContex)) {
                    fromNointernetstatusvendapageToComerciante(fragment, args.nome, args.matricula, args.token)
                }else{
                    failAnimation()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)

        // Configura o ciclo de vida
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    // Mostra a animação
    private fun failAnimation(){
        binding.ImageStatusError.visibility = View.VISIBLE
        binding.ImageStatusError.animate().apply{
            duration = 1000
            rotationYBy(360f)
        }.withEndAction{
            binding.result.visibility = View.VISIBLE
            binding.result.text = "Sem conexão"
        }
    }
}
package com.example.appcartaoservidorv1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.FragmentNointernetBinding
import com.example.appcartaoservidorv1.services.utilidades.isNetworkAvailable
import com.example.appcartaoservidorv1.viewmodels.NointernetViewModel
import com.example.appcartaoservidorv1.viewmodels.NointernetViewModelFactory

class NointernetFragment : Fragment() {
    // Variavel responsavel pelo binding
    lateinit var binding: FragmentNointernetBinding

    // Variavel que representa o viewModel
    private lateinit var viewModel: NointernetViewModel
    private lateinit var viewModelFactory: NointernetViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_nointernet, container, false)

        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory = NointernetViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[NointernetViewModel::class.java]

        val appContex = this.requireContext()
        // Click listener para o btn Refresh
        binding.btnRefresh.setOnClickListener {
            if (isNetworkAvailable(appContex)) {
                NavHostFragment.findNavController(this).popBackStack()
            } else {
                failAnimation()
            }
        }

        binding.btnVoltar.setOnClickListener {
            if (isNetworkAvailable(appContex)) {
                NavHostFragment.findNavController(this).popBackStack()
            } else {
                failAnimation()
            }
        }

        val fragment = this
        // Configura o botão de voltar para ao pressionar voltar para a página login
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isNetworkAvailable(appContex)) {
                    NavHostFragment.findNavController(fragment).popBackStack()
                } else {
                    failAnimation()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback
        )

        // Configura o ciclo de vida
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    // Mostra a animação
    private fun failAnimation() {
        binding.image.visibility = View.VISIBLE
        binding.image.animate().apply {
            duration = 200
            rotationYBy(360f)
        }.withEndAction {
//            binding.mensagem.text = "Sem conexão"
        }
    }
}
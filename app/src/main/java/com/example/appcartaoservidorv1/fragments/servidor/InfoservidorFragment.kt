package com.example.appcartaoservidorv1.fragments.servidor

import android.os.Build
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.FragmentInfoservidorBinding
import com.example.appcartaoservidorv1.databinding.FragmentServidorBinding
import com.example.appcartaoservidorv1.viewmodels.servidor.InfoservidorViewModel
import com.example.appcartaoservidorv1.viewmodels.servidor.InfoservidorViewModelFactory
import com.example.appcartaoservidorv1.viewmodels.servidor.ServidorViewModel
import com.example.appcartaoservidorv1.viewmodels.servidor.ServidorViewModelFactory

class InfoservidorFragment : Fragment() {

    // Variavel responsavel pelo binding
    lateinit var binding: FragmentInfoservidorBinding

    // Variavel que representa o viewModel
    private lateinit var viewModel: InfoservidorViewModel
    private lateinit var viewModelFactory: InfoservidorViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Infla o layout do fragmento
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_infoservidor, container, false)
        // Recupera as variaveis passada para a view
        var args = InfoservidorFragmentArgs.fromBundle(requireArguments())
        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory = InfoservidorViewModelFactory(
            args.matricula,
            args.nome,
            args.cpf,
            args.tipoUsuario,
            args.status,
            args.instituto,
            args.limiteMensal.toDouble()
        )
        viewModel = ViewModelProvider(this, viewModelFactory).get(InfoservidorViewModel::class.java)
        // Faz o binding com o viewModel
        binding.viewModel = viewModel

        // Configura o ciclo de vida
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            getActivity()?.getColor(R.color.primaryColor)
//                ?.let { getActivity()?.getWindow()?.setStatusBarColor(it) }
//        };
//    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val inflater = TransitionInflater.from(requireContext())
//        enterTransition  = inflater.inflateTransition(R.transition.slide_in)
//        exitTransition  = inflater.inflateTransition(R.transition.slide_out)

//        val enter = R.anim.slide_in
//        val exit = R.anim.slide_out
//
//        val fragment = InfoservidorFragment()
//        val supportFragmentManager = fragment.parentFragmentManager
//        supportFragmentManager.beginTransaction().setCustomAnimations(enter, exit)
//            .replace(R.id.servidorFragment, fragment).addToBackStack(null)
//    }
}



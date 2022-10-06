package com.example.appcartaoservidorv1.fragments.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.Constantes
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.FragmentUsuarioinativoBinding
import com.example.appcartaoservidorv1.services.utilidades.fromUsuarioinativoToLogin
import com.example.appcartaoservidorv1.viewmodels.login.UsuarioinativoViewModel
import com.example.appcartaoservidorv1.viewmodels.login.UsuarioinativoViewModelFactory


class UsuarioinativoFragment : Fragment() {
    // Variavel responsavel pelo binding
    lateinit var binding: FragmentUsuarioinativoBinding
    lateinit var args: UsuarioinativoFragmentArgs

    // Variavel que representa o viewModel
    private lateinit var viewModel: UsuarioinativoViewModel
    private lateinit var viewModelFactory: UsuarioinativoViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_usuarioinativo, container, false)
        // Recupera as variaveis passada para a view
        args = UsuarioinativoFragmentArgs.fromBundle(requireArguments())
        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory = UsuarioinativoViewModelFactory(args.nome, args.matricula)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[UsuarioinativoViewModel::class.java]
        // Faz o binding com o viewModel
        binding.viewModel = viewModel

        val fragment = this

        binding.btnVoltar.setOnClickListener {
            fromUsuarioinativoToLogin(fragment)
        }

        // Configura o botão de voltar para ao pressionar voltar para a página login
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                fromUsuarioinativoToLogin(fragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback
        )

        // Ao clickar abre o whatsapp
        binding.btnWhatsapp.setOnClickListener { whatsapp() }

        // Ao clickar abre o whatsapp
        binding.btnLigar.setOnClickListener { telefone() }

        // Configura o ciclo de vida
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    private fun whatsAppItent(): Intent {
        val url =
            "https://wa.me/${Constantes.TelefoneWhats}?text=Olá,%20meu%20nome%20é%20${viewModel.nome}%20e%20estou%20no%20sistema%20com%20matricula%20${viewModel.matricula}%20estou%20aparecendo%20como%20inativo,%20gostaria%20de%20saber%20o%20que%20pode%20ser%20feito."

        val shareIntent = Intent(Intent.ACTION_VIEW)
        shareIntent.data = Uri.parse(url)

        return shareIntent
    }

    private fun whatsapp() {
        startActivity(whatsAppItent())
    }

    private fun telefoneIntent(): Intent {
        return Intent(
            Intent.ACTION_DIAL,
            Uri.parse("tel:" + Constantes.TelefoneApp)
        )
    }

    private fun telefone() {
        startActivity(telefoneIntent())
    }
}
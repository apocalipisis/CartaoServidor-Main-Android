package com.example.appcartaoservidorv1.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.FragmentLoginBinding
import com.example.appcartaoservidorv1.fragments.servidor.ServidorFragmentDirections
import com.example.appcartaoservidorv1.models.DTO_Login
import com.example.appcartaoservidorv1.viewmodels.LoginViewModel
import com.example.appcartaoservidorv1.viewmodels.LoginViewModelFactory

class LoginFragment : Fragment() {
    // Variavel responsavel pelo binding
    lateinit var binding: FragmentLoginBinding

    // Variavel que representa o viewModel
    private lateinit var viewModel: LoginViewModel
    private lateinit var viewModelFactory: LoginViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla o layout do fragmento
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        // Inicializa as variaveis do ViewModel
        viewModelFactory = LoginViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)
        // Faz o binding com o viewModel
        binding.viewModel = viewModel
        // Observa se ocorreu mudança no login e redireciona para a página correta
        viewModel._login.observe(viewLifecycleOwner, Observer<DTO_Login> { login ->
            login(
                login.bancoDeDadosOk,
                login.loginAutorizado,
                login.usuarioAtivo,
                login.nomeUsuario,
                login.tipoUsuario,
                viewModel.Matricula.value.toString()
            )
        })
        // Observa as consulta a API e muda o status da barra
        viewModel.status.observe(viewLifecycleOwner, Observer<LoginViewModel.ApiStatus> { status ->
            when (status) {
                LoginViewModel.ApiStatus.LOADING -> {
                    mostrarBarra()
                }
                LoginViewModel.ApiStatus.DONE -> {
                    esconderBarra()
                }
                LoginViewModel.ApiStatus.ERROR -> {
                    esconderBarra()
                    Toast.makeText(
                        context,
                        "Problemas no servidor tente novamente em alguns instantes",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    // Caso o login seja válido, redireciona o usuário  para a página apropriada
    private fun redirectUsuario(matricula: String, nome: String, tipo: String) {
        when (tipo) {
            "Servidor" -> {
                val action =
                    LoginFragmentDirections.actionLoginFragmentToServidorFragment(
                        matricula,
                        nome
                    )
                NavHostFragment.findNavController(this).navigate(action)
            }
            "Comerciante" -> {
                val action =
                    LoginFragmentDirections.actionLoginFragmentToComercianteFragment(
                        matricula,
                        nome
                    )
                NavHostFragment.findNavController(this).navigate(action)
            }
            "Prefeitura" -> {
                val action =
                    LoginFragmentDirections.actionLoginFragmentToUsuarionaopermitidoFragment(
                        tipo
                    )
                NavHostFragment.findNavController(this).navigate(action)
            }
            "Admin" -> {
                val action =
                    LoginFragmentDirections.actionLoginFragmentToUsuarionaopermitidoFragment(
                        tipo
                    )
                NavHostFragment.findNavController(this).navigate(action)
            }
        }
    }

    // Verifica as condições de login e faz a ação apropriada (redirecionar ou exibir Toast)
    private fun login(
        bancoDeDadosOk: Boolean,
        loginAutorizado: Boolean,
        usuarioAtivo: Boolean,
        nome: String,
        tipoUsuario: String,
        matricula: String
    ) {

        if (!bancoDeDadosOk) {
            Toast.makeText(
                context,
                "Problemas no servidor tente novamente em alguns instantes",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        if (bancoDeDadosOk && !loginAutorizado) {
            Toast.makeText(context, "Credenciais incorretas", Toast.LENGTH_LONG).show()
            return
        }

        if (!usuarioAtivo) {
            goToUsuarioInativoPage(nome)
            return
        }

        redirectUsuario(matricula, nome, tipoUsuario)
    }

    // Função que redireciona o usuario para a pagina de usuário inativo
    private fun goToUsuarioInativoPage(nome: String) {
        val action =
            LoginFragmentDirections.actionLoginFragmentToUsuarioinativoFragment(
                nome,
            )
        NavHostFragment.findNavController(this).navigate(action)
    }

    // Mostra a barra de loading e esconde os demais campos
    private fun mostrarBarra() {
        binding.Bar.visibility = View.VISIBLE
        binding.Matricula.visibility = View.GONE
        binding.Senha.visibility = View.GONE
        binding.btnEntrar.visibility = View.GONE
    }

    // Esconde a barra de loading e mostra os demais campos
    private fun esconderBarra() {
        binding.Bar.visibility = View.GONE
        binding.Matricula.visibility = View.VISIBLE
        binding.Senha.visibility = View.VISIBLE
        binding.btnEntrar.visibility = View.VISIBLE
    }

}
package com.example.appcartaoservidorv1.fragments.login

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.*
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.*
import com.example.appcartaoservidorv1.databinding.FragmentLoginBinding
import com.example.appcartaoservidorv1.viewmodels.login.LoginViewModel
import com.example.appcartaoservidorv1.viewmodels.login.LoginViewModelFactory
import com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE
import java.io.File
import java.util.concurrent.Executor

class LoginFragment : Fragment() {
    // Variavel responsavel pelo binding
    lateinit var binding: FragmentLoginBinding

    // Variavel que representa o viewModel
    private lateinit var viewModel: LoginViewModel
    private lateinit var viewModelFactory: LoginViewModelFactory

    // Biometrics
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    private var saveBiometrics = false // Verifica se o usuário quer começar a entrar por biometria
    private var biometriaOn = false // Verifica se já havia a configuração para entrar por biometria
    private val arquivoSharedPreferences = Constantes.NomeArquivoSharedPreferences
    private var showBiometricOption: Boolean = true

    private lateinit var nomeBiometria: String
    private lateinit var senhaBiometria: String
    private lateinit var matriculaBiometria: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        // Inicializa as variaveis do ViewModel
        viewModelFactory = LoginViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]
        // Faz o binding com o viewModel
        binding.viewModel = viewModel

        val applicationContext = this.requireContext()

        // Redireciona o usuário
        viewModel.destino.observe(viewLifecycleOwner) { destino ->
            when (destino) {
                "UsuarioInativo" -> {
                    fromLoginToUsuarioinativo(
                        this,
                        viewModel.nome,
                        viewModel.matricula.value.toString()
                    )
                }
                "Servidor" -> {
                    salvarPreferencias(applicationContext, arquivoSharedPreferences)

                    fromLoginToServidor(
                        this,
                        viewModel.nome,
                        viewModel.matricula.value.toString(),
                        viewModel.token
                    )
                }
                "Comerciante" -> {
                    salvarPreferencias(applicationContext, arquivoSharedPreferences)

                    fromLoginToComerciante(
                        this,
                        viewModel.nome,
                        viewModel.matricula.value.toString(),
                        viewModel.token
                    )
                }
                "NãoAutorizado" -> {
                    fromLoginToUsuarionaopermitido(this)
                }
            }
        }

        // Caso de erro no login exibe o motivo
        viewModel.motivoLoginFail.observe(viewLifecycleOwner) { motivoLoginFail ->
            binding.Mensagem.text = motivoLoginFail
        }


        // Observa as consulta a API e muda o status da barra
        viewModel.status.observe(viewLifecycleOwner) { status ->
            when (status) {
                LoginViewModel.ApiStatus.LOADING -> {
                    mostrarBarra()
                }
                LoginViewModel.ApiStatus.DONE -> {
                    when (biometriaOn) {
                        true -> {
                            layoutBiometrics(nomeBiometria)
                        }
                        false -> {
                            layoutNormal()
                        }
                    }
                }
                LoginViewModel.ApiStatus.ERROR -> {
                    when (biometriaOn) {
                        true -> {
                            layoutBiometrics(nomeBiometria)
                        }
                        false -> {
                            layoutNormal()
                        }
                    }
                }
            }
        }
        checkBiometria(applicationContext, arquivoSharedPreferences)

        // ClickListener para o botão login
        binding.btnEntrar.setOnClickListener {
            binding.Mensagem.visibility = View.INVISIBLE
            if (isNetworkAvailable(this.requireContext())) {
                when (biometriaOn) {
                    true -> {
                        viewModel.matricula.value = matriculaBiometria
                        viewModel.senha.value = senhaBiometria
                        biometricPrompt.authenticate(promptInfo)
                    }
                    false -> {
                        viewModel.getApiResponse(
                            viewModel.matricula.value.toString(),
                            viewModel.senha.value.toString()
                        )

                    }
                }
            } else {
                goToNointernetpage(binding.root)
            }
        }

        binding.btnTrocarUsuario.setOnClickListener {
            deletarPreferencias(applicationContext, arquivoSharedPreferences)
            biometriaOn = false
            viewModel.matricula.value = ""
            viewModel.senha.value = ""
            layoutNormal()
        }

        binding.Biometria.setOnCheckedChangeListener { _, isChecked ->
            saveBiometrics = isChecked
        }

        // Biometrics
        val biometricManager = BiometricManager.from(applicationContext)
        when (biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS ->
                Log.d("MY_APP_TAG", "App can authenticate using biometrics.")
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                showBiometricOption = false
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
                showBiometricOption = false
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                // Prompts the user to create credentials that your app accepts.
                val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                    putExtra(
                        Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                        BIOMETRIC_STRONG or DEVICE_CREDENTIAL
                    )
                }
                startActivityForResult(enrollIntent, REQUEST_CODE)
            }
        }

        executor = ContextCompat.getMainExecutor(applicationContext)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)

                    viewModel.getApiResponse(
                        viewModel.matricula.value.toString(),
                        viewModel.senha.value.toString()
                    )
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(Constantes.NomeSistema)
            .setSubtitle("Desbloqueie seu celular")
            .setAllowedAuthenticators(BIOMETRIC_WEAK or DEVICE_CREDENTIAL)
            .build()


        // Ciclo de vida do fragment
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    // Mostra a barra de loading e esconde os demais campos
    private fun mostrarBarra() {
        // Barra
        binding.Bar.visibility = View.VISIBLE

        // Parte Normal
        binding.ImagemDigital.visibility = View.GONE
        binding.Matricula.visibility = View.GONE
        binding.Senha.visibility = View.GONE
        binding.Biometria.visibility = View.GONE

        // Parte Biometria
        binding.ImagemDigital.visibility = View.GONE
        binding.NomeBiometria.visibility = View.GONE
        binding.layoutTrocarUsuario.visibility = View.GONE

        // Btn Entrar e mensagem
        binding.Mensagem.visibility = View.GONE
        binding.btnEntrar.visibility = View.GONE
    }

    // Layout quando o usuário não marcou a opção usar biometria
    private fun layoutNormal() {
        // Barra
        binding.Bar.visibility = View.GONE

        // Parte Normal
        binding.ImagemDigital.visibility = View.VISIBLE
        binding.Matricula.visibility = View.VISIBLE
        binding.Senha.visibility = View.VISIBLE
        binding.Biometria.isVisible = showBiometricOption

        // Parte Biometria
        binding.ImagemDigital.visibility = View.GONE
        binding.NomeBiometria.visibility = View.GONE
        binding.layoutTrocarUsuario.visibility = View.GONE

        // Btn Entrar e mensagem
        binding.Mensagem.visibility = View.VISIBLE
        binding.btnEntrar.visibility = View.VISIBLE
    }

    // Layout quando o usuário marcou a opção usar biometria
    private fun layoutBiometrics(nomeBiometria: String) {
        // Barra
        binding.Bar.visibility = View.GONE

        // Parte Normal
        binding.ImagemDigital.visibility = View.GONE
        binding.Matricula.visibility = View.GONE
        binding.Senha.visibility = View.GONE
        binding.Biometria.visibility = View.GONE

        // Parte Biometria
        binding.ImagemDigital.visibility = View.VISIBLE
        binding.NomeBiometria.visibility = View.VISIBLE
        binding.NomeBiometria.text = "Olá, $nomeBiometria"
        binding.textTrocarUsuario.text = "Não é ${nomeBiometria}?"
        binding.layoutTrocarUsuario.visibility = View.VISIBLE

        // Btn Entrar e mensagem
        binding.Mensagem.visibility = View.VISIBLE
        binding.btnEntrar.visibility = View.VISIBLE
    }


    // Muda o layout da pagina de acordo com o que o usuário escolheu
    private fun checkBiometria(context: Context, name: String) {
        when (isBiometriaOn(context, name)) {
            true -> {
                biometriaOn = true
                getVariablesBiometria(context, name)
                layoutBiometrics(nomeBiometria)
            }
            false -> {
                biometriaOn = false
                layoutNormal()
            }
        }
    }

    private fun isBiometriaOn(context: Context, name: String): Boolean {
        // Recupera uma instancia do shared pref
        val sharedPref = context.getSharedPreferences(name, Context.MODE_PRIVATE)
        // Verifica se a chave BiometriaOn existe no sharedPreference
        return sharedPref.getBoolean("BiometriaOn", false)
    }

    // Salva as prefderencias no shared preference
    private fun salvarPreferencias(
        context: Context,
        name: String,
    ) {
        if (biometriaOn)
            return
        if (!saveBiometrics)
            return

        val sharedPref = context.getSharedPreferences(name, Context.MODE_PRIVATE)
        with(sharedPref.edit())
        {
            putBoolean("BiometriaOn", true)
            putString("Nome", viewModel.nome)
            putString("Senha", viewModel.senha.value)
            putString("Matricula", viewModel.matricula.value)
            apply()
        }
    }

    // Salva as prefderencias no shared preference
    private fun deletarPreferencias(context: Context, name: String): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return context.deleteSharedPreferences(name)
        } else {
            context.getSharedPreferences(name, Context.MODE_PRIVATE).edit().clear().commit()
            val dir = File(context.applicationInfo.dataDir, "shared_prefs")
            return File(dir, "$name.xml").delete()
        }
    }

    // Recupera as variaveis do shared preference
    private fun getVariablesBiometria(context: Context, name: String) {
        val sharedPref = context.getSharedPreferences(name, Context.MODE_PRIVATE)

        nomeBiometria = sharedPref.getString("Nome", "teste").toString()
        senhaBiometria = sharedPref.getString("Senha", "teste").toString()
        matriculaBiometria = sharedPref.getString("Matricula", "teste").toString()
    }


}
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
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.Constantes
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.FragmentLoginBinding
import com.example.appcartaoservidorv1.models.dto.DTOLogin
import com.example.appcartaoservidorv1.services.utilidades.*
import com.example.appcartaoservidorv1.viewmodels.login.LoginViewModel
import com.example.appcartaoservidorv1.viewmodels.login.LoginViewModelFactory
import com.google.zxing.integration.android.IntentIntegrator
import java.io.File
import java.util.concurrent.Executor


class LoginFragment : BaseFragment() {
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

        // Display o estado de erro
        viewModel.isCardStateErro.observe(viewLifecycleOwner) { isErro ->
            estadoCard(isErro)
        }

        val applicationContext = this.requireContext()

        // Coloca a barra de atualização como visivel
        viewModel.response.observe(viewLifecycleOwner) { response ->
            if (response.b) {
                viewModel.setCardStateErro(false)
            } else {
                viewModel.setCardStateErro(true)
            }
        }

        fun processResponse(response: DTOLogin) {
            Log.i("Teste", "Processando a resposta")
            if (response.b) {
                if (!response.usuarioAtivo) {
                    fromLoginToUsuarioinativo(
                        this,
                        response.nomeUsuario,
                        viewModel.matricula.value.toString()
                    )
                }

                salvarPreferencias(applicationContext, arquivoSharedPreferences)
                App.iniciarSessao()

                when (response.tipoUsuario) {
                    "Servidor" -> {
                        fromLoginToServidor(
                            this,
                            response.nomeUsuario,
                            viewModel.matricula.value.toString(),
                            response.token
                        )
                    }
                    "Comerciante" -> {
                        fromLoginToComerciante(
                            this,
                            response.nomeUsuario,
                            viewModel.matricula.value.toString(),
                            response.token
                        )
                    }
                    "ComercianteGerente" -> {
                        fromLoginToComerciantegerente(
                            this,
                            response.nomeUsuario,
                            viewModel.matricula.value.toString(),
                            response.token
                        )
                    }
                    "ComercianteFuncionario" -> {
                        fromLoginToComerciantefuncionario(
                            this,
                            response.nomeUsuario,
                            viewModel.matricula.value.toString(),
                            response.token
                        )
                    }
                    else -> {
                        fromLoginToUsuarionaopermitido(this)
                    }
                }
            } else {
                viewModel.setCardStateErro(true)
                if (biometriaOn) {
                    estadoOkTextBiometrics(nomeBiometria)
                } else {
                    estadoOkTextFields()
                }
            }
        }

        // Coloca a barra de atualização como visivel
        viewModel.status.observe(viewLifecycleOwner) { status ->
            when (status) {
                LoginViewModel.ApiStatus.LOADING -> {
                    estadoCarregando()
                }
                LoginViewModel.ApiStatus.DONE -> {
                    processResponse(viewModel.response.value!!)
                }
                else -> {
                    viewModel.setCardStateErro(true)
                    if (biometriaOn) {
                        estadoOkTextBiometrics(nomeBiometria)
                    } else {
                        estadoOkTextFields()
                    }
                }
            }
        }

        fun matriculaOk(): Boolean {
            if (viewModel.matricula.value?.length != 5)
                return false


            return true
        }

        fun senhaOk(): Boolean {
            if (viewModel.senha.value.isNullOrEmpty())
                return false

            return true
        }

        // ClickListener para o botão login
        binding.btnEntrar.setOnClickListener {
            estadoCard(false)
            var isOk = true

            when (biometriaOn) {
                true -> {
                    viewModel.matricula.value = matriculaBiometria
                    viewModel.senha.value = senhaBiometria
                    biometricPrompt.authenticate(promptInfo)
                }
                false -> {

                    if (!matriculaOk()) {
                        viewModel.setMensagem(Constantes.Erro5)
                        viewModel.setCardStateErro(true)
                        isOk = false
                    }

                    if (isOk) {
                        if (!senhaOk()) {
                            viewModel.setMensagem(Constantes.Erro6)
                            viewModel.setCardStateErro(true)
                            isOk = false
                        }
                    }

                    if (isOk) {
                        viewModel.request(
                            viewModel.matricula.value.toString(),
                            viewModel.senha.value.toString()
                        )

                    }
                }
            }
//            viewModel.matricula.value = "00002"
//            viewModel.senha.value = "1234"
            viewModel.request(
                viewModel.matricula.value.toString(),
                viewModel.senha.value.toString()
            )
        }

        binding.btnTrocarUsuario.setOnClickListener {
            deletarPreferencias(applicationContext, arquivoSharedPreferences)
            biometriaOn = false
            viewModel.matricula.value = ""
            viewModel.senha.value = ""
            estadoOkTextFields()
        }

        binding.Biometria.setOnCheckedChangeListener { _, isChecked ->
            saveBiometrics = isChecked
        }
        checkBiometria(applicationContext, arquivoSharedPreferences)

        // Biometrics
        val biometricManager = BiometricManager.from(applicationContext)
        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)) {
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
                        BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL
                    )
                }
                startActivityForResult(enrollIntent, IntentIntegrator.REQUEST_CODE)
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

                    viewModel.request(
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
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_WEAK or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
            .build()

        val fm = fragmentManager
        for (entry in 0 until fm!!.backStackEntryCount) {
            Log.i("Navigation", "Found fragment: " + fm.getBackStackEntryAt(entry).id)
        }


        // Ciclo de vida do fragment
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    //----------------------------------------------------------------------------------------------
    private fun estadoCard(isErro: Boolean) {
        if (isErro) {
            binding.cardCentral.strokeWidth = 3
            binding.mensagem.visibility = View.VISIBLE
        } else {
            binding.cardCentral.strokeWidth = 0
            binding.mensagem.visibility = View.INVISIBLE
        }
    }

    //----------------------------------------------------------------------------------------------
    private fun layoutOkTextFields(isVisible: Boolean) {
        if (isVisible) {
            binding.layoutOkTextFields.visibility = View.VISIBLE
        } else {
            binding.layoutOkTextFields.visibility = View.GONE
        }
    }

    //----------------------------------------------------------------------------------------------
    private fun layoutOkBiometrics(isVisible: Boolean) {
        if (isVisible) {
            binding.layoutOkBiometrics.visibility = View.VISIBLE
        } else {
            binding.layoutOkBiometrics.visibility = View.GONE
        }
    }

    //----------------------------------------------------------------------------------------------
    private fun layoutCarregando(isVisible: Boolean) {
        if (isVisible) {
            binding.layoutCarregando.visibility = View.VISIBLE
        } else {
            binding.layoutCarregando.visibility = View.GONE
        }
    }

    //----------------------------------------------------------------------------------------------
    private fun layoutBtnBiometrics(isVisible: Boolean) {
        if (isVisible) {
            binding.layoutBtnBiometrics.visibility = View.VISIBLE
        } else {
            binding.layoutBtnBiometrics.visibility = View.GONE
        }
    }

    //----------------------------------------------------------------------------------------------
    private fun layoutBtnTrocarUsuario(isVisible: Boolean) {
        if (isVisible) {
            binding.layoutBtnTrocarUsuario.visibility = View.VISIBLE
        } else {
            binding.layoutBtnTrocarUsuario.visibility = View.GONE
        }
    }

    //----------------------------------------------------------------------------------------------
    private fun btnEntrar(isVisible: Boolean) {
        if (isVisible) {
            binding.btnEntrar.visibility = View.VISIBLE
        } else {
            binding.btnEntrar.visibility = View.GONE
        }
    }

    //----------------------------------------------------------------------------------------------
    private fun mensagem(isVisible: Boolean) {
        if (isVisible) {
            binding.mensagem.visibility = View.VISIBLE
        } else {
            binding.mensagem.visibility = View.INVISIBLE
        }
    }
    //----------------------------------------------------------------------------------------------

    private fun estadoCarregando() {
        layoutOkTextFields(false)
        layoutOkBiometrics(false)
        layoutCarregando(true)
        layoutBtnBiometrics(false)
        layoutBtnTrocarUsuario(false)
        btnEntrar(false)
    }

    private fun estadoOkTextFields() {
        layoutOkTextFields(true)
        layoutOkBiometrics(false)
        layoutCarregando(false)
        layoutBtnBiometrics(true)
        layoutBtnTrocarUsuario(false)
        btnEntrar(true)
    }

    private fun estadoOkTextBiometrics(nome: String) {
        layoutOkTextFields(false)
        layoutOkBiometrics(true)
        layoutCarregando(false)
        layoutBtnBiometrics(false)
        layoutBtnTrocarUsuario(true)
        btnEntrar(true)

        binding.nomeBiometrics.text = nome
    }

    // Muda o layout da pagina de acordo com o que o usuário escolheu
    private fun checkBiometria(context: Context, name: String) {
        when (isBiometriaOn(context, name)) {
            true -> {
                biometriaOn = true
                getVariablesBiometria(context, name)
                estadoOkTextBiometrics(nomeBiometria)
            }
            false -> {
                biometriaOn = false
                estadoOkTextFields()
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
            putString("Nome", viewModel.response.value?.nomeUsuario)
            putString("Senha", viewModel.senha.value)
            putString("Matricula", viewModel.matricula.value)
            apply()
        }
    }

    // Salva as prefderencias no shared preference
    private fun deletarPreferencias(context: Context, name: String): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.deleteSharedPreferences(name)
        } else {
            context.getSharedPreferences(name, Context.MODE_PRIVATE).edit().clear().apply()
            val dir = File(context.applicationInfo.dataDir, "shared_prefs")
            File(dir, "$name.xml").delete()
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
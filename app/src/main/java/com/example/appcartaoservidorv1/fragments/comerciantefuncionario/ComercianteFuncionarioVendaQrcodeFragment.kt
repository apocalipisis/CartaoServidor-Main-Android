package com.example.appcartaoservidorv1.fragments.comerciantefuncionario

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.FragmentComerciantefuncionarioVendaQrcodeBinding
import com.example.appcartaoservidorv1.models.auxiliares.TStrings
import com.example.appcartaoservidorv1.services.qrcode.QRCodeFoundListener
import com.example.appcartaoservidorv1.services.qrcode.QRCodeImageAnalyzer
import com.example.appcartaoservidorv1.services.redirecionamento.fromComerciantefuncionarioQrcodeToInserirsenha
import com.example.appcartaoservidorv1.services.utilidades.BaseFragment
import com.example.appcartaoservidorv1.viewmodels.comerciantefuncionario.ComercianteFuncionarioVendaQrcodeViewModel
import com.example.appcartaoservidorv1.viewmodels.comerciantefuncionario.ComercianteFuncionarioVendaQrcodeViewModelFactory
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class ComercianteFuncionarioVendaQrcodeFragment : BaseFragment() {
    // Variavel responsavel pelo binding
    lateinit var binding: FragmentComerciantefuncionarioVendaQrcodeBinding
    lateinit var args: ComercianteFuncionarioVendaQrcodeFragmentArgs

    // Variavel que representa o viewModel
    private lateinit var viewModel: ComercianteFuncionarioVendaQrcodeViewModel
    private lateinit var viewModelFactory: ComercianteFuncionarioVendaQrcodeViewModelFactory

    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var previewView: PreviewView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_comerciantefuncionario_venda_qrcode, container, false)
        // Recupera as variaveis passada para a view

        args = ComercianteFuncionarioVendaQrcodeFragmentArgs.fromBundle(requireArguments())
        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory = ComercianteFuncionarioVendaQrcodeViewModelFactory(
            args.valor,
            args.matriculaComerciante,
            args.nomeComerciante,
            args.matriculaVendedor,
            args.nomeVendedor,
            args.token
        )
        viewModel =
            ViewModelProvider(this, viewModelFactory)[ComercianteFuncionarioVendaQrcodeViewModel::class.java]
        // Faz o binding com o viewModel
        binding.viewModel = viewModel

        // Configurações iniciais para o uso da camera
        cameraProviderFuture = ProcessCameraProvider.getInstance(this.requireContext())
        previewView = binding.activityMainPreviewView
        requestCamera()

        // Botão Voltar
        binding.btnVoltar.setOnClickListener {
            findNavController().navigateUp()
        }

        // Configura o ciclo de vida
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted. Continue the action or workflow in your
                // app.
                startCamera()
            } else {
                // Explain to the user that the feature is unavailable because the
                // features requires a permission that the user has denied. At the
                // same time, respect the user's decision. Don't link to system
                // settings in an effort to convince the user to change their
                // decision.
            }
        }

    private fun requestCamera() {
        when {
            ContextCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                // You can use the API that requires the permission.
                startCamera()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                // In an educational UI, explain to the user why your app requires this
                // permission for a specific feature to behave as expected. In this UI,
                // include a "cancel" or "no thanks" button that allows the user to
                // continue using your app without granting the permission.
                requestPermissionLauncher.launch(
                    Manifest.permission.CAMERA
                )
            }
            else -> {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                requestPermissionLauncher.launch(
                    Manifest.permission.CAMERA
                )
            }
        }
    }

    private fun startCamera() {
        cameraProviderFuture.addListener({
            try {
                val cameraProvider: ProcessCameraProvider =
                    cameraProviderFuture.get() as ProcessCameraProvider
                bindCameraPreview(cameraProvider)
            } catch (e: Exception) {
                Toast.makeText(context, "Error starting camera", Toast.LENGTH_SHORT)
                    .show()
            } catch (e: InterruptedException) {
                Toast.makeText(context, "Error starting camera", Toast.LENGTH_SHORT)
                    .show()

            }
        }, ContextCompat.getMainExecutor(this.requireContext()))
    }

    // Armazena o fragmento em uma variavel
    val fragment = this
    private fun bindCameraPreview(cameraProvider: ProcessCameraProvider) {
        previewView.implementationMode = PreviewView.ImplementationMode.PERFORMANCE
        val preview: Preview = Preview.Builder()
            .build()
        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()
        preview.setSurfaceProvider(previewView.surfaceProvider)

        val imageAnalysis = ImageAnalysis.Builder()
            .setTargetResolution(Size(1280, 720))
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()

        imageAnalysis.setAnalyzer(
            ContextCompat.getMainExecutor(this.requireContext()),
            QRCodeImageAnalyzer(object : QRCodeFoundListener {
                override fun onQRCodeFound(qrCode: String) {
                    try {
                        val obj = Json.decodeFromString<TStrings>(qrCode)
                        val nome = obj.str1
                        val matricula = obj.str2
                        val cartao = obj.str3

                        fromComerciantefuncionarioQrcodeToInserirsenha(
                            fragment,
                            matricula,
                            nome,
                            viewModel.valor,
                            viewModel.matriculaComerciante,
                            viewModel.nomeComerciante,
                            viewModel.matriculaVendedor,
                            viewModel.nomeVendedor,
                            cartao,
                            viewModel.token,
                        )
                    } catch (e: Exception) {
                        setTextQRcodeInvalido()
                    }
                }

                override fun qrCodeNotFound() {
                }
            })
        )

        val camera: Unit =
            cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            this as LifecycleOwner,
            cameraSelector,
            imageAnalysis,
            preview
        )
    }

    private fun setTextQRcodeInvalido() {
        if (binding.status.text != getString(R.string.QrCodeInvalido)) {
            binding.status.text = getString(R.string.QrCodeInvalido)
        }
    }
}
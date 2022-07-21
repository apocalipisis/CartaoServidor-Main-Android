package com.example.appcartaoservidorv1.services.utilidades

import android.app.Application
import android.util.Log
import androidx.core.content.ContextCompat
import java.util.concurrent.*

class App : Application() {
    companion object {
        lateinit var fragment: BaseFragment

        // Create an executor that executes tasks in the main thread.
        private lateinit var mainExecutor: Executor

        // Create an executor that executes tasks in a background thread.
        private lateinit var backgroundExecutor: ScheduledExecutorService

        private lateinit var agendar : ScheduledFuture<*>

        fun iniciarSessao() {
            // Recupera os executors
            setExecutors()
            // Execute a task in the background thread
            Log.i("Teste","A contagem regressiva começou no background")
            agendar = backgroundExecutor.schedule({
                mainExecutor.execute {
                    fragment.encerrarSessao()
                }
            }, 1, TimeUnit.HOURS)
        }

        private fun setExecutors(){

            mainExecutor = ContextCompat.getMainExecutor(fragment.requireContext())

            backgroundExecutor = Executors.newSingleThreadScheduledExecutor()

        }

        fun atualizaFragment(fragmentInput: BaseFragment) {

            fragment = fragmentInput

        }
    }
}
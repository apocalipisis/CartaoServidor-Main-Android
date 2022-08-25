package com.example.appcartaoservidorv1.services.utilidades

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData


open class BaseFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.atualizaFragment(this)
    }

    override fun onResume() {
        super.onResume()
        val appContext = this.requireContext()

        if (doNavigation)
            fromFragmentToSessaoexpirada(this)

        if(!isNetworkAvailable(appContext))
        {
            goToNointernetpage(view!!)
        }
    }

    private var doNavigation = false

    fun encerrarSessao() {
        doNavigation = true

        fromFragmentToSessaoexpirada(this)
    }
}

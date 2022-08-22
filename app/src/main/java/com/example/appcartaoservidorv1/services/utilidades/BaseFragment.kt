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
        if (doNavigation)
            fromFragmentToSessaoexpirada(this)
    }

    private var doNavigation = false


    fun encerrarSessao() {
        doNavigation = true

        fromFragmentToSessaoexpirada(this)
    }
}

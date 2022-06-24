package com.example.appcartaoservidorv1

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.io.File
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*


// Função que coloca o saldo no formato de dinheiro e transforma em string
fun formatDinheiro(entrada: Double): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale.getDefault()) as DecimalFormat
    val symbols: DecimalFormatSymbols = formatter.decimalFormatSymbols
    symbols.setCurrencySymbol("") // Don't use null.
    formatter.decimalFormatSymbols = symbols
    return formatter.format(entrada)
}

// Função que formata a data e retorna o mês em extenso
fun dataEmMes(data: Date): String {
    val formatter = SimpleDateFormat("MMMM", Locale.getDefault())
    return formatter.format(data)
}

// Função que verifica se tem conexão com a Internet
fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val nw = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            //for other device how are able to connect with Ethernet
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            //for check internet over Bluetooth
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            else -> false
        }
    } else {
        return connectivityManager.activeNetworkInfo?.isConnected ?: false
    }
}

fun mostrarTeclado(context: Context, view: View) {
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(view, InputMethodManager.HIDE_IMPLICIT_ONLY);
}

fun esconderTeclado(context: Context, view: View) {
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}
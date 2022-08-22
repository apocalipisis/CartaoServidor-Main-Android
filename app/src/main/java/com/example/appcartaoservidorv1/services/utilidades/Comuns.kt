package com.example.appcartaoservidorv1.services.utilidades

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

fun formatDinheiro(entrada: Double): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale.getDefault()) as DecimalFormat
    val symbols: DecimalFormatSymbols = formatter.decimalFormatSymbols
    symbols.currencySymbol = ""
    formatter.decimalFormatSymbols = symbols
    return formatter.format(entrada)
}

// Função que formata a data e retorna o mês em extenso
fun dataEmMes(data: Date): String {
    val formatter = SimpleDateFormat("MMMM", Locale.getDefault())
    return formatter.format(data)
}

// Função que formata a data e retorna o mês em extenso
fun dataCompletaMesExtenso(data: Date): String {
    val formatter = SimpleDateFormat("dd/MMMM/yy - HH:mm", Locale("pt", "BR"))
    return formatter.format(data)
}

// Função que verifica se tem conexão com a Internet
fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
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

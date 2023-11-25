package com.example.appcartaoservidorv1.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.ItemTransacaoBinding
import com.example.appcartaoservidorv1.models.Transacao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1

class HistoricocomercianteAdpter(val clickListener: Transacao_Listener) :
    ListAdapter<DataItem, RecyclerView.ViewHolder>(TransacaoDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addHeaderAndSubmitList(list: List<Transacao>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(DataItem.Header)
                else -> listOf(DataItem.Header) + list.map { DataItem.TransacaoItem(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.TransacaoItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val transacaoItem = getItem(position) as DataItem.TransacaoItem
                holder.bind(transacaoItem.transacao, clickListener)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }


    class TextViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        companion object {
            fun from(parent: ViewGroup): TextViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.header_comerciante_vendas, parent, false)
                return TextViewHolder(view)
            }
        }
    }

    class ViewHolder private constructor(val binding: ItemTransacaoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Transacao, clickListener: Transacao_Listener) {
            binding.transacao = item
            binding.Nome.text = item.NomeComprador
            binding.Valor.text = formatSaldo(item.Valor) + " R$"
            binding.Data.text = formatData(item.DataVenda)
            binding.clickListener = clickListener
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemTransacaoBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }

        // Função que coloca o saldo no formato adequado e transforma em string
        fun formatSaldo(saldo: Double): String {
            val formatter = NumberFormat.getCurrencyInstance(Locale.getDefault()) as DecimalFormat
            val symbols: DecimalFormatSymbols = formatter.decimalFormatSymbols
            symbols.setCurrencySymbol("") // Don't use null.
            formatter.decimalFormatSymbols = symbols
            return formatter.format(saldo)
        }

        // Função que formata a data e retorna o mês em extenso
        fun formatData(data: Date): String {
            val formatter = SimpleDateFormat("dd/MMMM/yy - HH:mm", Locale("pt", "BR"))
            return formatter.format(data)
        }
    }

}





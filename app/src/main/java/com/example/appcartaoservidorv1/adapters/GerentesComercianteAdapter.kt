package com.example.appcartaoservidorv1.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.ItemGerenteBinding
import com.example.appcartaoservidorv1.models.Gerente
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1

class GerentesComercianteAdapter (val clickListener: GerenteListener) :
    ListAdapter<GerenteDados, RecyclerView.ViewHolder>(GerenteDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addHeaderAndSubmitList(list: List<Gerente>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(GerenteDados.Header)
                else -> listOf(GerenteDados.Header) + list.map { GerenteDados.GerenteItem(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is GerenteDados.Header -> ITEM_VIEW_TYPE_HEADER
            is GerenteDados.GerenteItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val gerenteItem = getItem(position) as GerenteDados.GerenteItem
                holder.bind(gerenteItem.gerente, clickListener)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }


    class TextViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        companion object {
            fun from(parent: ViewGroup): TextViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.header_gerente, parent, false)
                return TextViewHolder(view)
            }
        }
    }

    class ViewHolder private constructor(val binding: ItemGerenteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Gerente, clickListener: GerenteListener) {
            binding.gerente = item
            binding.Nome.text = item.Nome
            binding.Matricula.text = item.Matricula
            binding.Status.text = item.Status
            binding.clickListener = clickListener
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemGerenteBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }

        // Função que coloca o saldo no formato adequado e transforma em string
        private fun formatCpf(cpf: String): String {
            return cpf.substring(0,3)+"."+cpf.substring(3,6)+"."+cpf.substring(6,9)+"-"+cpf.substring(9,11)
        }
    }

}



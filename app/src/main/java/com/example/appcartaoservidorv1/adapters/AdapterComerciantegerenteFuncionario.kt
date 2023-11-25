package com.example.appcartaoservidorv1.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.ItemFuncionarioBinding
import com.example.appcartaoservidorv1.models.Funcionario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1

class AdapterComerciantegerenteFuncionario(val clickListener: FuncionarioListener) :
    ListAdapter<FuncionarioDados, RecyclerView.ViewHolder>(FuncionarioDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addHeaderAndSubmitList(list: List<Funcionario>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(FuncionarioDados.Header)
                else -> listOf(FuncionarioDados.Header) + list.map { FuncionarioDados.FuncionarioItem(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is FuncionarioDados.Header -> ITEM_VIEW_TYPE_HEADER
            is FuncionarioDados.FuncionarioItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val funcionarioItem = getItem(position) as FuncionarioDados.FuncionarioItem
                holder.bind(funcionarioItem.funcionario, clickListener)
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
                val view = layoutInflater.inflate(R.layout.header_funcionario, parent, false)
                return TextViewHolder(view)
            }
        }
    }

    class ViewHolder private constructor(val binding: ItemFuncionarioBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Funcionario, clickListener: FuncionarioListener) {
            binding.funcionario = item
            binding.Nome.text = item.Nome
            binding.Matricula.text = item.Matricula
            binding.Status.text = item.Status
            binding.clickListener = clickListener
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemFuncionarioBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }

        // Função que coloca o saldo no formato adequado e transforma em string
        private fun formatCpf(cpf: String): String {
            return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(
                6,
                9
            ) + "-" + cpf.substring(9, 11)
        }
    }

}


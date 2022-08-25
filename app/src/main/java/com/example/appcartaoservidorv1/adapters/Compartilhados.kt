package com.example.appcartaoservidorv1.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.appcartaoservidorv1.models.Funcionario
import com.example.appcartaoservidorv1.models.Gerente
import com.example.appcartaoservidorv1.models.Transacao

// Transação
//--------------------------------------------------------------------------------------------------
class TransacaoDiffCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}

sealed class DataItem {
    abstract val id: Long

    data class TransacaoItem(val transacao: Transacao) : DataItem() {
        override val id = transacao.Id
    }

    object Header : DataItem() {
        override val id = Long.MIN_VALUE
    }
}

class Transacao_Listener(val clickListener: (nTransacao: Transacao) -> Unit) {
    fun onClick(transacao: Transacao) = clickListener(transacao)
}

// Gerente
//--------------------------------------------------------------------------------------------------
class GerenteDiffCallback : DiffUtil.ItemCallback<GerenteDados>() {
    override fun areItemsTheSame(oldItem: GerenteDados, newItem: GerenteDados): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GerenteDados, newItem: GerenteDados): Boolean {
        return oldItem == newItem
    }
}

sealed class GerenteDados {
    abstract val id: Long

    data class GerenteItem(val gerente: Gerente) : GerenteDados() {
        override val id = gerente.Id
    }

    object Header : GerenteDados() {
        override val id = Long.MIN_VALUE
    }
}

class GerenteListener(val clickListener: (nGerente: Gerente) -> Unit) {
    fun onClick(gerente: Gerente) = clickListener(gerente)
}

// Funcionario
//--------------------------------------------------------------------------------------------------

class FuncionarioDiffCallback : DiffUtil.ItemCallback<FuncionarioDados>() {
    override fun areItemsTheSame(oldItem: FuncionarioDados, newItem: FuncionarioDados): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FuncionarioDados, newItem: FuncionarioDados): Boolean {
        return oldItem == newItem
    }
}

sealed class FuncionarioDados {
    abstract val id: Long

    data class FuncionarioItem(val funcionario: Funcionario) : FuncionarioDados() {
        override val id = funcionario.Id
    }

    object Header : FuncionarioDados() {
        override val id = Long.MIN_VALUE
    }
}

class FuncionarioListener(val clickListener: (nFuncionario: Funcionario) -> Unit) {
    fun onClick(funcionario: Funcionario) = clickListener(funcionario)
}
package com.example.appcartaoservidorv1.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.ItemComercioBinding
import com.example.appcartaoservidorv1.models.ComercianteModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1

class AdapterServidorComerciantes(val clickListener: ComercianteModelListener) :
    ListAdapter<ComercianteModelDados, RecyclerView.ViewHolder>(ComercianteModelDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addHeaderAndSubmitList(list: List<ComercianteModel>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(ComercianteModelDados.Header)
                else -> listOf(ComercianteModelDados.Header) + list.map {
                    ComercianteModelDados.ComercianteModelItem(
                        it
                    )
                }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ComercianteModelDados.Header -> ITEM_VIEW_TYPE_HEADER
            is ComercianteModelDados.ComercianteModelItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val comercianteModelItem =
                    getItem(position) as ComercianteModelDados.ComercianteModelItem
                holder.bind(comercianteModelItem.comercianteModel, clickListener)
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
                val view =
                    layoutInflater.inflate(R.layout.header_servidor_comerciante, parent, false)
                return TextViewHolder(view)
            }
        }
    }

    class ViewHolder private constructor(val binding: ItemComercioBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ComercianteModel, clickListener: ComercianteModelListener) {
            binding.comercianteModel = item
            binding.Nome.text = item.Nome
            binding.clickListener = clickListener
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemComercioBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

}

package com.enrico.nit3213.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.enrico.nit3213.databinding.ItemEntityBinding

class EntityAdapter(
    private val onItemClick: (Map<String, String>) -> Unit
) : ListAdapter<Map<String, String>, EntityAdapter.EntityViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntityViewHolder {
        val binding = ItemEntityBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return EntityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EntityViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class EntityViewHolder(
        private val binding: ItemEntityBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(entity: Map<String, String>) {
            val entries = entity.entries.filter { it.key != "description" }

            // First property as title
            binding.tvEntityTitle.text = entries.firstOrNull()?.value ?: "Entity"

            // Remaining properties as summary
            binding.tvEntityProperties.text = entries.drop(1).joinToString("\n") {
                "${it.key}: ${it.value}"
            }

            binding.root.setOnClickListener {
                onItemClick(entity)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Map<String, String>>() {
        override fun areItemsTheSame(
            oldItem: Map<String, String>,
            newItem: Map<String, String>
        ) = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: Map<String, String>,
            newItem: Map<String, String>
        ) = oldItem == newItem
    }
}
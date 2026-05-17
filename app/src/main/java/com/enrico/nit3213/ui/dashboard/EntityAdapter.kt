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

            binding.tvEntityTitle.text = entries.firstOrNull()?.value ?: "Entity"

            binding.tvEntityProperties.text = entries.drop(1).joinToString("\n") {
                "${it.key}: ${it.value}"
            }

            binding.root.setOnClickListener {
                it.animate()
                    .scaleX(0.95f)
                    .scaleY(0.95f)
                    .setDuration(100)
                    .withEndAction {
                        it.animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .setDuration(100)
                            .start()
                        onItemClick(entity)
                    }
                    .start()
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
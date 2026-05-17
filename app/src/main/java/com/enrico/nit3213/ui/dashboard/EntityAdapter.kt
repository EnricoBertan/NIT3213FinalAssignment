package com.enrico.nit3213.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.enrico.nit3213.databinding.ItemEntityBinding

class EntityAdapter(
    private val onItemClick: (Map<String, Any>) -> Unit
) : ListAdapter<Map<String, Any>, EntityAdapter.EntityViewHolder>(DiffCallback()) {

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

        fun bind(entity: Map<String, Any>) {
            android.util.Log.d("ENTITY", "Binding: $entity")

            val entries = entity.entries.toList()

            val titleEntry = entries.find { it.key == "event" }
                ?: entries.find { it.key == "name" }
                ?: entries.find { it.key == "title" }
                ?: entries.firstOrNull()

            val titleText = titleEntry?.value?.toString() ?: "Entity"
            android.util.Log.d("ENTITY", "Title: $titleText")

            binding.tvEntityTitle.text = titleText

            val properties = entries.filter {
                it.key != "description" && it.key != titleEntry?.key
            }

            binding.tvEntityProperties.text = properties.joinToString("\n") {
                val value = it.value
                val displayValue = if (value is Double && value == value.toLong().toDouble()) {
                    value.toLong().toString()
                } else {
                    value.toString()
                }
                "${it.key.replaceFirstChar { c -> c.uppercase() }}: $displayValue"
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

    class DiffCallback : DiffUtil.ItemCallback<Map<String, Any>>() {
        override fun areItemsTheSame(
            oldItem: Map<String, Any>,
            newItem: Map<String, Any>
        ) = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: Map<String, Any>,
            newItem: Map<String, Any>
        ) = oldItem == newItem
    }
}
package com.enrico.nit3213.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.enrico.nit3213.databinding.FragmentDetailsBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val entityJson = arguments?.getString("entityJson") ?: ""
        val type = object : TypeToken<Map<String, Any>>() {}.type
        val entity: Map<String, Any> = Gson().fromJson(entityJson, type)
        viewModel.setEntity(entity)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.entity.collect { entityMap ->
                if (entityMap.isNotEmpty()) {
                    displayEntity(entityMap)
                }
            }
        }
    }

    private fun formatValue(value: Any): String {
        return if (value is Double && value == value.toLong().toDouble()) {
            value.toLong().toString()
        } else {
            value.toString()
        }
    }

    private fun displayEntity(entity: Map<String, Any>) {
        val entries = entity.entries.toList()

        val titleEntry = entries.find { it.key == "event" }
            ?: entries.find { it.key == "name" }
            ?: entries.find { it.key == "title" }
            ?: entries.firstOrNull()

        binding.tvEntityTitle.text = titleEntry?.value?.toString() ?: "Details"

        val properties = entries.filter {
            it.key != "description" && it.key != titleEntry?.key
        }

        binding.tvProperties.text = properties.joinToString("\n") {
            "${it.key.replaceFirstChar { c -> c.uppercase() }}: ${formatValue(it.value)}"
        }

        binding.tvDescription.text = entity["description"]?.let { formatValue(it) }
            ?: "No description available"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
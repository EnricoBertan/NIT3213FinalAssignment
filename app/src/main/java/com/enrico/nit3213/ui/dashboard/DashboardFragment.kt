package com.enrico.nit3213.ui.dashboard

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.enrico.nit3213.R
import com.enrico.nit3213.databinding.FragmentDashboardBinding
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DashboardViewModel by viewModels()
    private lateinit var adapter: EntityAdapter
    private var keypass: String = ""
    private var allEntities: List<Map<String, String>> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        keypass = arguments?.getString("keypass") ?: ""

        setupRecyclerView()
        setupSearch()
        viewModel.loadDashboard(keypass)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.dashboardState.collect { state ->
                when (state) {
                    is DashboardViewModel.DashboardState.Loading -> {
                        binding.shimmerLayout.visibility = View.VISIBLE
                        binding.shimmerLayout.startShimmer()
                        binding.recyclerView.visibility = View.GONE
                        binding.tvError.visibility = View.GONE
                        binding.tvEntityCount.visibility = View.GONE
                        binding.tilSearch.visibility = View.GONE
                    }
                    is DashboardViewModel.DashboardState.Success -> {
                        binding.shimmerLayout.stopShimmer()
                        binding.shimmerLayout.visibility = View.GONE
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.tvError.visibility = View.GONE
                        binding.tvEntityCount.visibility = View.VISIBLE
                        binding.tilSearch.visibility = View.VISIBLE
                        allEntities = state.entities
                        adapter.submitList(state.entities)
                        binding.tvEntityCount.text = "${state.total} entities found"
                    }
                    is DashboardViewModel.DashboardState.Error -> {
                        binding.shimmerLayout.stopShimmer()
                        binding.shimmerLayout.visibility = View.GONE
                        binding.recyclerView.visibility = View.GONE
                        binding.tvError.visibility = View.VISIBLE
                        binding.tvError.text = state.message
                    }
                }
            }
        }
    }

    private fun setupSearch() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterEntities(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filterEntities(query: String) {
        if (query.isEmpty()) {
            adapter.submitList(allEntities)
        } else {
            val filtered = allEntities.filter { entity ->
                entity.values.any { it.contains(query, ignoreCase = true) }
            }
            adapter.submitList(filtered)
        }
    }

    private fun setupRecyclerView() {
        adapter = EntityAdapter { entity ->
            val entityJson = Gson().toJson(entity)
            val bundle = Bundle().apply {
                putString("entityJson", entityJson)
            }
            findNavController().navigate(
                R.id.action_dashboardFragment_to_detailsFragment,
                bundle
            )
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
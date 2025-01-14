package com.damolks.ouxynext.ui.debug

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.damolks.ouxynext.databinding.ActivityDebugBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DebugActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDebugBinding
    private val viewModel: DebugViewModel by viewModels()
    private lateinit var moduleAdapter: ModuleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDebugBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupObservers()
        setupClickListeners()
    }

    private fun setupRecyclerView() {
        moduleAdapter = ModuleAdapter(emptyList()) { moduleInfo ->
            viewModel.toggleModule(moduleInfo.id)
        }
        binding.modulesRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@DebugActivity)
            adapter = moduleAdapter
        }
    }

    private fun setupObservers() {
        viewModel.moduleList.observe(this) { modules ->
            moduleAdapter.updateModules(modules)
        }

        viewModel.events.observe(this) { event ->
            binding.eventLogTextView.append("\n${event}")
        }

        lifecycleScope.launch {
            viewModel.systemEvents.collect { event ->
                binding.eventLogTextView.append("\nSystem: ${event}")
            }
        }
    }

    private fun setupClickListeners() {
        binding.testEventButton.setOnClickListener {
            viewModel.emitTestEvent()
        }

        binding.clearLogButton.setOnClickListener {
            binding.eventLogTextView.text = ""
        }
    }
}
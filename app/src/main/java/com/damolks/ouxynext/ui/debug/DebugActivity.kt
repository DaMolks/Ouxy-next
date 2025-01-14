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
    private lateinit var eventLogAdapter: EventLogAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDebugBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerViews()
        setupObservers()
        setupClickListeners()
    }

    private fun setupRecyclerViews() {
        moduleAdapter = ModuleAdapter(emptyList()) { moduleInfo ->
            viewModel.toggleModule(moduleInfo.id)
        }
        binding.modulesRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@DebugActivity)
            adapter = moduleAdapter
        }

        eventLogAdapter = EventLogAdapter()
        binding.eventLogsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@DebugActivity)
            adapter = eventLogAdapter
        }
    }

    private fun setupObservers() {
        viewModel.moduleList.observe(this) { modules ->
            moduleAdapter.updateModules(modules)
        }

        viewModel.eventLogs.observe(this) { logs ->
            eventLogAdapter.submitList(logs)
            binding.eventLogsRecyclerView.scrollToPosition(logs.size - 1)
        }

        viewModel.events.observe(this) { event ->
            // Les événements sont maintenant gérés par l'adapter
        }

        lifecycleScope.launch {
            viewModel.systemEvents.collect { event ->
                // Les événements système sont maintenant gérés par l'adapter
            }
        }
    }

    private fun setupClickListeners() {
        binding.testEventButton.setOnClickListener {
            viewModel.emitTestEvent()
        }

        binding.clearLogButton.setOnClickListener {
            viewModel.clearLogs()
        }
    }
}
package com.damolks.ouxynext.testmodule

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.damolks.ouxynext.testmodule.databinding.ActivityTestModuleBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestModuleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTestModuleBinding
    private val viewModel: TestModuleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestModuleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupTestButtons()
        observeResults()
    }

    private fun setupTestButtons() {
        binding.apply {
            testEventButton.setOnClickListener {
                viewModel.testEventBus()
            }

            testDataButton.setOnClickListener {
                viewModel.testDataPersistence()
            }

            testCrashButton.setOnClickListener {
                viewModel.testErrorHandling()
            }

            testPermissionsButton.setOnClickListener {
                viewModel.testPermissions()
            }
        }
    }

    private fun observeResults() {
        viewModel.testResults.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}
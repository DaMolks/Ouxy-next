package com.ouxy.next

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
com.ouxy.next.databinding.ActivityMainBinding
import com.ouxy.core.api.event.EventBus
import com.ouxy.test.module.TestModule
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var eventBus: EventBus
    private lateinit var testModule: TestModule

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialiser le module de test
        testModule = TestModule(eventBus)

        binding.btnTestModule.setOnClickListener {
            lifecycleScope.launch {
                testModule.initialize()
                testModule.start()
                // On devrait voir les logs des événements ici
            }
        }
    }
}

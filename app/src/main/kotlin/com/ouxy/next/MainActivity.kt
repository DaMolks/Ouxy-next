package com.ouxy.next

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ouxy.next.databinding.ActivityMainBinding
import com.ouxy.core.api.event.Event
import com.ouxy.core.events.OuxyEventBus
import com.ouxy.test.module.TestModule
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var eventBus: OuxyEventBus
    private lateinit var testModule: TestModule

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialiser le bus d'événements
        eventBus = OuxyEventBus(lifecycleScope)

        // Initialiser le module de test
        testModule = TestModule(eventBus)

        // S'abonner aux événements du module
        lifecycleScope.launch {
            eventBus.subscribe("test.event") { event ->
                showToast(event.payload?.toString() ?: "Event sans message")
            }
        }

        // Configurer le bouton de test
        binding.btnTestModule.setOnClickListener {
            lifecycleScope.launch {
                testModule.initialize()
                testModule.start()
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

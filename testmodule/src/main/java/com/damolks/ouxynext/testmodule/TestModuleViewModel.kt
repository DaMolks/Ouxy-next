package com.damolks.ouxynext.testmodule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.damolks.ouxynext.core.Event
import com.damolks.ouxynext.core.EventBus
import com.damolks.ouxynext.core.repository.EventLogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestModuleViewModel @Inject constructor(
    private val eventBus: EventBus,
    private val eventLogRepository: EventLogRepository
) : ViewModel() {

    fun testEventBus() {
        viewModelScope.launch {
            val event = Event.ModuleEvent(
                moduleId = "test_module",
                type = "TEST_EVENT",
                data = mapOf("timestamp" to System.currentTimeMillis())
            )
            eventBus.emit(event)
            eventLogRepository.logEvent(
                moduleId = "test_module",
                eventType = "TEST_EVENT",
                data = mapOf("timestamp" to System.currentTimeMillis())
            )
        }
    }

    fun testDataPersistence() {
        viewModelScope.launch {
            eventLogRepository.logEvent(
                moduleId = "test_module",
                eventType = "DATA_TEST",
                data = mapOf("test_data" to "Données de test")
            )
        }
    }

    fun testErrorHandling() {
        viewModelScope.launch {
            eventLogRepository.logEvent(
                moduleId = "test_module",
                eventType = "ERROR_TEST",
                data = mapOf("error" to "Test d'erreur contrôlée")
            )
            throw RuntimeException("Test d'erreur contrôlée")
        }
    }

    fun testPermissions() {
        viewModelScope.launch {
            eventLogRepository.logEvent(
                moduleId = "test_module",
                eventType = "PERMISSION_TEST",
                data = mapOf("permission" to "storage")
            )
        }
    }
}
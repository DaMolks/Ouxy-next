package com.damolks.ouxynext.testmodule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.damolks.ouxynext.core.Event
import com.damolks.ouxynext.core.EventBus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestModuleViewModel @Inject constructor(
    private val eventBus: EventBus
) : ViewModel() {

    fun testEventBus() {
        viewModelScope.launch {
            eventBus.emit(Event.ModuleEvent(
                moduleId = "test_module",
                type = "TEST_EVENT",
                data = mapOf("timestamp" to System.currentTimeMillis())
            ))
        }
    }

    fun testDataPersistence() {
        viewModelScope.launch {
            eventBus.emit(Event.ModuleEvent(
                moduleId = "test_module",
                type = "DATA_TEST",
                data = mapOf("test_data" to "Données de test")
            ))
        }
    }

    fun testErrorHandling() {
        viewModelScope.launch {
            eventBus.emit(Event.ModuleEvent(
                moduleId = "test_module",
                type = "ERROR_TEST",
                data = mapOf("error" to "Test d'erreur contrôlée")
            ))
        }
    }

    fun testPermissions() {
        viewModelScope.launch {
            eventBus.emit(Event.ModuleEvent(
                moduleId = "test_module",
                type = "PERMISSION_TEST",
                data = mapOf("permission" to "storage")
            ))
        }
    }
}
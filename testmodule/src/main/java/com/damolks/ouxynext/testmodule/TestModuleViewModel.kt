package com.damolks.ouxynext.testmodule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val _testResults = MutableLiveData<String>()
    val testResults: LiveData<String> = _testResults

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
            _testResults.value = "Test d'événement réussi"
        }
    }

    fun testDataPersistence() {
        viewModelScope.launch {
            try {
                eventLogRepository.logEvent(
                    moduleId = "test_module",
                    eventType = "DATA_TEST",
                    data = mapOf("test_data" to "Données de test")
                )
                _testResults.value = "Test de persistence réussi"
            } catch (e: Exception) {
                _testResults.value = "Erreur: ${e.message}"
                e.printStackTrace()
            }
        }
    }

    fun testErrorHandling() {
        viewModelScope.launch {
            try {
                eventLogRepository.logEvent(
                    moduleId = "test_module",
                    eventType = "ERROR_TEST",
                    data = mapOf("error" to "Test d'erreur contrôlée")
                )
                _testResults.value = "Erreur loggée, déclenchement de l'exception..."
                // Simulation d'une erreur après un court délai pour que le log soit enregistré
                kotlinx.coroutines.delay(500)
                throw RuntimeException("Test d'erreur contrôlée")
            } catch (e: Exception) {
                eventLogRepository.logEvent(
                    moduleId = "test_module",
                    eventType = "ERROR_CAUGHT",
                    data = mapOf("error" to (e.message ?: "Erreur inconnue"))
                )
                _testResults.value = "Exception capturée: ${e.message}"
                throw e // On relance l'exception pour voir comment elle est gérée
            }
        }
    }

    fun testPermissions() {
        viewModelScope.launch {
            try {
                eventLogRepository.logEvent(
                    moduleId = "test_module",
                    eventType = "PERMISSION_TEST",
                    data = mapOf("permission" to "storage")
                )
                _testResults.value = "Test de permissions effectué"
            } catch (e: Exception) {
                _testResults.value = "Erreur: ${e.message}"
                e.printStackTrace()
            }
        }
    }
}
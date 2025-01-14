package com.damolks.ouxynext.testmodule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.damolks.ouxynext.core.Event
import com.damolks.ouxynext.core.EventBus
import com.damolks.ouxynext.core.repository.EventLogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestModuleViewModel @Inject constructor(
    private val eventBus: EventBus,
    private val eventLogRepository: EventLogRepository
) : ViewModel() {

    private val _testResults = MutableLiveData<String>()
    val testResults: LiveData<String> = _testResults

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        _testResults.postValue("Erreur capturée: ${exception.message}")
    }

    fun testEventBus() {
        viewModelScope.launch(errorHandler) {
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
            _testResults.value = "Event envoyé et loggé avec succès"
        }
    }

    fun testDataPersistence() {
        viewModelScope.launch(errorHandler) {
            eventLogRepository.logEvent(
                moduleId = "test_module",
                eventType = "DATA_TEST",
                data = mapOf("test_data" to "Données de test")
            )
            _testResults.value = "Données persistées avec succès"
        }
    }

    fun testErrorHandling() {
        viewModelScope.launch(errorHandler) {
            try {
                eventLogRepository.logEvent(
                    moduleId = "test_module",
                    eventType = "ERROR_TEST",
                    data = mapOf("error" to "Test d'erreur contrôlée")
                )
                _testResults.value = "Erreur loggée, déclenchement de l'exception..."
                // Simulation d'une erreur après un court délai
                kotlinx.coroutines.delay(500)
                throw RuntimeException("Test d'erreur contrôlée")
            } catch (e: Exception) {
                _testResults.value = "Exception capturée: ${e.message}"
            }
        }
    }

    fun testPermissions() {
        viewModelScope.launch(errorHandler) {
            eventLogRepository.logEvent(
                moduleId = "test_module",
                eventType = "PERMISSION_TEST",
                data = mapOf("permission" to "storage")
            )
            _testResults.value = "Test de permissions effectué"
        }
    }
}
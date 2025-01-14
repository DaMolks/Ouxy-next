package com.damolks.ouxynext.ui.debug

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.damolks.ouxynext.core.Event
import com.damolks.ouxynext.core.EventBus
import com.damolks.ouxynext.core.ModuleManager
import com.damolks.ouxynext.data.ModuleInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DebugViewModel @Inject constructor(
    private val moduleManager: ModuleManager,
    private val eventBus: EventBus
) : ViewModel() {

    val moduleList: LiveData<List<ModuleInfo>> = moduleManager.installedModules

    private val _events = MutableLiveData<String>()
    val events: LiveData<String> = _events

    private val _systemEvents = MutableSharedFlow<String>()
    val systemEvents = _systemEvents.asSharedFlow()

    init {
        viewModelScope.launch {
            eventBus.events.collect { event ->
                when (event) {
                    is Event.ModuleEvent -> _events.value = "Module ${event.moduleId}: ${event.type}"
                    is Event.SystemEvent -> _systemEvents.emit("${event.type}: ${event.data}")
                }
            }
        }
    }

    fun emitTestEvent() {
        viewModelScope.launch {
            eventBus.emit(Event.SystemEvent(
                type = "TEST",
                data = mapOf("timestamp" to System.currentTimeMillis())
            ))
        }
    }

    fun toggleModule(moduleId: String) {
        val module = moduleList.value?.find { it.id == moduleId }
        if (module?.isActive == true) {
            moduleManager.unloadModule(moduleId)
        } else {
            moduleManager.loadModule(moduleId)
        }
    }
}
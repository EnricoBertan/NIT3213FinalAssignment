package com.enrico.nit3213.ui.details

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor() : ViewModel() {

    private val _entity = MutableStateFlow<Map<String, Any>>(emptyMap())
    val entity: StateFlow<Map<String, Any>> = _entity

    fun setEntity(entity: Map<String, Any>) {
        _entity.value = entity
    }
}
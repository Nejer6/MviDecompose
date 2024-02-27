package com.example.mvidecomposetest.presentation

import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable

interface AddContactComponent {

    val model: StateFlow<Model>

    fun onUsernameChanged(username: String)

    fun onPhoneChanged(phone: String)

    fun onSaveContactClicked()

    @Serializable
    data class Model(
        val username: String,
        val phone: String
    )
}
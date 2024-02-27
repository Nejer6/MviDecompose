package com.example.mvidecomposetest.presentation

import com.arkivanov.mvikotlin.core.store.Store
import com.example.mvidecomposetest.domain.Contact

interface ContactListStore :
    Store<ContactListStore.Intent, ContactListStore.State, ContactListStore.Label> {

    sealed interface Intent {

        data object AddContact : Intent

        data class SelectContact(val contact: Contact) : Intent
    }

    sealed interface Label {

        data class EditContact(val contact: Contact) : Label

        data object AddContact : Label
    }

    data class State(
        val contactList: List<Contact>
    )
}
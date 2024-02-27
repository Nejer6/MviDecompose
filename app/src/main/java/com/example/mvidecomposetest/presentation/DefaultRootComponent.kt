package com.example.mvidecomposetest.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.example.mvidecomposetest.domain.Contact
import kotlinx.serialization.Serializable

class DefaultRootComponent(
    componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    val stack: Value<ChildStack<Config, ComponentContext>> = childStack(
        source = navigation,
        initialConfiguration = Config.ContactList,
        handleBackButton = true,
        childFactory = ::child,
        serializer = Config.serializer()
    )

    private fun child(
        config: Config,
        componentContext: ComponentContext
    ): ComponentContext {
        return when (config) {
            Config.AddContact -> DefaultAddContactComponent(
                componentContext,
                onContactSaved = navigation::pop
            )

            Config.ContactList -> DefaultContactListComponent(
                componentContext = componentContext,
                onAddContactRequested = {
                    navigation.push(Config.AddContact)
                },
                onEditingContactRequested = {
                    navigation.push(Config.EditContact(it))
                }
            )

            is Config.EditContact -> DefaultEditContactComponent(
                componentContext = componentContext,
                contact = config.contact,
                onContactSaved = navigation::pop
            )
        }
    }

    @Serializable
    sealed class Config {

        @Serializable
        data object ContactList : Config()

        @Serializable
        data object AddContact : Config()

        @Serializable
        data class EditContact(val contact: Contact) : Config()
    }
}

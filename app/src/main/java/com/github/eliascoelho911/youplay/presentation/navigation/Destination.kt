package com.github.eliascoelho911.youplay.presentation.navigation

import androidx.navigation.NamedNavArgument

sealed class Destination(private val name: String, private val arguments: List<NamedNavArgument>) {
    object CreateRoom : Destination(name = "createRoom", arguments = emptyList())

    object RoomDetails: Destination(name = "roomDetails", arguments = emptyList())

    object AccessRoom : Destination(name = "accessRoom", arguments = emptyList())

    val baseRoute: String
        get() {
            val argumentsJointed = arguments.joinToString(separator = "&", transform = {
                "${it.name}={${it.name}}"
            })
            return if (argumentsJointed.isNotEmpty())
                "$name?$argumentsJointed"
            else
                name
        }

    val route: (arguments: Map<String, Any>) -> String = { arguments ->
        validateArguments(arguments)

        baseRoute.apply {
            arguments.forEach { (key, value) ->
                replace("{$key}", "$value")
            }
        }
    }

    private fun validateArguments(arguments: Map<String, Any>) {
        fun isRequired(argument: NamedNavArgument) =
            (argument.argument.defaultValue != null) and argument.argument.isNullable.not()

        val requiredArgumentsNotSend = this.arguments.filter {
            isRequired(it) and !arguments.containsKey(it.name)
        }

        if (requiredArgumentsNotSend.isNotEmpty()) throw IllegalArgumentException("you did not submit the following required arguments: [${requiredArgumentsNotSend.joinToString()}]")
    }
}
package com.github.eliascoelho911.youplay.domain.usecases.user

import com.github.eliascoelho911.youplay.global.Messages
import com.github.eliascoelho911.youplay.domain.repositories.UserRepository
import com.github.eliascoelho911.youplay.domain.util.changeFailureMessage

class GetLoggedUser(
    private val userRepository: UserRepository,
    private val errorMessages: Messages.Error,
) {
    fun get() = userRepository.getLoggedUser().changeFailureMessage(errorMessages.default)
}
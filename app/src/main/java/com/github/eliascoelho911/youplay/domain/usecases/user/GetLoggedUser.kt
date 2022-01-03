package com.github.eliascoelho911.youplay.domain.usecases.user

import com.github.eliascoelho911.youplay.domain.repositories.UserRepository

class GetLoggedUser(
    userRepository: UserRepository,
) {
    val loggedUser = userRepository.loggedUser
}
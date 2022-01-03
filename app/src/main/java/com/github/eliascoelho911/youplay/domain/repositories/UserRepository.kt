package com.github.eliascoelho911.youplay.domain.repositories

import com.github.eliascoelho911.youplay.common.Resource
import com.github.eliascoelho911.youplay.domain.entities.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    val loggedUser: Flow<Resource<User>>
}
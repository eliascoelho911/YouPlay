package com.github.eliascoelho911.youplay.domain.repositories

import com.github.eliascoelho911.youplay.global.Resource
import com.github.eliascoelho911.youplay.domain.entities.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getLoggedUser(): Flow<Resource<User>>
}
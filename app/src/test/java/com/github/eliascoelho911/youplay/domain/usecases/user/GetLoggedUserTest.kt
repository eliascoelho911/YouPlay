package com.github.eliascoelho911.youplay.domain.usecases.user

import com.github.eliascoelho911.youplay.common.Resource
import com.github.eliascoelho911.youplay.common.collectResource
import com.github.eliascoelho911.youplay.domain.entities.User
import com.github.eliascoelho911.youplay.domain.repositories.UserRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetLoggedUserTest {
    @RelaxedMockK
    private lateinit var userRepository: UserRepository

    @InjectMockKs
    private lateinit var getLoggedUser: GetLoggedUser

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun getLoggedUser() {
        val user = User()

        every { userRepository.loggedUser } returns flowOf(Resource.success(user))

        runBlocking {
            getLoggedUser.loggedUser.collectResource {
                onSuccess {
                    assertEquals(user, it)
                }
            }
        }
    }
}
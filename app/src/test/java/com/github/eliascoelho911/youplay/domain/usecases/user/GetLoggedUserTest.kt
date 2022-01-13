package com.github.eliascoelho911.youplay.domain.usecases.user

import com.github.eliascoelho911.youplay.assertIsResourceSuccess
import com.github.eliascoelho911.youplay.common.Resource
import com.github.eliascoelho911.youplay.domain.entities.User
import com.github.eliascoelho911.youplay.domain.repositories.UserRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
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
    fun testGetLoggedUser() {
        val user = User()

        every { userRepository.getLoggedUser() } returns flowOf(Resource.success(user))

        runBlocking {
            getLoggedUser.get().collect {
                assertIsResourceSuccess(it, user)
            }
        }
    }
}
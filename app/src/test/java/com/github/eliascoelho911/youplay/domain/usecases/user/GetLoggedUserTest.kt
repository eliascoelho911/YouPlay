package com.github.eliascoelho911.youplay.domain.usecases.user

import com.github.eliascoelho911.youplay.BaseTest
import com.github.eliascoelho911.youplay.assertIsResourceSuccess
import com.github.eliascoelho911.youplay.util.Resource
import com.github.eliascoelho911.youplay.domain.entities.User
import com.github.eliascoelho911.youplay.domain.repositories.UserRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetLoggedUserTest: BaseTest() {
    @RelaxedMockK
    private lateinit var userRepository: UserRepository

    @InjectMockKs
    private lateinit var getLoggedUser: GetLoggedUser

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
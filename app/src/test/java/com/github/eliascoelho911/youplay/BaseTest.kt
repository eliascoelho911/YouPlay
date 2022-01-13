package com.github.eliascoelho911.youplay

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import org.junit.Before
import org.junit.Rule

abstract class BaseTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    open fun setup() {
        MockKAnnotations.init(this)
    }
}
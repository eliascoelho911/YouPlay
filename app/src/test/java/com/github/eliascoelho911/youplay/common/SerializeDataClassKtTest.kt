package com.github.eliascoelho911.youplay.common

import com.github.eliascoelho911.youplay.BaseTest
import org.junit.Assert.assertEquals
import org.junit.Test

class SerializeDataClassKtTest: BaseTest() {
    @Test
    fun testSerializeToMap() {
        val model = Model(arg1 = "arg1", arg2 = 2)
        val expected = mapOf("arg1" to "arg1", "arg2" to 2.0)

        val result = model.serializeToMap()

        assertEquals(expected, result)
    }

    private data class Model(val arg1: String, val arg2: Int)
}
package com.syanko.simonis

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Example local unit test, which will signIn on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)

        val test1 = Testt(id = "id", address = "address")
        val test2 = Testt(id = "id", address = "address")

        val isSame = test1 == test2
        assertTrue(isSame)
        var seal: Seal? = null
    }
}

class Testt(
    val id: String,
    val name: String = "oke",
    val address: String
) {
    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return this.id == (other as Testt).id
    }
}

data class Oke(
    val id: String,
    val name: String = "oke",
    val address: String
)

sealed class Seal {
    class Seal1 : Seal()
    class Seal2 : Seal()
    class Seal3 : Seal()
    class Seal4 : Seal()
    class Seal5 : Seal()
}

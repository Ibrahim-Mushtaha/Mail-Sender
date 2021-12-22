package com.ix.ibrahim7.mailsender

import androidx.test.filters.SmallTest
import com.ix.ibrahim7.mailsender.ds.LinkedQueue
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class ParameterizedTest(
    var d: Int,
    var s: Int
) {
    @SmallTest
    @Test
    fun enqueue() {
        Assert.assertTrue(q1.enqueue(d))
        Assert.assertTrue(q1.enqueue(s))
    }

    companion object {
        var q1: LinkedQueue<Int> = LinkedQueue()

        @BeforeClass
        @JvmStatic
        fun beforeClass() {
            println("beforeClass")
        }

        @JvmStatic
        @Parameterized.Parameters
        fun data() = listOf(
            arrayOf(7, 8),
            arrayOf(2, 8),
            arrayOf(3, 8),
            arrayOf(4, 8),
            arrayOf(5, 8),
            arrayOf(6, 8),
            arrayOf(8, 8),
            arrayOf(9, 8),
            arrayOf(0, 8)
        )
    }
}

@RunWith(Parameterized::class)
class Parameterized2(
    var input: String,
    var input2: Boolean

) {

    @Test
    fun isValid() {
        Assert.assertEquals(q1.isValid(input), input2)
    }
    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data() = listOf(
            arrayOf("[{()}]", true),
            arrayOf("[{({{(())}})}]", true),
            arrayOf("[{{()}]", false),
            arrayOf("{[[{()}]]}", true),
            arrayOf("[{()}[[]]]]]]", false),
            arrayOf("[{()}]", true),
            arrayOf("[{({{(())}})}]", true),
            arrayOf("[{{()}]", false),
            arrayOf("{[[{()}]]}", true),
            arrayOf("[{()}[[]]]]]]", false)
        )

        var q1: LinkedQueue<Int> = LinkedQueue()

        @BeforeClass
        @JvmStatic
        fun beforeClass() {
            println("beforeClass")
        }

    }
}

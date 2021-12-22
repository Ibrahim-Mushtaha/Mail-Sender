package com.ix.ibrahim7.mailsender

import com.ix.ibrahim7.mailsender.ds.LinkedQueue

import org.junit.*


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class LinkedQueueTest {

    companion object {

        var q1: LinkedQueue<Int>? = null

        @BeforeClass
        @JvmStatic
        fun beforeClass() {
            println("beforeClass")
            q1 = LinkedQueue()
        }


        @AfterClass
        @JvmStatic
        fun afterClass() {
            println("afterClass")
            q1 = null
        }
    }


    @Before
    fun beforeTest() {
        println("beforeTest")
    }


    @After
    fun afterTest() {
        println("afterTest")
    }

    @Test
    fun addEnqueue() {
        for (i in 1..20) {
            Assert.assertTrue(q1!!.enqueue(i))
        }
        Assert.assertEquals(1, q1!!.front())
    }

    @Test
    fun dequeue() {
        if (!q1!!.isEmpty)
            Assert.assertTrue(q1!!.dequeue())
        else
            Assert.assertFalse(q1!!.dequeue())
    }

    @Test
    fun clearQueue() {
        addEnqueue()
        Assert.assertNotNull(q1?.front())

    }

    @Test
    fun isValid() {
        Assert.assertTrue(q1?.isValid("[{()}]")!!)
    }


}
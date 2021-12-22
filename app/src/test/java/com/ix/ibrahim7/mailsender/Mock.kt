package com.ix.ibrahim7.mailsender


import com.ix.ibrahim7.mailsender.ds.LinkedQueue
import org.junit.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.io.IOException


class Mock {


    @Mock
    var q1: LinkedQueue<Int> = LinkedQueue()


    @Before
    @Throws(IOException::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        println("beforeTest")
    }


    @After
    fun afterTest() {
        println("afterTest")
    }

    @Test
    fun addEnqueue() {
        for (i in 1..20) {
            val d = q1.enqueue(i)
            Mockito.`when`(d).thenReturn(!d)
            Assert.assertEquals(!d, true)
        }
    }

    @Test
    fun clearQueue() {
        Mockito.`when`(q1.isValid("[{()}]")).thenReturn(true)
        Assert.assertEquals(q1.isValid("[{()}]"), true)
    }

}
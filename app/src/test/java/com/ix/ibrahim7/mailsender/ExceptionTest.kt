package com.ix.ibrahim7.mailsender

import com.ix.ibrahim7.mailsender.ds.LinkedQueue
import org.junit.Assert

import org.junit.Test
import java.lang.IndexOutOfBoundsException
import java.lang.NullPointerException


class ExceptionTest {


    var q1: LinkedQueue<Int>? = null
    var q2: LinkedQueue<Int> = LinkedQueue()


    @Test
    fun isValid() {
        try {
            q1!!.isValid("[{()}]")
            Assert.fail("Null Pinter Exception")
        } catch (e: NullPointerException) {
        }
    }
    @Test
    fun isValidIsNull() {
        try {
            q2.isValidIsNull("[{()}]")
            Assert.fail("Index Out Of Bounds Exception")
        } catch (e: IndexOutOfBoundsException) {
        }
    }


}
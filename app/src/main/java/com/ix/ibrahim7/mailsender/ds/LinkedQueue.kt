package com.ix.ibrahim7.mailsender.ds

import android.icu.lang.UCharacter.GraphemeClusterBreak
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import java.util.Stack





class Node<T>(var item: T) {
    var next: Node<T>? = null
}

class LinkedQueue<T> {
    private val next: Node<T>? = null
    private var frontPtr: Node<T>? = null
    private var rearPtr: Node<T>? = null
    var size = 0
        private set
    val isEmpty: Boolean
        get() = size == 0

    fun front(): T {
        return frontPtr!!.item
    }

    fun rear(): T {
        return rearPtr!!.item
    }

    fun enqueue(item: T): Boolean {
        val node = Node(item)
        if (size == 0) {
            rearPtr = node
            frontPtr = rearPtr
        }
        rearPtr!!.next = node
        rearPtr = node
        size++

        return true
    }

    fun dequeue(): Boolean {
        if (isEmpty) {
            return false
        }
        frontPtr = frontPtr!!.next
        if (frontPtr == null) {
            rearPtr = null
        }
        size--

        return true
    }

    fun clearQueue() {
        frontPtr = null
    }

    fun display() {
        var cur = frontPtr
        print("[ ")
        while (cur != null) {
            print(cur.item.toString() + " ")
            cur = cur.next
        }
        println("]")
    }

    fun isValid(s: String): Boolean {
        val chars = s.toCharArray()
        val stack = Stack<Char>()
        for (i in chars.indices) {
            val type = chars[i]
            if (type == '[' || type == '{' || type == '(') stack.push(type)
            if (type == ']' || type == '}' || type == ')') {
                if (stack.isEmpty()) return false
                val typePop = stack.pop()
                if (typePop == '(' && type != ')' || typePop == '{' && type != '}' || typePop == '[' && type != ']') {
                    return false
                }
            }
        }
        return stack.isEmpty()
    }

    fun isValidIsNull(s: String): Boolean {
        val chars = s.toCharArray()
        val stack = Stack<Char>()
        for (i in 0..chars.size) {
            val type = chars[i+8]
            if (type == '[' || type == '{' || type == '(') stack.push(type)
            if (type == ']' || type == '}' || type == ')') {
                if (stack.isEmpty()) return false
                val typePop = stack.pop()
                if (typePop == '(' && type != ')' || typePop == '{' && type != '}' || typePop == '[' && type != ']') {
                    return false
                }
            }
        }
        return stack.isEmpty()
    }
}

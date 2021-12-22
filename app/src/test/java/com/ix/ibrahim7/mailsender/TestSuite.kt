package com.ix.ibrahim7.mailsender

import androidx.test.filters.LargeTest
import org.junit.runner.RunWith
import org.junit.runners.Suite


@RunWith(Suite::class)
@LargeTest
@Suite.SuiteClasses(
    LinkedQueueTest::class,
    ParameterizedTest::class,
    Parameterized2::class,
    Mock::class,
    ExceptionTest::class
)
class TestSuite
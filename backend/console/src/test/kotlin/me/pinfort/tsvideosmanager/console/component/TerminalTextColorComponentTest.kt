package me.pinfort.tsvideosmanager.console.component

import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class TerminalTextColorComponentTest {
    @InjectMockKs
    private lateinit var terminalTextColorComponent: TerminalTextColorComponent

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Nested
    inner class Warn {
        @Test
        fun success() {
            println(terminalTextColorComponent.warn("test"))
        }
    }
}

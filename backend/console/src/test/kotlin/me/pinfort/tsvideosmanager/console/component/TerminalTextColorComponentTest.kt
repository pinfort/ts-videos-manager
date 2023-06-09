package me.pinfort.tsvideosmanager.console.component

import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import org.assertj.core.api.Assertions
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
            val actual = terminalTextColorComponent.warn("test")

            val expected = "\u001B[${TerminalTextColorComponent.COLOR.YELLOW.code + 10}mtest\u001B[0m"
            Assertions.assertThat(actual).isEqualTo(expected)
        }
    }

    @Nested
    inner class Debug {
        @Test
        fun success() {
            val actual = terminalTextColorComponent.debug("test")

            val expected = "\u001B[${TerminalTextColorComponent.COLOR.WHITE.code + 10}mtest\u001B[0m"
            Assertions.assertThat(actual).isEqualTo(expected)
        }
    }

    @Nested
    inner class Info {
        @Test
        fun success() {
            val actual = terminalTextColorComponent.info("test")

            val expected = "\u001B[${TerminalTextColorComponent.COLOR.GREEN.code + 10}mtest\u001B[0m"
            Assertions.assertThat(actual).isEqualTo(expected)
        }
    }

    @Nested
    inner class Error {
        @Test
        fun success() {
            val actual = terminalTextColorComponent.error("test")

            val expected = "\u001B[${TerminalTextColorComponent.COLOR.RED.code + 10}mtest\u001B[0m"
            Assertions.assertThat(actual).isEqualTo(expected)
        }
    }
}

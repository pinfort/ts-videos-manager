package me.pinfort.tsvideosmanager.console.component

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.mockkStatic
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class UserQuestionComponentTest {
    @InjectMockKs
    private lateinit var userQuestionComponent: UserQuestionComponent

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Nested
    inner class AskDefaultFalseTest {
        @ParameterizedTest
        @CsvSource(
            "y, true",
            "Y, true",
            "n, false",
            "N, false",
            "null, false",
            "foo, false",
            nullValues = ["null"]
        )
        fun success(input: String?, expected: Boolean) {
            mockkStatic(::readlnOrNull)
            every { readlnOrNull() } returns input
            // when
            val actual = userQuestionComponent.askDefaultFalse("question")

            // then
            Assertions.assertThat(actual).isEqualTo(expected)
        }
    }

    @Nested
    inner class AskDefaultTrueTest {
        @ParameterizedTest
        @CsvSource(
            "y, true",
            "Y, true",
            "n, false",
            "N, false",
            "null, true",
            "foo, true",
            nullValues = ["null"]
        )
        fun success(input: String?, expected: Boolean) {
            mockkStatic(::readlnOrNull)

            every { readlnOrNull() } returns input
            // when
            val actual = userQuestionComponent.askDefaultTrue("question")

            // then
            Assertions.assertThat(actual).isEqualTo(expected)
        }
    }
}

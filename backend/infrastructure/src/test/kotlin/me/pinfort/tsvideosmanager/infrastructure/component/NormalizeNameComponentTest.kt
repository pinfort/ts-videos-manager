package me.pinfort.tsvideosmanager.infrastructure.component

import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class NormalizeNameComponentTest {
    @InjectMockKs
    private lateinit var normalizeNameComponent: NormalizeNameComponent

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Nested
    inner class NormalizeTest {
        @Test
        fun success() {
            val actual = normalizeNameComponent.normalize("\\/:*?\"<>|~‼０１２３４５６７８９ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ")
            Assertions.assertThat(actual).isEqualTo("￥／：＊？”＜＞｜～!!0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz")
        }
    }

    @Nested
    inner class NormalizeSpecialCharactersTest {
        @Test
        fun success() {
            val actual = normalizeNameComponent.normalizeSpecialCharacters("\\/:*?\"<>|~‼")
            Assertions.assertThat(actual).isEqualTo("￥／：＊？”＜＞｜～!!")
        }
    }

    @Nested
    inner class NormalizeAlphabetAndNumericTest {
        @Test
        fun success() {
            val actual = normalizeNameComponent.normalizeAlphabetAndNumeric("０１２３４５６７８９ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ")
            Assertions.assertThat(actual).isEqualTo("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz")
        }
    }
}

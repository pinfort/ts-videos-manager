package me.pinfort.tsvideosmanager.infrastructure.component

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.nio.file.Path

class DirectoryNameComponentTest {
    @MockK
    private lateinit var normalizeNameComponent: NormalizeNameComponent

    @InjectMockKs
    private lateinit var directoryNameComponent: DirectoryNameComponent

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Nested
    inner class IndexDirectoryName {
        @Test
        fun success() {
            every { normalizeNameComponent.normalize("かきくけこ") } returns "さしすせそ"

            val result = directoryNameComponent.indexDirectoryName(Path.of("あいうえお/かきくけこ/てすとvideofile.m2ts"))

            Assertions.assertThat(result).isEqualTo("さ")
        }
    }

    @Nested
    inner class ProgramDirectoryName {
        @Test
        fun success() {
            every { normalizeNameComponent.normalize("かきくけこ") } returns "さしすせそ"

            val result = directoryNameComponent.programDirectoryName(Path.of("あいうえお/かきくけこ/てすとvideofile.m2ts"))

            Assertions.assertThat(result).isEqualTo("さしすせそ")
        }
    }
}

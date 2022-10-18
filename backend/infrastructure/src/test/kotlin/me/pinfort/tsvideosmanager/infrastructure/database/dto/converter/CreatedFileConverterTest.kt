package me.pinfort.tsvideosmanager.infrastructure.database.dto.converter

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import me.pinfort.tsvideosmanager.infrastructure.database.dto.CreatedFileDto
import me.pinfort.tsvideosmanager.infrastructure.structs.CreatedFile
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CreatedFileConverterTest {
    @MockK
    private lateinit var createdFileStatusConverter: CreatedFileStatusConverter

    @InjectMockKs
    private lateinit var createdFileConverter: CreatedFileConverter

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun success() {
        every { createdFileStatusConverter.convert(any()) } returns CreatedFile.Status.REGISTERED

        val actual = createdFileConverter.convert(
            CreatedFileDto(
                id = 1,
                splittedFileId = 2,
                file = "file",
                size = 3,
                mime = "mime",
                encoding = "encoding",
                status = CreatedFileDto.Status.REGISTERED
            )
        )

        Assertions.assertThat(actual).isEqualTo(
            CreatedFile(
                id = 1,
                splittedFileId = 2,
                file = "file",
                size = 3,
                mime = "mime",
                encoding = "encoding",
                status = CreatedFile.Status.REGISTERED
            )
        )
    }
}

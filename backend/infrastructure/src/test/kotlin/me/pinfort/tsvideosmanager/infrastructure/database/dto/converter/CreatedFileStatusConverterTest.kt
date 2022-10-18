package me.pinfort.tsvideosmanager.infrastructure.database.dto.converter

import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import me.pinfort.tsvideosmanager.infrastructure.database.dto.CreatedFileDto
import me.pinfort.tsvideosmanager.infrastructure.structs.CreatedFile
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class CreatedFileStatusConverterTest {
    @InjectMockKs
    private lateinit var createdFileStatusConverter: CreatedFileStatusConverter

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @CsvSource(
        "REGISTERED,REGISTERED",
        "ENCODE_SUCCESS,ENCODE_SUCCESS",
        "FILE_MOVED,FILE_MOVED",
    )
    @ParameterizedTest
    fun success(originalStatus: CreatedFileDto.Status, status: CreatedFile.Status) {
        val actual = createdFileStatusConverter.convert(originalStatus)

        Assertions.assertThat(actual).isEqualTo(status)
    }
}

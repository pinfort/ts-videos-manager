package me.pinfort.tsvideosmanager.infrastructure.command

import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.verifySequence
import me.pinfort.tsvideosmanager.infrastructure.database.mapper.SplittedFileMapper
import me.pinfort.tsvideosmanager.infrastructure.structs.SplittedFile
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.slf4j.Logger

class SplittedFileCommandTest {
    @MockK
    private lateinit var splittedFileMapper: SplittedFileMapper

    @MockK
    private lateinit var logger: Logger

    @InjectMockKs
    private lateinit var splittedFileCommand: SplittedFileCommand

    private val splittedFile = SplittedFile(
        id = 1,
        executedFileId = 1,
        file = "test.ts",
        size = 1,
        duration = 1.0,
        status = SplittedFile.Status.REGISTERED
    )

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Nested
    inner class DeleteTest {
        @Test
        fun success() {
            every { splittedFileMapper.delete(any()) } just Runs
            every { logger.info(any()) } just Runs

            splittedFileCommand.delete(splittedFile)

            verifySequence {
                splittedFileMapper.delete(splittedFile.id.toLong())
                logger.info("Delete splitted file, id=${splittedFile.id}")
            }
        }
    }
}

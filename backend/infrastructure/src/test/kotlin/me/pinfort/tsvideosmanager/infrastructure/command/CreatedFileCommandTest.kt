package me.pinfort.tsvideosmanager.infrastructure.command

import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.verify
import io.mockk.verifySequence
import jcifs.smb.SmbException
import me.pinfort.tsvideosmanager.infrastructure.database.dto.CreatedFileDto
import me.pinfort.tsvideosmanager.infrastructure.database.dto.converter.CreatedFileConverter
import me.pinfort.tsvideosmanager.infrastructure.database.mapper.CreatedFileMapper
import me.pinfort.tsvideosmanager.infrastructure.samba.client.SambaClient
import me.pinfort.tsvideosmanager.infrastructure.samba.component.NasComponent
import me.pinfort.tsvideosmanager.infrastructure.structs.CreatedFile
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import java.io.InputStream

class CreatedFileCommandTest {
    @MockK
    private lateinit var createdFileMapper: CreatedFileMapper

    @MockK
    private lateinit var createdFileConverter: CreatedFileConverter

    @MockK
    private lateinit var sambaClient: SambaClient

    @MockK
    private lateinit var nasComponent: NasComponent

    @MockK
    private lateinit var logger: Logger

    @InjectMockKs
    private lateinit var createdFileCommand: CreatedFileCommand

    private val createdFileDto = CreatedFileDto(
        id = 1,
        splittedFileId = 2,
        file = "file",
        size = 3,
        mime = "mime",
        encoding = "encoding",
        status = CreatedFileDto.Status.ENCODE_SUCCESS
    )
    private val createdFile = CreatedFile(
        id = 1,
        splittedFileId = 2,
        file = "file",
        size = 3,
        mime = "mime",
        encoding = "encoding",
        status = CreatedFile.Status.ENCODE_SUCCESS
    )

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Nested
    inner class FindMp4File {
        @Test
        fun success() {
            val testCreatedFile = createdFile.copy(
                mime = "video/mp4"
            )
            every { createdFileMapper.find(any()) } returns createdFileDto
            every { createdFileConverter.convert(any()) } returns testCreatedFile

            val actual = createdFileCommand.findMp4File(1)

            Assertions.assertThat(actual).isEqualTo(testCreatedFile)

            verifySequence {
                createdFileMapper.find(1)
                createdFileConverter.convert(createdFileDto)
                testCreatedFile.isMp4
            }
        }

        @Test
        fun notVideo() {
            every { createdFileMapper.find(any()) } returns createdFileDto
            every { createdFileConverter.convert(any()) } returns createdFile

            val actual = createdFileCommand.findMp4File(1)

            Assertions.assertThat(actual).isNull()

            verifySequence {
                createdFileMapper.find(1)
                createdFileConverter.convert(createdFileDto)
                createdFile.isMp4
            }
        }

        @Test
        fun noHit() {
            every { createdFileMapper.find(any()) } returns null

            val actual = createdFileCommand.findMp4File(1)

            Assertions.assertThat(actual).isNull()

            verifySequence {
                createdFileMapper.find(1)
            }
        }
    }

    @Nested
    inner class StreamCreatedFile {
        @Test
        fun success() {
            val testCreatedFile = createdFile.copy(
                mime = "video/mp4"
            )
            val testStream = InputStream.nullInputStream()
            every { createdFileMapper.find(any()) } returns createdFileDto
            every { createdFileConverter.convert(any()) } returns testCreatedFile

            every { sambaClient.videoStoreNas().resolve(any()).openInputStream() } returns testStream

            val actual = createdFileCommand.streamCreatedFile(1)

            Assertions.assertThat(actual).isEqualTo(testStream)

            verifySequence {
                createdFileMapper.find(1)
                createdFileConverter.convert(createdFileDto)
                sambaClient.videoStoreNas().resolve("file")
            }
        }

        @Test
        fun successBackSlash() {
            val testCreatedFile = createdFile.copy(
                mime = "video/mp4",
                file = "test\\"
            )
            val testStream = InputStream.nullInputStream()
            every { createdFileMapper.find(any()) } returns createdFileDto
            every { createdFileConverter.convert(any()) } returns testCreatedFile

            every { sambaClient.videoStoreNas().resolve(any()).openInputStream() } returns testStream

            val actual = createdFileCommand.streamCreatedFile(1)

            Assertions.assertThat(actual).isEqualTo(testStream)

            verifySequence {
                createdFileMapper.find(1)
                createdFileConverter.convert(createdFileDto)
                sambaClient.videoStoreNas().resolve("test/")
            }
        }

        @Test
        fun noFile() {
            val testCreatedFile = createdFile.copy(
                mime = "video/mp4",
                file = "test\\"
            )
            every { createdFileMapper.find(any()) } returns createdFileDto
            every { createdFileConverter.convert(any()) } returns testCreatedFile

            every { sambaClient.videoStoreNas().resolve(any()).openInputStream() } throws SmbException("err")

            val actual = createdFileCommand.streamCreatedFile(1)

            Assertions.assertThat(actual).isNull()

            verifySequence {
                createdFileMapper.find(1)
                createdFileConverter.convert(createdFileDto)
                sambaClient.videoStoreNas().resolve("test/")
            }
        }

        @Test
        fun noHit() {
            every { createdFileMapper.find(any()) } returns null

            val actual = createdFileCommand.streamCreatedFile(1)

            Assertions.assertThat(actual).isNull()

            verifySequence {
                createdFileMapper.find(1)
            }
        }
    }

    @Nested
    inner class Delete {
        @Test
        fun success() {
            val createdFile = CreatedFile(
                id = 1,
                splittedFileId = 2,
                file = "file",
                size = 3,
                mime = "mime",
                encoding = "encoding",
                status = CreatedFile.Status.ENCODE_SUCCESS
            )
            every { createdFileMapper.delete(any()) } just Runs
            every { nasComponent.deleteResource(createdFile.file) } returns SambaClient.NasType.ORIGINAL_STORE_NAS
            every { logger.info(any()) } just Runs

            val actual = createdFileCommand.delete(createdFile)

            Assertions.assertThat(actual).isEqualTo(SambaClient.NasType.ORIGINAL_STORE_NAS)

            verifySequence {
                createdFileMapper.delete(1)
                nasComponent.deleteResource(createdFile.file)
                logger.info(any())
            }
        }

        @Test
        fun noHit() {
            val createdFile = CreatedFile(
                id = 1,
                splittedFileId = 2,
                file = "file",
                size = 3,
                mime = "mime",
                encoding = "encoding",
                status = CreatedFile.Status.ENCODE_SUCCESS
            )
            every { createdFileMapper.delete(any()) } just Runs
            every { nasComponent.deleteResource(any()) } throws Exception("err")
            every { logger.error(any(), any<Exception>()) } just Runs

            Assertions.assertThatThrownBy {
                createdFileCommand.delete(createdFile)
            }
                .hasMessage("java.lang.Exception: err")
                .isInstanceOf(Exception::class.java)

            verifySequence {
                createdFileMapper.delete(1)
                nasComponent.deleteResource(createdFile.file)
                logger.error("Failed to delete file. id=1, file=${createdFile.file}", any<Exception>())
            }
        }

        @Test
        fun dryRun() {
            val createdFile = CreatedFile(
                id = 1,
                splittedFileId = 2,
                file = "file",
                size = 3,
                mime = "mime",
                encoding = "encoding",
                status = CreatedFile.Status.ENCODE_SUCCESS
            )
            every { logger.info(any()) } just Runs

            val actual = createdFileCommand.delete(createdFile, true)

            Assertions.assertThat(actual).isEqualTo(SambaClient.NasType.VIDEO_STORE_NAS)

            verifySequence {
                logger.info(any())
            }
            verify(exactly = 0) {
                createdFileMapper.delete(any())
                nasComponent.deleteResource(any())
            }
        }
    }
}

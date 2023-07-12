package me.pinfort.tsvideosmanager.api.controller

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import me.pinfort.tsvideosmanager.infrastructure.command.ProgramCommand
import me.pinfort.tsvideosmanager.infrastructure.structs.Program
import me.pinfort.tsvideosmanager.infrastructure.structs.ProgramDetail
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime

@SpringBootTest
@AutoConfigureMockMvc
class ProgramControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var programCommand: ProgramCommand

    private val program = Program(
        id = 1,
        name = "test",
        executedFileId = 2,
        status = Program.Status.COMPLETED,
        drops = 3,
        size = 4,
        duration = 5.0,
        recordedAt = LocalDateTime.MIN,
        channel = "",
        title = "",
        channelName = ""
    )

    private val programDetail = ProgramDetail(
        id = 1,
        name = "test",
        executedFileId = 2,
        status = Program.Status.COMPLETED,
        drops = 3,
        size = 4,
        duration = 5.0,
        recordedAt = LocalDateTime.MIN,
        channel = "",
        title = "",
        channelName = "",
        createdFiles = listOf()
    )

    @Nested
    inner class IndexTest {
        @Test
        fun success() {
            every { programCommand.selectByName(any()) } returns listOf()
            // andDo(print())でリクエスト・レスポンスを表示
            mockMvc.perform(get("/api/v1/programs"))
                .andDo(print())
                .andExpect(status().isOk)
        }
    }

    @Nested
    inner class DetailTest {
        @Test
        fun success() {
            every { programCommand.find(any()) } returns program
            every { programCommand.findDetail(any()) } returns programDetail
            every { programCommand.videoFiles(any()) } returns listOf()

            mockMvc.perform(get("/api/v1/programs/1"))
                .andDo(print())
                .andExpect(status().isOk)
        }
    }
}

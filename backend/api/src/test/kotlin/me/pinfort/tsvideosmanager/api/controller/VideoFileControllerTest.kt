package me.pinfort.tsvideosmanager.api.controller

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import me.pinfort.tsvideosmanager.infrastructure.command.CreatedFileCommand
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.io.InputStream

@SpringBootTest
@AutoConfigureMockMvc
class VideoFileControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var createdFileCommand: CreatedFileCommand

    @Nested
    inner class Stream {
        @Test
        fun success() {
            every { createdFileCommand.streamCreatedFile(any()) } returns InputStream.nullInputStream()

            mockMvc.perform(get("/api/v1/video/1/stream"))
                .andDo(print())
                .andExpect(status().isOk)
        }
    }
}

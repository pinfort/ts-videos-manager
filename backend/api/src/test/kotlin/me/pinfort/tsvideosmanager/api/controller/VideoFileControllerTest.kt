package me.pinfort.tsvideosmanager.api.controller

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.testcontainers.containers.DockerComposeContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.io.File

@SpringBootTest(
    properties = [
        "samba.video-store-nas.url=smb://samba:139/alice",
        "samba.video-store-nas.username=alice",
        "samba.video-store-nas.password=alipass",
        "samba.original-store-nas.url=smb://samba:139/bob",
        "samba.original-store-nas.username=bob",
        "samba.original-store-nas.password=bobpass",
        "spring.datasource.url=jdbc:tc:mariadb:10.7:///?TC_INITSCRIPT=ddl/01_create_database.sql"
    ]
)
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
class VideoFileControllerTest {
    @Container
    var dockerComposeContainer: DockerComposeContainer<*> =
        DockerComposeContainer(listOf(File("src/test/resources/docker-compose.yml")))

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Nested
    inner class Stream {
        @Test
        fun success() {
            mockMvc.perform(get("/api/v1/video/1/stream"))
                .andDo(print())
                .andExpect(status().isNotFound)
        }
    }
}

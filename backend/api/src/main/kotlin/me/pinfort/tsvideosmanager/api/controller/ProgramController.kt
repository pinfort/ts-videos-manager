package me.pinfort.tsvideosmanager.api.controller

import me.pinfort.tsvideosmanager.api.exception.ProgramNotFoundException
import me.pinfort.tsvideosmanager.api.response.ProgramDetailResponse
import me.pinfort.tsvideosmanager.api.response.SearchResponse
import me.pinfort.tsvideosmanager.infrastructure.command.ProgramCommand
import me.pinfort.tsvideosmanager.infrastructure.structs.Program
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.validation.constraints.Max
import javax.validation.constraints.Positive
import javax.validation.constraints.PositiveOrZero

@RestController
class ProgramController(
    private val programCommand: ProgramCommand,
) {
    @GetMapping("/api/v1/programs")
    fun index(
        @RequestParam(name = "name", required = false, defaultValue = "") name: String,
        @RequestParam(name = "limit", required = false, defaultValue = "100") @Max(100) @Positive limit: Int,
        @RequestParam(name = "offset", required = false, defaultValue = "0") @PositiveOrZero offset: Int,
    ): SearchResponse {
        return SearchResponse(
            programCommand.selectByName(name, limit, offset)
        )
    }

    @GetMapping("/api/v1/programs/{id}")
    fun get(@PathVariable(name = "id") id: Int): ProgramDetailResponse {
        val program = programCommand.find(id) ?: throw ProgramNotFoundException("program not found. id=$id")
        val videoFiles = programCommand.videoFiles(program)
        return ProgramDetailResponse(
            program = program,
            videoFiles = videoFiles,
        )
    }

    @DeleteMapping("/api/v1/programs/{id}")
    fun delete(@PathVariable(name = "id") id: Int) {}
}

package me.pinfort.tsvideosmanager.api.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ProgramController {
    @GetMapping("/")
    fun index(): String {
        return "index"
    }

    @GetMapping("/v1/programs/{id}")
    fun get(@RequestParam(name = "id") id: Int) {}

    @DeleteMapping("/v1/programs/{id}")
    fun delete(@RequestParam(name = "id") id: Int) {}
}

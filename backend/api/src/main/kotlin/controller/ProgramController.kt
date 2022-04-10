package controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class ProgramController {
    @GetMapping("/v1/programs/search")
    fun search() {}

    @GetMapping("/v1/programs/{id}")
    fun get(@RequestParam(name = "id") id: Int) {}

    @DeleteMapping("/v1/programs/{id}")
    fun delete(@RequestParam(name = "id") id: Int) {}
}

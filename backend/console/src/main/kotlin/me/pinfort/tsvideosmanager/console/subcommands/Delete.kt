package me.pinfort.tsvideosmanager.console.subcommands

import kotlinx.cli.ArgType
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand
import me.pinfort.tsvideosmanager.console.component.UserQuestionComponent
import me.pinfort.tsvideosmanager.infrastructure.command.ProgramCommand
import org.springframework.stereotype.Component

@OptIn(ExperimentalCli::class)
@Component
class Delete(
    private val programCommand: ProgramCommand,
    private val userQuestionComponent: UserQuestionComponent
) : Subcommand("delete", "delete resources") {
    private val id by argument(ArgType.Int, "id", "id of resource")

    override fun execute() {
        val targetProgram = programCommand.find(id.toLong()) ?: return println("program not found")

        println("program ready to delete:$targetProgram")
        val response = userQuestionComponent.askDefaultFalse("delete?")
        if (!response) {
            println("canceled")
            return
        }
        programCommand.delete(targetProgram)
        println("program deleted")
    }
}

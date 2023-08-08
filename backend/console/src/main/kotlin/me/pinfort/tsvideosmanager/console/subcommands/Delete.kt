package me.pinfort.tsvideosmanager.console.subcommands

import kotlinx.cli.ArgType
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand
import kotlinx.cli.default
import me.pinfort.tsvideosmanager.console.component.UserQuestionComponent
import me.pinfort.tsvideosmanager.infrastructure.command.ProgramCommand
import org.springframework.stereotype.Component

@OptIn(ExperimentalCli::class)
@Component
class Delete(
    private val programCommand: ProgramCommand,
    private val userQuestionComponent: UserQuestionComponent
) : Subcommand("delete", "delete program") {
    private val programId by argument(ArgType.Int, "programId", "id of program to delete")
    private val dryRun by option(ArgType.Boolean, "dryRun", "d").default(false)

    override fun execute() {
        if (dryRun) {
            println("in dry run mode.")
        }
        val targetProgram = programCommand.find(programId.toLong()) ?: return println("program not found")

        println("program ready to delete:$targetProgram")
        val response = userQuestionComponent.askDefaultFalse("delete?")
        if (!response) {
            println("canceled")
            return
        }
        programCommand.delete(targetProgram, dryRun)
        println("program deleted")
    }
}

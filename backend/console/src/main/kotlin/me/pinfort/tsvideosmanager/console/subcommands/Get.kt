package me.pinfort.tsvideosmanager.console.subcommands

import kotlinx.cli.ArgType
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand
import me.pinfort.tsvideosmanager.console.component.ProgramDetailToTextComponent
import me.pinfort.tsvideosmanager.infrastructure.command.ExecutedFileCommand
import me.pinfort.tsvideosmanager.infrastructure.command.ProgramCommand
import org.springframework.stereotype.Component

@OptIn(ExperimentalCli::class)
@Component
class Get(
    private val programCommand: ProgramCommand,
    private val executedFileCommand: ExecutedFileCommand,
    private val programDetailToTextComponent: ProgramDetailToTextComponent
) : Subcommand("get", "get resources") {
    private val resourceType by argument(ArgType.Choice<ResourceType>(), "resourceType", "type of target resource")
    private val id by argument(ArgType.Int, "id", "id of resource")

    enum class ResourceType {
        PROGRAM,
        EXECUTED_FILE
    }

    override fun execute() {
        when (resourceType) {
            ResourceType.PROGRAM -> {
                val program = programCommand.findDetail(id) ?: return println("program not found")
                println(programDetailToTextComponent.convertConsole(program))
            }
            ResourceType.EXECUTED_FILE -> {
                val executedFile = executedFileCommand.find(id)
                println(executedFile)
            }
        }
    }
}

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

    enum class ResourceType {
        PROGRAM,
        EXECUTED_FILE
    }

    override fun execute() {
        when (resourceType) {
            ResourceType.PROGRAM -> {
                val programId by argument(ArgType.Int, "programId", "id of program")
                val program = programCommand.findDetail(programId) ?: return println("program not found")
                println(programDetailToTextComponent.convertConsole(program))
            }
            ResourceType.EXECUTED_FILE -> {
                val executedFileId by argument(ArgType.Int, "executedFileId", "id of executedFile")
                val executedFile = executedFileCommand.find(executedFileId)
                println(executedFile)
            }
        }
    }
}

package me.pinfort.tsvideosmanager.console.subcommands

import kotlinx.cli.ArgType
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand
import kotlinx.cli.default
import me.pinfort.tsvideosmanager.console.component.UserQuestionComponent
import me.pinfort.tsvideosmanager.infrastructure.command.ProgramCommand
import me.pinfort.tsvideosmanager.infrastructure.component.DirectoryNameComponent
import org.springframework.stereotype.Component
import java.nio.file.Path

@OptIn(ExperimentalCli::class)
@Component
class Modify(
    private val programCommand: ProgramCommand,
    private val directoryNameComponent: DirectoryNameComponent,
    private val userQuestionComponent: UserQuestionComponent
) : Subcommand("modify", "modify programs") {
    private val id by argument(ArgType.Int, "id", "id of resource")
    private val targetType by argument(ArgType.Choice<TargetType>(), "targetType", "type of target resource")
    private val newValue by argument(ArgType.String, "newValue", "new value of target resource")
    private val dryRun by option(ArgType.Boolean, "dryRun", "d").default(false)

    enum class TargetType {
        DIRECTORY_NAME
    }

    override fun execute() {
        when (targetType) {
            TargetType.DIRECTORY_NAME -> {
                val program = programCommand.find(id.toLong()) ?: return println("program not found")
                println("You are moving files in program id: ${program.id}, name:${program.name}")
                val createdFiles = programCommand.videoFiles(program)
                createdFiles.forEach {
                    val oldPath = Path.of(it.file)
                    val newPath = directoryNameComponent.replaceWithGivenDirectoryName(oldPath, newValue)
                    println("mv $oldPath to $newPath")
                }
                val answer = userQuestionComponent.askDefaultFalse("Are you sure to move files?")
                if (!answer) {
                    println("canceled")
                    return
                }
                programCommand.moveCreatedFiles(program, newValue, dryRun)
                println("moved")
            }
        }
    }
}

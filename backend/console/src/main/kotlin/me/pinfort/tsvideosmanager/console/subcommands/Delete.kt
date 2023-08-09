package me.pinfort.tsvideosmanager.console.subcommands

import kotlinx.cli.ArgType
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand
import kotlinx.cli.default
import me.pinfort.tsvideosmanager.console.component.UserQuestionComponent
import me.pinfort.tsvideosmanager.infrastructure.command.CreatedFileCommand
import me.pinfort.tsvideosmanager.infrastructure.command.ProgramCommand
import org.springframework.stereotype.Component

@OptIn(ExperimentalCli::class)
@Component
class Delete(
    private val programCommand: ProgramCommand,
    private val userQuestionComponent: UserQuestionComponent,
    private val createdFileCommand: CreatedFileCommand
) : Subcommand("delete", "delete resources") {
    private val targetType by argument(ArgType.Choice<TargetType>(), "targetType", "type of target resource")
    private val id by argument(ArgType.Int, "id", "id of resource to delete. if ts_files, program id.")
    private val dryRun by option(ArgType.Boolean, "dryRun", "d").default(false)

    enum class TargetType {
        PROGRAM,
        TS_FILES
    }

    override fun execute() {
        if (dryRun) {
            println("in dry run mode.")
        }

        when (targetType) {
            TargetType.PROGRAM -> deleteProgram()
            TargetType.TS_FILES -> deleteTsFiles()
        }
    }

    private fun deleteProgram() {
        val targetProgram = programCommand.find(id.toLong()) ?: return println("program not found")

        println("program ready to delete:$targetProgram")
        val response = userQuestionComponent.askDefaultFalse("delete?")
        if (!response) {
            println("canceled")
            return
        }
        programCommand.delete(targetProgram, dryRun)
        println("program deleted")
    }

    private fun deleteTsFiles() {
        val targetProgram = programCommand.findDetail(id.toLong()) ?: return println("program not found")

        val targetFiles = targetProgram.createdFiles.filter { it.isTs }

        println("created file ready to delete")
        targetFiles.forEach { println(it.file) }
        val response = userQuestionComponent.askDefaultFalse("delete?")
        if (!response) {
            println("canceled")
            return
        }
        targetFiles.forEach { createdFileCommand.delete(it, dryRun) }
        println("ts files deleted")
    }
}

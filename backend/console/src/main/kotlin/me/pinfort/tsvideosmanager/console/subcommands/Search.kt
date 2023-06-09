package me.pinfort.tsvideosmanager.console.subcommands

import kotlinx.cli.ArgType
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand
import kotlinx.cli.required
import me.pinfort.tsvideosmanager.console.component.TerminalTextColorComponent
import me.pinfort.tsvideosmanager.infrastructure.command.ExecutedFileCommand
import me.pinfort.tsvideosmanager.infrastructure.command.ProgramCommand
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalCli::class)
class Search(
    private val programCommand: ProgramCommand,
    private val executedFileCommand: ExecutedFileCommand,
    private val terminalTextColorComponent: TerminalTextColorComponent
) : Subcommand("search", "search programs") {
    val datetimeFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")

    val programSearchName by option(ArgType.String, "name", "n", "ambiguous program name for search").required()

    override fun execute() {
        println("id\trecorded_at\t\tchannelName\t\tdrops\ttsExists\ttitle")

        val programs = programCommand.selectByName(programSearchName, 500, 0)
        programs.forEach {
            val tsExists = programCommand.hasTsFile(it)
            val executedFile = executedFileCommand.find(it.executedFileId) ?: return@forEach
            val programInfo = "%d\t%s\t%s\t%d\t%b\t%s".format(it.id, datetimeFormat.format(executedFile.recordedAt), executedFile.channelName, it.drops ?: 0, tsExists, executedFile.title)
            val decoratedProgramInfo = decorateProgramInfo(it.drops ?: -1, programInfo)
            println(decoratedProgramInfo)
        }
    }

    private fun decorateProgramInfo(drops: Int, programInfo: String): String {
        return when {
            drops == 0 -> programInfo
            drops > 0 -> terminalTextColorComponent.error(programInfo)
            else -> terminalTextColorComponent.info(programInfo)
        }
    }
}

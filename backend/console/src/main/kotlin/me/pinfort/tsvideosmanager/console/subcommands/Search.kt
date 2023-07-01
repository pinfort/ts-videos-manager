package me.pinfort.tsvideosmanager.console.subcommands

import kotlinx.cli.ArgType
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand
import kotlinx.cli.required
import me.pinfort.tsvideosmanager.console.component.TerminalTextColorComponent
import me.pinfort.tsvideosmanager.infrastructure.command.ProgramCommand
import org.springframework.stereotype.Component
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalCli::class)
@Component
class Search(
    private val programCommand: ProgramCommand,
    private val terminalTextColorComponent: TerminalTextColorComponent
) : Subcommand("search", "search programs") {
    private val datetimeFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")

    private val programSearchName by option(ArgType.String, "name", "n", "ambiguous program name for search").required()

    override fun execute() {
        println("id\trecorded_at\t\tchannelName\t\tdrops\ttsExists\ttitle")

        val programs = programCommand.selectByName(programSearchName, 500, 0) // TODO: 全部JOINにしてSQL発行回数をなんとかする
        programs.forEach {
            val tsExists = programCommand.hasTsFile(it)
            val programInfo = "%d\t%s\t%s\t%d\t%b\t%s".format(it.id, datetimeFormat.format(it.recordedAt), it.channelName, it.drops, tsExists, it.title)
            val decoratedProgramInfo = decorateProgramInfo(it.drops, programInfo)
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

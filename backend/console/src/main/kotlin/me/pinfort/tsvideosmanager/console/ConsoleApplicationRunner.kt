package me.pinfort.tsvideosmanager.console

import kotlinx.cli.ArgParser
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand
import me.pinfort.tsvideosmanager.console.subcommands.Delete
import me.pinfort.tsvideosmanager.console.subcommands.Get
import me.pinfort.tsvideosmanager.console.subcommands.Modify
import me.pinfort.tsvideosmanager.console.subcommands.Search
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@OptIn(ExperimentalCli::class)
@Component
class ConsoleApplicationRunner(
    private val search: Search,
    private val get: Get,
    private val delete: Delete,
    private val modify: Modify
) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        val subCommands: List<Subcommand> = listOf(
            search,
            get,
            delete,
            modify
        )

        val parser = ArgParser("tsVideosManager")
        parser.subcommands(*subCommands.toTypedArray())

        parser.parse(args?.sourceArgs ?: emptyArray())
    }
}

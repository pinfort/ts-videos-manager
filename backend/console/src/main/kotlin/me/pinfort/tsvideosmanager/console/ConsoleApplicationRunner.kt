package me.pinfort.tsvideosmanager.console

import kotlinx.cli.ArgParser
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand
import me.pinfort.tsvideosmanager.console.subcommands.Search
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

@OptIn(ExperimentalCli::class)
@Component
class ConsoleApplicationRunner(
    private val search: Search,
) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        val subCommands: List<Subcommand> = listOf(
            search,
        )

        val parser = ArgParser("tsVideosManager")
        parser.subcommands(*subCommands.toTypedArray())

        parser.parse(args?.sourceArgs ?: emptyArray())
    }
}

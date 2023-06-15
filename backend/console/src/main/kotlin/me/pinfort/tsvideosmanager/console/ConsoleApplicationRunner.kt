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
class ConsoleApplicationRunner : ApplicationRunner {
    companion object {
        val subCommands: List<KClass<out Subcommand>> = listOf(
            Search::class
        )
    }

    override fun run(args: ApplicationArguments?) {
        val parser = ArgParser("tsVideosManager")
        parser.subcommands(*subCommands.map { it.createInstance() }.toTypedArray())

        parser.parse(args?.sourceArgs ?: emptyArray())
    }
}

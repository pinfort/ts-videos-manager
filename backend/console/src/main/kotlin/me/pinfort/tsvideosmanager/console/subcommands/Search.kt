package me.pinfort.tsvideosmanager.console.subcommands

import kotlinx.cli.ArgType
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand
import me.pinfort.tsvideosmanager.infrastructure.database.mapper.ProgramMapper

@OptIn(ExperimentalCli::class)
class Search(
    private val programMapper: ProgramMapper
) : Subcommand("search", "search programs") {
    val programSearchName by option(ArgType.String, "name", "n", "ambiguous program name for search")

    override fun execute() {
        println("test")
    }
}

package me.pinfort.tsvideosmanager.console.subcommands

import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand

@OptIn(ExperimentalCli::class)
class Search: Subcommand("search", "search programs") {
    override fun execute() {
        println("test")
    }
}

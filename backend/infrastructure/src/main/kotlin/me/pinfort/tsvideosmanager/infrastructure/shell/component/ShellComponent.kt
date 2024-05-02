package me.pinfort.tsvideosmanager.infrastructure.shell.component

import me.pinfort.tsvideosmanager.infrastructure.component.EnvironmentComponent
import me.pinfort.tsvideosmanager.infrastructure.exception.InvalidEnvironmentException
import me.pinfort.tsvideosmanager.infrastructure.shell.client.ShellClient
import java.io.File

class ShellComponent(
    private val environmentComponent: EnvironmentComponent,
    private val shellClient: ShellClient
) {
    fun executeOnWindows(workingDir: File, commands: String, timeout: Long = 600): Int {
        if (!environmentComponent.runningOnWindows()) {
            throw InvalidEnvironmentException("This function must be called on Windows only.")
        }

        return shellClient.execute(workingDir, commands, timeout)
    }
}

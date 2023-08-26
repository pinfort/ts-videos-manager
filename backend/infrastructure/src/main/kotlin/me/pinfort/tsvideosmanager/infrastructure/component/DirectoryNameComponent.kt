package me.pinfort.tsvideosmanager.infrastructure.component

import org.springframework.stereotype.Component
import java.nio.file.Path
import kotlin.io.path.name

@Component
class DirectoryNameComponent(
    private val normalizeNameComponent: NormalizeNameComponent
) {
    fun indexDirectoryName(path: Path): String {
        return programDirectoryName(path).take(1)
    }

    fun programDirectoryName(path: Path): String {
        return normalizeNameComponent.normalize(path.parent.name)
    }
}

package me.pinfort.tsvideosmanager.api.response

import me.pinfort.tsvideosmanager.infrastructure.structs.CreatedFile
import me.pinfort.tsvideosmanager.infrastructure.structs.Program

data class ProgramDetailResponse(
    val program: Program,
    val videoFiles: List<CreatedFile>,
)

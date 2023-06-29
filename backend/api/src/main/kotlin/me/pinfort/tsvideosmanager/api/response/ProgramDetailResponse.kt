package me.pinfort.tsvideosmanager.api.response

import me.pinfort.tsvideosmanager.infrastructure.structs.CreatedFile
import me.pinfort.tsvideosmanager.infrastructure.structs.ProgramDetail

data class ProgramDetailResponse(
    val program: ProgramDetail,
    val videoFiles: List<CreatedFile>
)

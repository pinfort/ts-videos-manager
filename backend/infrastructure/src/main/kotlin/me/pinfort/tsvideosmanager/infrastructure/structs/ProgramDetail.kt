package me.pinfort.tsvideosmanager.infrastructure.structs

import java.time.LocalDateTime

data class ProgramDetail(
    val id: Int,
    val name: String,
    val executedFileId: Int,
    val status: Program.Status,
    val drops: Int,
    val size: Long,
    val recordedAt: LocalDateTime,
    val channel: String,
    val title: String,
    val channelName: String,
    val duration: Double,
    val createdFiles: List<CreatedFile>
)

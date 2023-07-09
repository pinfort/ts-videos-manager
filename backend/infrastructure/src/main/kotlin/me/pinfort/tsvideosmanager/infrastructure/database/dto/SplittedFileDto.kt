package me.pinfort.tsvideosmanager.infrastructure.database.dto

data class SplittedFileDto(
    val id: Int,
    val executedFileId: Int,
    val file: String,
    val size: Long,
    val duration: Double,
    val status: Status
) {
    enum class Status {
        REGISTERED,
        COMPRESS_SAVED,
        ENCODE_TASK_ADDED
    }
}

package me.pinfort.tsvideosmanager.infrastructure.database.dto

data class ProgramDto(
    val id: Int,
    val name: String,
    val executedFileId: Int,
    val status: Status,
) {
    enum class Status {
        REGISTERED,
        COMPLETED,
        ERROR,
    }
}

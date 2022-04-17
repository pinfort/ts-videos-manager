package me.pinfort.tsvideosmanager.infrastructure.structs

data class Program(
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

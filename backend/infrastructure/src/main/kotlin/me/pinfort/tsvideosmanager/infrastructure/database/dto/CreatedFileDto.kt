package me.pinfort.tsvideosmanager.infrastructure.database.dto

data class CreatedFileDto(
    val id: Int,
    val splittedFileId: Int,
    val file: String,
    val size: Long,
    val mime: String?,
    val encoding: String?,
    val status: Status
) {
    enum class Status {
        REGISTERED,
        ENCODE_SUCCESS,
        FILE_MOVED
    }
}

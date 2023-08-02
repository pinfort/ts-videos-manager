package me.pinfort.tsvideosmanager.infrastructure.structs

data class CreatedFile(
    val id: Long,
    val splittedFileId: Long,
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

    val isMp4 = mime == "video/mp4"

    val isTs = mime == "video/vnd.dlna.mpeg-tts"
}

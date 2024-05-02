package me.pinfort.tsvideosmanager.infrastructure.structs

import me.pinfort.tsvideosmanager.infrastructure.exception.InvalidFileNameException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class FileName(
    val recordedAt: LocalDateTime,
    val channel: String,
    val channelName: String,
    val title: String
) {
    fun toFileNameString(): String {
        return "[${recordedAt.format(DateTimeFormatter.ofPattern("yyMMdd-HHmm"))}]" +
            "[$channel]" +
            "[$channelName]" +
            title
    }

    companion object {
        fun fromFileNameString(fileName: String): FileName {
            val parts = fileName.split("]")
            if (parts.size < 4) {
                throw InvalidFileNameException("filename parsing error")
            }
            return FileName(
                recordedAt = LocalDateTime.parse(parts[0].substring(1), DateTimeFormatter.ofPattern("yyMMdd-HHmm")),
                channel = parts[1].substring(1),
                channelName = parts[2].substring(1),
                title = parts.slice(3 until parts.size).joinToString("]")
            )
        }
    }
}

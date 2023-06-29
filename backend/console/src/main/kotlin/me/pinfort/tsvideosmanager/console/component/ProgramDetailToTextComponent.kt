package me.pinfort.tsvideosmanager.console.component

import me.pinfort.tsvideosmanager.infrastructure.structs.ProgramDetail
import org.springframework.stereotype.Component
import java.time.format.DateTimeFormatter
import kotlin.time.Duration.Companion.minutes
import kotlin.time.DurationUnit

@Component
class ProgramDetailToTextComponent {
    private val datetimeFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")

    fun convertConsole(programDetail: ProgramDetail): String {
        val videoFiles = programDetail.createdFiles
        val sb = StringBuilder()
        sb.appendLine("番組ID: ${programDetail.id}")
        sb.appendLine("番組名: ${programDetail.title}")
        sb.appendLine("放送局: ${programDetail.channelName}")
        sb.appendLine("放送日時: ${programDetail.recordedAt.format(datetimeFormat)}")
        sb.appendLine("放送時間: ${programDetail.duration.minutes.toString(DurationUnit.MINUTES)}")
        sb.appendLine("ファイル: ${videoFiles.size}件")
        sb.appendLine("mime\tname")
        videoFiles.forEach {
            sb.appendLine("${it.mime}\t${it.file}")
        }
        return sb.toString()
    }
}

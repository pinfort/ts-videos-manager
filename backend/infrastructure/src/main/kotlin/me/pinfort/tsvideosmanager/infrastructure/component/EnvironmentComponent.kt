package me.pinfort.tsvideosmanager.infrastructure.component

import org.springframework.stereotype.Component

@Component
class EnvironmentComponent {
    fun getOs(): String {
        return System.getProperty("os.name").lowercase() // java.lang.Systemはmockkstaticできないのでテストしない
    }

    fun runningOnWindows(): Boolean {
        return getOs() == "windows"
    }
}

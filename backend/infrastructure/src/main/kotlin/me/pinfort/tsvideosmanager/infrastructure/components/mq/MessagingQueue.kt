package me.pinfort.tsvideosmanager.infrastructure.components.mq

interface MessagingQueue {
    fun connect() {}
    fun disconnect() {}
}

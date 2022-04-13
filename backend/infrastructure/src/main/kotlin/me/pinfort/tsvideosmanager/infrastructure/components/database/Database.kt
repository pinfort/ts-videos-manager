package me.pinfort.tsvideosmanager.infrastructure.components.database

interface Database {
    fun connect() {}
    fun disConnect() {}
}
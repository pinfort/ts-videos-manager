package me.pinfort.tsvideosmanager.api.response

import me.pinfort.tsvideosmanager.infrastructure.structs.Program

data class SearchResponse(
    val programs: List<Program>
)

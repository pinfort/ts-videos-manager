package response

import structs.Program

data class SearchResponse(
    val programs: List<Program>,
)

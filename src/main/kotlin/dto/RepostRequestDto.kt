package dto

import java.time.LocalDateTime

data class RepostRequestDto(
    val id: Int,
    val author: String,
    val content: String,
    val created: LocalDateTime,
    val originalPostId: Int
)

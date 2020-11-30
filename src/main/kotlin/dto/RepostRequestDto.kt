package dto

data class RepostRequestDto(
    val id: Int,
    val author: String,
    val content: String,
    val created: Int,
    val originalPostId: Int
)

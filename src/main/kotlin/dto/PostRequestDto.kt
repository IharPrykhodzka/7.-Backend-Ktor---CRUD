package dto

import model.Advertising
import model.Location
import model.PostType
import model.Video

data class PostRequestDto(
    val id: Int,
    val author: String,
    val content: String,
    val address: String?,
    val location: Location?,
    val video: Video?,
    val advertising: Advertising?,
    val postType: PostType?
)

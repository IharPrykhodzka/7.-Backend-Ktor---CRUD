package dto

import model.*
import java.time.LocalDateTime

data class PostResponseDto(
    val id: Int,
    val author: String,
    val content: String,
    val created: LocalDateTime,
    val likesCount: Int,
    val commentsCount: Int,
    val shareCount: Int,
    val likedByMe: Boolean,
    val commentedByMe: Boolean,
    val sharedByMe: Boolean,
    val address: String?,
    val location: Location?,
    val video: Video?,
    val advertising: Advertising?,
    val source: PostModel?,
    val postType: PostType,
    val isHidden: Boolean,
    val timesShown: Long
) {
    companion object {
        fun fromModel(model: PostModel) = PostResponseDto(
            id = model.id,
            author = model.author,
            content = model.content,
            created = model.created,
            likesCount = model.likesCount,
            commentsCount = model.commentsCount,
            shareCount = model.shareCount,
            likedByMe = model.likedByMe,
            commentedByMe = model.commentedByMe,
            sharedByMe = model.sharedByMe,
            address = model.address,
            location = model.location,
            video = model.video,
            advertising = model.advertising,
            source = model.source,
            postType = model.postType,
            isHidden = model.isHidden,
            timesShown = model.timesShown
        )
    }
}

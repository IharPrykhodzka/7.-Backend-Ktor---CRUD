package dto

import model.AttachmentModel
import model.PostModel
import model.PostType

data class PostResponseDto(
    val id: Int,
    val author: String,
    val content: String,
    val created: Int,
    val likesCount: Int,
    val commentsCount: Int,
    val shareCount: Int,
    val likedByMe: Boolean,
    val commentedByMe: Boolean,
    val sharedByMe: Boolean,
    val link: String? = null,
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
            link = model.link,
            source = model.source,
            postType = model.postType,
            isHidden = model.isHidden,
            timesShown = model.timesShown
        )
    }
}

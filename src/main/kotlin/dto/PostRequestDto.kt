package dto

import model.AttachmentModel
import model.PostModel
import model.PostType

data class PostRequestDto(
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
    val link: String?,
    val source: PostModel?,
    val postType: PostType,
    val isHidden: Boolean,
    val timesShown: Long,
    val attachmentModel: AttachmentModel
) {
    companion object {
        fun toModel(dto: PostRequestDto) = PostModel(
            id = dto.id,
            author = dto.author,
            content = dto.content,
            created = dto.created,
            likesCount = dto.likesCount,
            commentsCount = dto.commentsCount,
            shareCount = dto.shareCount,
            likedByMe = dto.likedByMe,
            commentedByMe = dto.commentedByMe,
            sharedByMe = dto.sharedByMe,
            link = dto.link,
            source = dto.source,
            postType = dto.postType,
            isHidden = dto.isHidden,
            timesShown = dto.timesShown,
            attachment = dto.attachmentModel
        )
    }
}

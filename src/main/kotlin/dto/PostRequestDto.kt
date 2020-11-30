package dto

import model.*

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
    val address: String?,
    val location: Location?,
    val video: Video?,
    val advertising: Advertising?,
    val source: PostModel?,
    val postType: PostType,
    val isHidden: Boolean
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
            address = dto.address,
            location = dto.location,
            video = dto.video,
            advertising = dto.advertising,
            source = dto.source,
            postType = dto.postType,
            isHidden = dto.isHidden
        )
    }
}

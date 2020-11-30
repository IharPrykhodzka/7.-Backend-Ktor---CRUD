package model

data class PostModel(
    val id: Int,
    val author: String,
    val content: String = "",
    val created: Int = (System.currentTimeMillis() / 1000).toInt(),
    val likesCount: Int = 0,
    val commentsCount: Int = 0,
    val shareCount: Int = 0,
    val likedByMe: Boolean = false,
    val commentedByMe: Boolean = false,
    val sharedByMe: Boolean = false,
    val link: String? = null,
    val source: PostModel? = null,
    val postType: PostType = PostType.SIMPLE_POST,
    val isHidden: Boolean = false,
    val timesShown: Long = 0,
    val attachment: AttachmentModel? = null
) {
}
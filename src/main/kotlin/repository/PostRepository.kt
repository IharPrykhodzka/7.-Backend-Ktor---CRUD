package repository

import model.PostModel

interface PostRepository {
    suspend fun getAll(): List<PostModel>
    suspend fun getById(id: Int): PostModel?
    suspend fun save(item: PostModel): PostModel
    suspend fun removeById(id: Int)
    suspend fun likeById(id: Int): PostModel?
    suspend fun dislikeById(id: Int): PostModel?
    suspend fun getRecent(count: Int): List<PostModel>
    suspend fun getPostsAfter(id: Int): List<PostModel>
    suspend fun getPostsCreatedBefore(idCurPost: Int, countUploadedPosts: Int): List<PostModel>
}

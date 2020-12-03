package service

import dto.PostRequestDto
import dto.PostResponseDto
import error.ForbiddenException
import io.ktor.features.*
import model.UserModel
import repository.PostRepository

class PostService(
    val repo: PostRepository
) {
    suspend fun getAll(): List<PostResponseDto> = repo.getAll().map(PostResponseDto.Companion::fromModel)

    suspend fun getByID(id: Int): PostResponseDto {
        val model = repo.getById(id) ?: throw NotFoundException()
        return PostResponseDto.fromModel(model)
    }

    suspend fun save(request: PostRequestDto, user: UserModel): PostResponseDto {
        if (user.userName != request.author) {
            throw ForbiddenException("Невозможно редактировать!")
        }
        val model = repo.save(
            PostRequestDto.toModel(request)
        )
        return PostResponseDto.fromModel(model)
    }

    suspend fun deleteById(id: Int, user: UserModel): PostResponseDto{
        val model = repo.getById(id) ?: throw NotFoundException()

        if (user.userName != model.author) {
            throw ForbiddenException("Невозможно удалить пост!")
        }
        val response = PostResponseDto.fromModel(model)
        repo.removeById(id)
        return response
    }

    suspend fun likeById(id: Int): PostResponseDto {
        val model = repo.getById(id) ?: throw NotFoundException()
        val copy = model.copy(likesCount =  model.likesCount.inc())
        return PostResponseDto.fromModel(repo.save(copy))
    }

    suspend fun dislikeById(id: Int): PostResponseDto {
        val model = repo.getById(id) ?: throw NotFoundException()
        val copy = model.copy(likesCount = model.likesCount.dec())
        return PostResponseDto.fromModel(repo.save(copy))
    }
}
package route

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.kodein.di.generic.instance
import org.kodein.di.ktor.kodein
import dto.PostRequestDto
import dto.PostResponseDto
import dto.RepostRequestDto
import model.PostModel
import model.PostType
import repository.PostRepository

fun Routing.v1() {

    //раскомментировать для отладки
    trace { application.log.trace(it.buildText()) }

    val repo by kodein().instance<PostRepository>()

    route("/") {
        get {
            call.respondText("Hello!!!", ContentType.Text.Plain)
        }
    }

    route("/api/v1/posts") {

        get {
            val response = repo.getAll().map { PostResponseDto.fromModel(it) }
            call.respond(response)
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: throw ParameterConversionException("id", "Int")
            val model = repo.getById(id) ?: throw NotFoundException()
            val response = PostResponseDto.fromModel(model)
            call.respond(response)
        }

        get("/{id}/like") {
            val id = call.parameters["id"]?.toIntOrNull() ?: throw ParameterConversionException("id", "Int")
            val model = repo.likeById(id) ?: throw NotFoundException()
            val response = PostResponseDto.fromModel(model)
            call.respond(response)
        }

        get("/{id}/dislike") {
            val id = call.parameters["id"]?.toIntOrNull() ?: throw ParameterConversionException("id", "Int")
            val model = repo.dislikeById(id) ?: throw NotFoundException()
            val response = PostResponseDto.fromModel(model)
            call.respond(response)
        }

        post {
            val post = call.receive<PostRequestDto>()
            val model = PostModel(
                post.id,
                post.author,
                post.content,
                address = post.address,
                location = post.location,
                video = post.video,
                postType = post.postType ?: PostType.SIMPLE_POST
            )
            val newPost = repo.save(model)
            val response = PostResponseDto.fromModel(newPost)
            call.respond(response)
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: throw ParameterConversionException("id", "Int")
            repo.removeById(id)
            call.respond(HttpStatusCode.NoContent)
        }
    }

    route("/api/v1/repost") {
        post {
            val post = call.receive<RepostRequestDto>()
            val originalPost = repo.getById(post.originalPostId) ?: throw NotFoundException()
            val model = PostModel(
                post.id,
                post.author,
                post.content,
                post.created,
                source = originalPost,
                postType = PostType.REPOST
            )
            val newPost = repo.save(model)
            val response = PostResponseDto.fromModel(newPost)
            call.respond(response)
        }
    }

    route("/api/v1/share") {
        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: throw ParameterConversionException("id", "Int")
            //что-то альтернативное можно сделать здесь?
            val model = repo.getById(id) ?: throw NotFoundException()
            val response = PostResponseDto.fromModel(model)
            call.respond(response)
        }
    }
}

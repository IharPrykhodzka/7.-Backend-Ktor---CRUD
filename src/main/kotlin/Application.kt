import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.netty.*
import io.ktor.util.*
import kotlinx.coroutines.runBlocking
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton
import org.kodein.di.ktor.KodeinFeature
import model.PostModel
import repository.PostRepository
import repository.PostRepositoryMutexImpl
import route.v1
import java.time.LocalDateTime

fun main(args: Array<String>) {
    EngineMain.main(args)
}

@KtorExperimentalAPI
fun Application.module(testing: Boolean = false) {
    println(testing)

    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
            serializeNulls()
        }
    }

    install(Routing) {
        v1()
    }

    install(StatusPages) {
        exception<ParameterConversionException> { e ->
            call.respond(HttpStatusCode.BadRequest)
            throw e
        }
        exception<NotFoundException> { e ->
            call.respond(HttpStatusCode.NotFound)
            throw e
        }
        exception<Throwable> { e ->
            call.respond(HttpStatusCode.InternalServerError)
            throw e
        }
    }

    install(KodeinFeature) {
        bind<PostRepository>() with singleton {
            PostRepositoryMutexImpl().apply {
                runBlocking {
                    repeat(10) {
                        save(
                            PostModel(
                                it,
                                "author_$it",
                                "test_$it",
                                LocalDateTime.of(2020, 2, 23, 12, 12, 12)
                            )
                        )
                    }
                }
            }
        }
    }


}

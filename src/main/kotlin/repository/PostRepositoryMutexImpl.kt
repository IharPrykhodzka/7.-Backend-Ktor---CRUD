package repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import model.*
import java.time.LocalDateTime
import kotlin.coroutines.EmptyCoroutineContext

class PostRepositoryMutexImpl : PostRepository {
    private var nextId = 1
    private val items = mutableListOf<PostModel>()
    private val mutex = Mutex()

    override suspend fun getAll(): List<PostModel> = mutex.withLock {
        val newItems = items.map { it.copy(timesShown = it.timesShown + 1) }
        items.clear()
        items.addAll(newItems)
        items.reversed()
    }

    override suspend fun getById(id: Int): PostModel? = mutex.withLock {
        when (val index = items.indexOfFirst { it.id == id }) {
            -1 -> null
            else -> {
                val item = items[index]
                val newItem = item.copy(timesShown = item.timesShown + 1)
                items[index] = newItem
                newItem
            }
        }
    }

    override suspend fun save(item: PostModel): PostModel = mutex.withLock {
        when (val index = items.indexOfFirst { it.id == item.id }) {
            -1 -> {
                val copy = item.copy(id = nextId++)
                items.add(copy)
                copy
            }
            else -> {
                items[index] = item
                item
            }
        }
    }

    override suspend fun removeById(id: Int) {
        mutex.withLock {
            items.removeIf { it.id == id }
        }
    }

    override suspend fun likeById(id: Int): PostModel? = mutex.withLock {
        when (val index = items.indexOfFirst { it.id == id }) {
            -1 -> null
            else -> {
                val item = items[index]
                val newItem = item.copy(likesCount = item.likesCount + 1)
                items[index] = newItem
                newItem
            }
        }
    }

    override suspend fun dislikeById(id: Int): PostModel? = mutex.withLock {
        when (val index = items.indexOfFirst { it.id == id }) {
            -1 -> null
            else -> {
                val item = items[index]
                val newItem = if (item.likesCount > 0) item.copy(likesCount = item.likesCount - 1) else item
                items[index] = newItem
                newItem
            }
        }
    }

    fun generateContent(): List<PostModel> {

        val createdTime = LocalDateTime.of(2020, 10, 1, 11, 11, 11)

        val postsList = mutableListOf(
            PostModel(
                1,
                "Igor Prikhodko",
                "Something",
                createdTime,
                likesCount = 1,
                shareCount = 2,
                likedByMe = true,
                sharedByMe = true
            ),
            PostModel(
                2,
                "Tarzan from Africa",
                "AAAAAaaaaAAAAAaaaAAAAaaa!!!!",
                createdTime.plusHours(1),
                likesCount = 10,
                video = Video("https://www.youtube.com/watch?v=rxr3OzyGcyE"),
                postType = PostType.VIDEO_POST
            ),
            PostModel(
                3,
                "Shaverma",
                "12:00 free 10 minutes",
                createdTime.plusDays(1),
                commentsCount = 3,
                shareCount = 2,
                commentedByMe = true,
                address = "Санкт-Петербург, Коменданский пр.",
                location = 60.012878 x 30.252335,
                postType = PostType.EVENT_POST
            ),
            PostModel(
                4,
                "WarMan",
                "The World is mine!!!",
                createdTime.plusMonths(1),
                likesCount = 5,
                shareCount = 2,
                likedByMe = true
            ),
            PostModel(
                5,
                "Brutal BatMan",
                "Rock in my life!",
                createdTime,
                likesCount = 100,
                commentsCount = 3,
                shareCount = 2,
                likedByMe = true,
                sharedByMe = true,
                advertising = Advertising(
                    "https://static-ru.insales.ru/images/products/1/6335/205330623/460c2a624899693ea071e424032b89c5572eaa0a.jpg",
                    "https://www.arhybes.com/video-batmetal"
                ),
                postType = PostType.ADVERTISING
            )
        )

        postsList.add(
            PostModel(
                6,
                "CrazyMan",
                "I want to see the world in the fire!!!",
                createdTime.minusMonths(1),
                likesCount = 2,
                commentsCount = 0,
                shareCount = 0,
                likedByMe = true,
                sharedByMe = false,
                source = postsList[4],
                postType = PostType.REPOST
            )
        )
        return postsList
    }

    fun main() {

        CoroutineScope(EmptyCoroutineContext).launch {
            mutex.withLock {
                items.addAll(generateContent())
            }
        }

    }
}



//fun main() {
//
//    val repo = PostRepositoryMutexImpl()
//    val scope = CoroutineScope(EmptyCoroutineContext + SupervisorJob())
//
//
//    scope.launch {
//        repo.save(PostModel(id = 0, author = "Test"))
//    }
//
//
//    Thread.sleep(1000)
//    with(CoroutineScope(EmptyCoroutineContext + SupervisorJob())) {
//
//        launch {
//            repo.likeById(1)
//        }
//    }
//
//    with(CoroutineScope(EmptyCoroutineContext + SupervisorJob())) {
//        launch {
//            println(repo.getById(1))
//            repo.removeById(1)
//            println("After remove ${repo.getById(1)}")
//        }
//    }
//
//
//    Thread.sleep(2500)
//    runBlocking {
//        val all = repo.getAll()
//        println(all.size)
//        File("result.json").writeText(Gson().toJson(all))
//    }
//
//}

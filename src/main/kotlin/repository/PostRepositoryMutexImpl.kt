package repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import model.AttachmentModel
import model.AttachmentType
import model.PostModel
import model.PostType
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
        val timeMillis = (System.currentTimeMillis() / 1000).toInt()

        val postsList = mutableListOf(
            PostModel(
                1,
                "Igor Prikhodko",
                "Something",
                timeMillis + 5_000,
                likesCount = 1,
                shareCount = 2,
                likedByMe = true,
                sharedByMe = true
            ),
            PostModel(
                2,
                "Tarzan from Africa",
                "AAAAAaaaaAAAAAaaaAAAAaaa!!!!",
                timeMillis + 10_000,
                likesCount = 10,
                link = "https://www.youtube.com/watch?v=rxr3OzyGcyE",
                postType = PostType.VIDEO_POST,
                attachment = AttachmentModel("Rek", "https://www.youtube.com/watch?v=rxr3OzyGcyE", AttachmentType.VIDEO)
            ),
            PostModel(
                3,
                "WarMan",
                "The World is mine!!!",
                timeMillis - 10_000,
                likesCount = 5,
                shareCount = 2,
                likedByMe = true
            ),
            PostModel(
                5,
                "Brutal BatMan",
                "Rock in my life!",
                timeMillis,
                likesCount = 100,
                commentsCount = 3,
                shareCount = 2,
                likedByMe = true,
                sharedByMe = true,
                link = "https://www.arhybes.com/video-batmetal",
                postType = PostType.ADVERTISING,
                attachment = AttachmentModel("Funny Batman", "https://www.arhybes.com/video-batmetal", AttachmentType.VIDEO)
            )
        )

        postsList.add(
            PostModel(
                6,
                "CrazyMan",
                "I want to see the world in the fire!!!",
                timeMillis + 20_000,
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


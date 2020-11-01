package repository

import model.PostModel

class PostRepositoryInMemoryImpl : PostRepository {
    private var nextId = 1
    private val items = mutableListOf<PostModel>()

    override suspend fun getAll(): List<PostModel> {
        val newItems = items.map { it.copy(timesShown = it.timesShown + 1) }
        items.clear()
        items.addAll(newItems)
        return items.reversed()
    }

    override suspend fun getById(id: Int): PostModel? =
        when (val index = items.indexOfFirst { it.id == id }) {
            -1 -> null
            else -> {
                val item = items[index]
                val newItem = item.copy(timesShown = item.timesShown + 1)
                items[index] = newItem
                newItem
            }
        }

    override suspend fun save(item: PostModel): PostModel {
        return when (val index = items.indexOfFirst { it.id == item.id }) {
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
        items.removeIf { it.id == id }
    }

    override suspend fun likeById(id: Int): PostModel? {
        return when (val index = items.indexOfFirst { it.id == id }) {
            -1 -> null
            else -> {
                val item = items[index]
                val newItem = item.copy(likesCount = item.likesCount + 1)
                items[index] = newItem
                newItem
            }
        }
    }

    override suspend fun dislikeById(id: Int): PostModel? {
        return when (val index = items.indexOfFirst { it.id == id }) {
            -1 -> null
            else -> {
                val item = items[index]
                val newItem = if (item.likesCount > 0) item.copy(likesCount = item.likesCount - 1) else item
                items[index] = newItem
                newItem
            }
        }
    }
}

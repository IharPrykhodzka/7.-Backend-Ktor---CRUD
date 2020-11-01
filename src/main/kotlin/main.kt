import com.google.gson.Gson
import model.*
import java.time.LocalDateTime

fun main() {

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

    println(Gson().toJson(postsList))

//    val gson = GsonBuilder().apply {
//        setPrettyPrinting()
//        serializeNulls()
//    }.create()
//    Files.write(Paths.get("./listPosts.json"), gson.toJson(postsList).toByteArray(), StandardOpenOption.CREATE)
}

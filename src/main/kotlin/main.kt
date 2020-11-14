
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm

fun main() {

    val secret = "5c2dbef6-289c-46e6-8cfd-d8b3292d373a"
    val algo = Algorithm.HMAC256(secret)
    val verifier: JWTVerifier = JWT.require(algo).build()
    println(secret)
}

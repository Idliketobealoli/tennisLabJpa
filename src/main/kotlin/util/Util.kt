package util

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.delay
import java.math.BigInteger
import java.security.MessageDigest

fun encode(text: String): String {
    val md = MessageDigest.getInstance("SHA-512")
    val messageDigest = md.digest(text.encodeToByteArray())
    val no = BigInteger(1, messageDigest)
    var hashText = no.toString(16)
    while (hashText.length < 32) {
        hashText = "0$hashText"
    }
    return hashText
}

fun betweenXandY(res: String, x: Int, y: Int): Boolean {
    val n = res.toIntOrNull() ?: return false
    return n in x..y
}

suspend fun waitingText(deferred: Deferred<Any?>) {
    while(!deferred.isCompleted) {
        for (i in 1..3) {
            print(".")
            delay(100)
        }
        println()
    }
}
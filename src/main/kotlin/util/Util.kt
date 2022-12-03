package util

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.delay
import java.math.BigInteger
import java.security.MessageDigest
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*


fun Double.toLocalMoney(locale: Locale): String {
    return NumberFormat.getCurrencyInstance(locale).format(this)
}

fun LocalDate.toLocalDate(locale: Locale): String {
    return this.format(
        DateTimeFormatter
            .ofLocalizedDate(FormatStyle.MEDIUM).withLocale(locale)
    )
}

fun Double.toLocalNumber(locale: Locale): String {
    return NumberFormat.getNumberInstance(locale).format(this)
}

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
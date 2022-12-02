package util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dto.Respuesta
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.delay
import java.math.BigInteger
import java.security.MessageDigest
import java.text.NumberFormat
import java.time.LocalDate
import java.time.LocalDateTime
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
    /*
    val hash = Hash()
    val tb = hash.blake2(text.toByteArray())
    return HEX.encode(tb)

     */
}

fun generateRespuesta(result: String, errorMessage: String): String {
    /*
    val respuesta = Respuesta(
        code =
        if (result.contentEquals(errorMessage))
            1707
        else 0,
        body = result
    )

     */
    return ""//respuesta.toJSON()
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

/*
fun generateRespuesta(result: List<DTO>): String {
    val respuesta = Respuesta(
        code =
        if (result.isEmpty())
            1707
        else 0,
        body = result
    )
    return respuesta.toJSON()
}

 */
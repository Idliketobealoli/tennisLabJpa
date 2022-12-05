package util

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.delay
import java.math.BigInteger
import java.security.MessageDigest

/**
 * @author Daniel Rodriguez Muñoz
 *
 * Funcion de codificacion de un String a SHA-512
 */
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

/**
 * @author Ivan Azagra Troya
 *
 * Funcion de parseado de string a entero y comprobacion de
 * si ese entero se encuentra dentro del rango pasado por parametro
 */
fun betweenXandY(res: String, x: Int, y: Int): Boolean {
    val n = res.toIntOrNull() ?: return false
    return n in x..y
}

/**
 * @author Daniel Rodriguez Muñoz
 *
 * Funcion que imprime un "." cada 0,1 segundos en grupos de 3 hasta que el deferred
 * que se le ha pasado por parametro sea completado.
 */
suspend fun waitingText(deferred: Deferred<Any?>) {
    while(!deferred.isCompleted) {
        for (i in 1..3) {
            print(".")
            delay(100)
        }
        println()
    }
}
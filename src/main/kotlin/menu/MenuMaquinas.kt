package menu

import controllers.EncordadoraController
import controllers.MaquinaController
import controllers.PersonalizadoraController
import dto.EncordadoraDTO
import dto.PersonalizadoraDTO
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull
import models.enums.Profile
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync
import util.betweenXandY
import util.waitingText
import java.time.LocalDate
import java.util.*

suspend fun menuMaquinas(profile: Profile) {
    var back = false
    while (!back) {
        println(" - Please select one of the following actions.")
        when (profile) {
            Profile.ADMIN -> {
                println("""
                    1. Find all maquinas
                    2. Find all encordadoras
                    3. Find all personalizadoras
                    4. Find maquina by [options]
                    5. Find all maquinas from [range/condition]
                    6. Create maquina
                    7. Update maquina
                    8. Delete maquina
                    9. Back
                """.trimIndent())
                var res = ""
                while (!betweenXandY(res, 1, 9)) {
                    res = readln()
                }
                when (res.toInt()) {
                    1 -> {
                        val maquinas = suspendedTransactionAsync(Dispatchers.IO) { MaquinaController.findAllMaquinas() }
                        waitingText(maquinas)
                        println(maquinas.await())
                    }
                    2 -> {
                        val maquinas = suspendedTransactionAsync(Dispatchers.IO) { EncordadoraController.findAllEncordadoras() }
                        waitingText(maquinas)
                        println(maquinas.await())
                    }
                    3 -> {
                        val maquinas = suspendedTransactionAsync(Dispatchers.IO) { PersonalizadoraController.findAllPersonalizadoras() }
                        waitingText(maquinas)
                        println(maquinas.await())
                    }
                    4 -> findBySomething()
                    5 -> findAllFromCondition()
                    6 -> create()
                    7 -> update()
                    8 -> delete()
                    9 -> back = true
                }
            }
            Profile.WORKER -> {
                println("""
                    1. Find all maquinas
                    2. Find all encordadoras
                    3. Find all personalizadoras
                    4. Find maquina by [options]
                    5. Find all maquinas from [range/condition]
                    6. Back
                """.trimIndent())
                var res = ""
                while (!betweenXandY(res, 1, 6)) {
                    res = readln()
                }
                when (res.toInt()) {
                    1 -> {
                        val maquinas = suspendedTransactionAsync(Dispatchers.IO) { MaquinaController.findAllMaquinas() }
                        waitingText(maquinas)
                        println(maquinas.await())
                    }
                    2 -> {
                        val maquinas = suspendedTransactionAsync(Dispatchers.IO) { EncordadoraController.findAllEncordadoras() }
                        waitingText(maquinas)
                        println(maquinas.await())
                    }
                    3 -> {
                        val maquinas = suspendedTransactionAsync(Dispatchers.IO) { PersonalizadoraController.findAllPersonalizadoras() }
                        waitingText(maquinas)
                        println(maquinas.await())
                    }
                    4 -> findBySomething()
                    5 -> findAllFromCondition()
                    6 -> back = true
                }
            }
            else -> { back = true }
        }
    }
}

private suspend fun delete() {
    println(" - Serial number of target maquina: ")
    val sNum = readln()
    val bm = suspendedTransactionAsync(Dispatchers.IO) { MaquinaController.getMaquinaBySerialNumberForCreation(sNum) }
    waitingText(bm)
    val baseMaquina = bm.await()
    if (baseMaquina == null) println("There are no maquinas with serial number: $sNum")
    else {
        println("Deleting will be a permanent action. " +
                "Do you really want to proceed? [y/n]")
        var input = ""
        while (!input.contentEquals("y") && !input.contentEquals("n"))
            input = readln()
        if (input.contentEquals("y")) {
            val result = suspendedTransactionAsync(Dispatchers.IO) { MaquinaController.deleteMaquina(baseMaquina) }
            println("Deleting maquina...")
            waitingText(result)
            println(result)
        }
    }
}

private suspend fun update() {
    println(" - Serial number of target maquina: ")
    val sNum = readln()
    val bm = suspendedTransactionAsync(Dispatchers.IO) { MaquinaController.getMaquinaBySerialNumberForCreation(sNum) }
    waitingText(bm)
    val baseMaquina = bm.await()
    if (baseMaquina == null) println("There are no maquinas with serial number: $sNum")
    else {
        println(" - Input new data: ")
        println(" - Modelo: ")
        val modelo = readln()
        println(" - Marca: ")
        val marca = readln()
        println(" - Numero de serie: ")
        val numSerie = readln()
        var input = ""
        println(" - Encordadora o Personalizadora? [e/p]")
        while (!input.contentEquals("e") &&
            !input.contentEquals("p"))
            input = readln()
        when (input) {
            "e" -> {
                var manual = ""
                var isManual = false
                println(" - Manual? [y/n]")
                while (!manual.contentEquals("y") &&
                    !manual.contentEquals("n"))
                    manual = readln()
                when (manual) {
                    "y" -> isManual = true
                    "n" -> isManual = false
                }
                println(" - Tension maxima?")
                val tensionMax = readln().toDoubleOrNull() ?: 0.0
                println(" - Tension minima?")
                val tensionMin = readln().toDoubleOrNull() ?: 0.0
                val newMaquina = EncordadoraDTO(
                    id = UUID.randomUUID(),
                    modelo = modelo,
                    marca = marca,
                    fechaAdquisicion = LocalDate.now(),
                    numeroSerie = numSerie,
                    isManual = isManual,
                    maxTension = tensionMax,
                    minTension = tensionMin
                )
                val result = suspendedTransactionAsync(Dispatchers.IO) { MaquinaController.insertMaquina(newMaquina) }
                waitingText(result)
                println(result.await())
            }
            "p" -> {
                var maneuv = ""
                var mManeu = false
                println(" - Mide maniobrabilidad? [y/n]")
                while (!maneuv.contentEquals("y") &&
                    !maneuv.contentEquals("n"))
                    maneuv = readln()
                when (maneuv) {
                    "y" -> mManeu = true
                    "n" -> mManeu = false
                }
                var balance = ""
                var mBalance = false
                println(" - Mide balance? [y/n]")
                while (!balance.contentEquals("y") &&
                    !balance.contentEquals("n"))
                    balance = readln()
                when (balance) {
                    "y" -> mBalance = true
                    "n" -> mBalance = false
                }
                var rigidity = ""
                var mRigidity = false
                println(" - Mide rigidez? [y/n]")
                while (!rigidity.contentEquals("y") &&
                    !rigidity.contentEquals("n"))
                    rigidity = readln()
                when (rigidity) {
                    "y" -> mRigidity = true
                    "n" -> mRigidity = false
                }
                val newMaquina = PersonalizadoraDTO(
                    id = UUID.randomUUID(),
                    modelo = modelo,
                    marca = marca,
                    fechaAdquisicion = LocalDate.now(),
                    numeroSerie = numSerie,
                    measuresManeuverability = mManeu,
                    measuresBalance = mBalance,
                    measuresRigidity = mRigidity
                )
                val result = suspendedTransactionAsync(Dispatchers.IO) { MaquinaController.insertMaquina(newMaquina) }
                waitingText(result)
                println(result.await())
            }
        }
    }
}

private suspend fun create() {
    var goBack = false
    while (!goBack) {
        println(" - Modelo: ")
        val modelo = readln()
        println(" - Marca: ")
        val marca = readln()
        println(" - Numero de serie: ")
        val numSerie = readln()
        val maquinaConNumSerie = suspendedTransactionAsync(Dispatchers.IO) { MaquinaController.getMaquinaBySerialNumberForCreation(numSerie) }
        var input = ""
        println(" - Encordadora o Personalizadora? [e/p]")
        while (!input.contentEquals("e") &&
            !input.contentEquals("p"))
            input = readln()
        when (input) {
            "e" -> {
                var manual = ""
                var isManual = false
                println(" - Manual? [y/n]")
                while (!manual.contentEquals("y") &&
                    !manual.contentEquals("n"))
                    manual = readln()
                when (manual) {
                    "y" -> isManual = true
                    "n" -> isManual = false
                }
                println(" - Tension maxima?")
                val tensionMax = readln().toDoubleOrNull() ?: 0.0
                println(" - Tension minima?")
                val tensionMin = readln().toDoubleOrNull() ?: 0.0
                if (maquinaConNumSerie.await() == null) {
                    val newMaquina = EncordadoraDTO(
                        id = UUID.randomUUID(),
                        modelo = modelo,
                        marca = marca,
                        fechaAdquisicion = LocalDate.now(),
                        numeroSerie = numSerie,
                        isManual = isManual,
                        maxTension = tensionMax,
                        minTension = tensionMin
                    )
                    val result = suspendedTransactionAsync(Dispatchers.IO) { MaquinaController.insertMaquina(newMaquina) }
                    waitingText(result)
                    println(result.await())
                    goBack = true
                }
                else {
                    println(" - This serial number is already registered. Do you want to go back? [y/n]")
                    var res = ""
                    while (!res.contentEquals("y") && !res.contentEquals("n")) res = readln()
                    if (res.contentEquals("y")) goBack = true
                }
            }
            "p" -> {
                var maneuv = ""
                var mManeu = false
                println(" - Mide maniobrabilidad? [y/n]")
                while (!maneuv.contentEquals("y") &&
                    !maneuv.contentEquals("n"))
                    maneuv = readln()
                when (maneuv) {
                    "y" -> mManeu = true
                    "n" -> mManeu = false
                }
                var balance = ""
                var mBalance = false
                println(" - Mide balance? [y/n]")
                while (!balance.contentEquals("y") &&
                    !balance.contentEquals("n"))
                    balance = readln()
                when (balance) {
                    "y" -> mBalance = true
                    "n" -> mBalance = false
                }
                var rigidity = ""
                var mRigidity = false
                println(" - Mide rigidez? [y/n]")
                while (!rigidity.contentEquals("y") &&
                    !rigidity.contentEquals("n"))
                    rigidity = readln()
                when (rigidity) {
                    "y" -> mRigidity = true
                    "n" -> mRigidity = false
                }
                if (maquinaConNumSerie.await() == null) {
                    val newMaquina = PersonalizadoraDTO(
                        id = UUID.randomUUID(),
                        modelo = modelo,
                        marca = marca,
                        fechaAdquisicion = LocalDate.now(),
                        numeroSerie = numSerie,
                        measuresManeuverability = mManeu,
                        measuresBalance = mBalance,
                        measuresRigidity = mRigidity
                    )
                    val result = suspendedTransactionAsync(Dispatchers.IO) { MaquinaController.insertMaquina(newMaquina) }
                    waitingText(result)
                    println(result.await())
                    goBack = true
                }
                else {
                    println(" - This serial number is already registered. Do you want to go back? [y/n]")
                    var res = ""
                    while (!res.contentEquals("y") && !res.contentEquals("n")) res = readln()
                    if (res.contentEquals("y")) goBack = true
                }
            }
        }
    }
}

suspend fun findAllFromCondition() {
    var input = ""
    println("""
        .-----------  Select a field to search by: {comand}_[arguments]  -----------.
        |                                                                           |
        | {fecha de adquisicion}     _[>/<]_[fecha] (en ISO  yyyy-mm-dd)            |
        | {manual?}                  _[y/n]         (exclusive to Encordadoras)     |
        | {measures maneuverability?}_[y/n]         (exclusive to Personalizadoras) |
        | {measures balance?}        _[y/n]         (exclusive to Personalizadoras) |
        | {measures rigidity?}       _[y/n]         (exclusive to Personalizadoras) |
        |___________________________________________________________________________|
    """.trimIndent())
    while (!input.startsWith("fecha de adquisicion") &&
        !input.startsWith("manual?") &&
        !input.startsWith(" maneuverability?") &&
        !input.startsWith("balance?") &&
        !input.startsWith("measures rigidity?"))
        input = readln()
    val args = input.split("_")
    when (args[0]) {
        "fecha de adquisicion" -> {
            if (args.size == 3 &&
                (args[1].contentEquals(">") ||
                        args[1].contentEquals("<"))) {
                val fecha = LocalDate.parse(args[2]) ?: null
                if (fecha != null) {
                    val result = suspendedTransactionAsync(Dispatchers.IO) { MaquinaController.findAllMaquinasByAcquisitionDate(fecha, args[1]) }
                    waitingText(result)
                    println(result.await())
                }
                else println("Incorrect date")
            }
            else println("Incorrect parameters")
        }
        "manual?" -> {
            if (args.size == 2 &&
                (args[1].contentEquals("y") ||
                        args[1].contentEquals("n"))) {
                lateinit var result: Deferred<String>
                when (args[1]) {
                    "y" -> result = suspendedTransactionAsync(Dispatchers.IO) { EncordadoraController.findAllManuales(true) }
                    "n" -> result = suspendedTransactionAsync(Dispatchers.IO) { EncordadoraController.findAllManuales(false) }
                }
                waitingText(result)
                println(result.await())
            }
            else println("incorrect parameters")
        }
        "measures maneuverability?" -> {
            if (args.size == 2 &&
                (args[1].contentEquals("y") ||
                        args[1].contentEquals("n"))) {
                lateinit var result: Deferred<String>
                when (args[1]) {
                    "y" -> result = suspendedTransactionAsync(Dispatchers.IO) { PersonalizadoraController.findAllManeuverability(true) }
                    "n" -> result = suspendedTransactionAsync(Dispatchers.IO) { PersonalizadoraController.findAllManeuverability(false) }
                }
                waitingText(result)
                println(result.await())
            }
            else println("incorrect parameters")
        }
        "measures balance?" -> {
            if (args.size == 2 &&
                (args[1].contentEquals("y") ||
                        args[1].contentEquals("n"))) {
                lateinit var result: Deferred<String>
                when (args[1]) {
                    "y" -> result = suspendedTransactionAsync(Dispatchers.IO) { PersonalizadoraController.findAllBalance(true) }
                    "n" -> result = suspendedTransactionAsync(Dispatchers.IO) { PersonalizadoraController.findAllBalance(false) }
                }
                waitingText(result)
                println(result.await())
            }
            else println("incorrect parameters")
        }
        "measures rigidity?" -> {
            if (args.size == 2 &&
                (args[1].contentEquals("y") ||
                        args[1].contentEquals("n"))) {
                lateinit var result: Deferred<String>
                when (args[1]) {
                    "y" -> result = suspendedTransactionAsync(Dispatchers.IO) { PersonalizadoraController.findAllRigidity(true) }
                    "n" -> result = suspendedTransactionAsync(Dispatchers.IO) { PersonalizadoraController.findAllRigidity(false) }
                }
                waitingText(result)
                println(result.await())
            }
            else println("incorrect parameters")
        }
    }
}

suspend fun findBySomething() {
    var input = ""
    println(" - Select a field to search by: " +
            "[id/model/brand/serial number]")
    while (!input.contentEquals("id") &&
        !input.contentEquals("model") &&
        !input.contentEquals("brand") &&
        !input.contentEquals("serial number"))
        input = readln()
    lateinit var result: Deferred<String>
    when (input) {
        "id" -> result = suspendedTransactionAsync(Dispatchers.IO) { MaquinaController.getMaquinaById(UUID.fromString(input)) }
        "model" -> result = suspendedTransactionAsync(Dispatchers.IO) { MaquinaController.getMaquinaByModel(input) }
        "brand" -> result = suspendedTransactionAsync(Dispatchers.IO) { MaquinaController.getMaquinaByBrand(input) }
        "serial number" -> result = suspendedTransactionAsync(Dispatchers.IO) { MaquinaController.getMaquinaBySerialNumber(input) }
    }
    waitingText(result)
    println(result.await())
}

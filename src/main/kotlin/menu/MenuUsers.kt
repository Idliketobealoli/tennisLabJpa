package menu

import controllers.UserController
import dto.UserDTO
import kotlinx.coroutines.*
import models.enums.Profile
import util.betweenXandY
import util.waitingText
import java.util.*

suspend fun menuUsers() = coroutineScope {
    var back = false
    while (!back) {
        println(" - Please select one of the following actions.")
        println("""
            1. Find all users
            2. Find all users with a particular role
            3. Find user with a particular id
            4. Find user with a particular email
            5. Find user with a particular phone number
            6. Create user
            7. Update user
            8. Delete user
            9. Back
        """.trimIndent())
        var res = ""
        while (!betweenXandY(res, 1, 9)) {
            res = readln()
        }
        when (res.toInt()) {
            1 -> {
                val users = async(Dispatchers.IO) { UserController.findAllUsers() }
                waitingText(users)
                println(users.await())
            }
            2 -> findAllByRole()
            3 -> findId()
            4 -> findEmail()
            5 -> findPhone()
            6 -> create()
            7 -> update()
            8 -> delete()
            9 -> back = true
        }
    }
}

private suspend fun delete() = coroutineScope {
    println(" - Email of target user:")
    val email = readln()
    val bu = async(Dispatchers.IO) { UserController.getUserByEmailForLogin(email) }
    waitingText(bu)
    val baseUser = bu.await()
    if (baseUser == null) println("There are no users with email: $email")
    else {
        println("Deleting ${baseUser.nombre} ${baseUser.apellido} will be a permanent action. " +
                "Do you really want to proceed? [y/n]")
        var input = ""
        while (!input.contentEquals("y") && !input.contentEquals("n"))
            input = readln()
        if (input.contentEquals("y")) {
            val deleted = async(Dispatchers.IO) { UserController.deleteUser(baseUser) }
            println("Deleting user...")
            waitingText(deleted)
            println(deleted.await())
        }
    }
}

private suspend fun update() = coroutineScope {
    println(" - Current email of target user:")
    val email = readln()
    val bu = async(Dispatchers.IO) { UserController.getUserByEmailForLogin(email) }
    waitingText(bu)
    val baseUser = bu.await()
    if (baseUser == null) println("There are no users with email: $email")
    else {
        println(" - Input new data:")
        println(" - Name: ")
        val name = readln()
        println(" - Family name: ")
        val famName = readln()
        println(" - Phone number: ")
        val number = readln()
        println(" - Email: ")
        val mail = readln()
        println(" - Password: ")
        val pwd = readln()
        lateinit var profile: Profile
        var input = ""
        println(" - Profile: [admin/worker/client]")
        while (!input.contentEquals("admin") &&
            !input.contentEquals("worker") &&
            !input.contentEquals("client")
        )
            input = readln()
        when (input) {
            "admin" -> profile = Profile.ADMIN
            "worker" -> profile = Profile.WORKER
            "client" -> profile = Profile.CLIENT
        }
        val newUser = UserDTO(
            id = baseUser.id,
            nombre = name,
            apellido = famName,
            telefono = number,
            email = mail,
            password = pwd,
            perfil = profile
        )
        val updated = async(Dispatchers.IO) { UserController.insertUser(newUser) }
        waitingText(updated)
        println(updated.await())
    }
}

private suspend fun create() = coroutineScope {
    var goBack = false
    while (!goBack) {
        println(" - Name: ")
        val name = readln()
        println(" - Family name: ")
        val famName = readln()
        println(" - Phone number: ")
        val number = readln()
        val u2 = async(Dispatchers.IO) { UserController.getUserByPhoneForLogin(number) }
        println(" - Email: ")
        val mail = readln()
        val u1 = async(Dispatchers.IO) { UserController.getUserByEmailForLogin(mail) }
        println(" - Password: ")
        val pwd = readln()
        lateinit var profile: Profile
        var input = ""
        println(" - Profile: [admin/worker/client]")
        while (!input.contentEquals("admin") &&
            !input.contentEquals("worker") &&
            !input.contentEquals("client"))
            input = readln()
        when (input) {
            "admin" -> profile = Profile.ADMIN
            "worker" -> profile = Profile.WORKER
            "client" -> profile = Profile.CLIENT
        }
        val user1 = u1.await()
        val user2 = u2.await()
        if (user1 == null && user2 == null) {
            val newUser = UserDTO(
                id = UUID.randomUUID(),
                nombre = name,
                apellido = famName,
                telefono = number,
                email = mail,
                password = pwd,
                perfil = profile
            )
            val result = async(Dispatchers.IO) { UserController.insertUser(newUser) }
            waitingText(result)
            println(result.await())
            goBack = true
        }
        else {
            println(" - This email or phone number is already registered. Do you want to go back? [y/n]")
            var res = ""
            while (!res.contentEquals("y") && !res.contentEquals("n")) res = readln()
            if (res.contentEquals("y")) goBack = true
        }
    }
}

private suspend fun findPhone() = coroutineScope {
    println(" - Input phone:")
    val input = readln()
    val result = async(Dispatchers.IO) { UserController.getUserByPhone(input) }
    waitingText(result)
    println(result.await())
}

private suspend fun findEmail() = coroutineScope {
    println(" - Input email:")
    val input = readln()
    val result = async(Dispatchers.IO) { UserController.getUserByEmail(input) }
    waitingText(result)
    println(result.await())
}

private suspend fun findId() = coroutineScope {
    println(" - Input id:")
    var id: UUID? = null
    var correctFormat = false
    while (!correctFormat) {
        val input = readln()
        id = UUID.fromString(input) ?: null
        if (id != null) correctFormat = true
    }
    val result = async(Dispatchers.IO) { UserController.getUserById(id!!) }
    waitingText(result)
    println(result.await())
}

private suspend fun findAllByRole() = coroutineScope {
    var input = ""
    println(" - Select a role: [admin/worker/client]")
    while (!input.contentEquals("admin") &&
        !input.contentEquals("worker") &&
        !input.contentEquals("client"))
        input = readln()
    lateinit var result: Deferred<String>
    when (input) {
        "admin" -> result = async(Dispatchers.IO) { UserController.findAllUsersWithRole(Profile.ADMIN) }
        "worker" -> result = async(Dispatchers.IO) { UserController.findAllUsersWithRole(Profile.WORKER) }
        "client" -> result = async(Dispatchers.IO) { UserController.findAllUsersWithRole(Profile.CLIENT) }
    }
    waitingText(result)
    println(result.await())
}

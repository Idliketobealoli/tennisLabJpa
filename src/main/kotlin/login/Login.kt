package login

import controllers.UserController
import dto.UserDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import models.enums.Profile
import util.encode
import util.waitingText
import java.util.*
import kotlin.system.exitProcess

suspend fun login(): UserDTO {
    val res = coroutineScope {
        while (true) {
            println(" --- Login --- ")
            println(" - Email: ")
            val email = readln()
            val u = async { UserController.getUserByEmailForLogin(email) }
            println(" - Password: ")
            val pwd = readln()
            var res = ""
            val user = u.await()
            if(user == null) {
                println(" - Incorrect credentials. Do you want to exit? [y/n]")
                while (!res.contentEquals("y") && !res.contentEquals("n")) res = readln()
                if (res.contentEquals("y")) exitProcess(0)
            }
            else {
                val encryptedPwd = encode(pwd)
                if (!encryptedPwd.contentEquals(user.password)) {
                    println(" - Incorrect credentials. Do you want to exit? [y/n]")
                    while (!res.contentEquals("y") && !res.contentEquals("n")) res = readln()
                    if (res.contentEquals("y")) exitProcess(0)
                }
                else {
                    println(" - Logged in. Welcome ${user.nombre}.")
                    return@coroutineScope user
                }
            }
        }
    }
    return res as UserDTO
}

suspend fun register(): UserDTO {
    val res = coroutineScope {
        while (true) {
            println(" --- Register --- ")
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
            println(" - Repeat password: ")
            val rpwd = readln()
            val user1 = u1.await()
            val user2 = u2.await()
            if (pwd.contentEquals(rpwd) && user1 == null && user2 == null) {
                val newUser = UserDTO(
                    id = UUID.randomUUID(),
                    nombre = name,
                    apellido = famName,
                    telefono = number,
                    email = mail,
                    password = pwd,
                    perfil = Profile.CLIENT
                )
                val result = async(Dispatchers.IO) { UserController.insertUser(newUser) }
                waitingText(result)
                println("Registering $name $famName...")
                return@coroutineScope newUser
            }
            else {
                println(" - Incorrect parameters. Do you want to exit? [y/n]")
                var res = ""
                while (!res.contentEquals("y") && !res.contentEquals("n")) res = readln()
                if (res.contentEquals("y")) exitProcess(0)
            }
        }
    }
    return res as UserDTO
}
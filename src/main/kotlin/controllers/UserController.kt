package controllers

import dto.UserDTO
import models.enums.Profile
import services.UserService
import java.util.*

/**
 * @author Daniel Rodriguez Muñoz
 *
 * Controlador de User. Emplea el servicio para realizar todas las operaciones
 * que necesitemos de esta entidad, devolviendo los resultados como JSON.
 *
 * Adicionalmente, este controlador tiene un par de metodos que en vez de
 * devolver el JSON devuelven el objeto, y son unicamente usados para el
 * login/register.
 */
object UserController {
    var service = UserService()

    suspend fun findAllUsers(): String {
        return service.getAllUsers().toString()
    }

    suspend fun findAllUsersWithRole(role: Profile): String {
        return service.getAllUsersByPerfil(role).toString()
    }

    suspend fun getUserById(id: UUID): String {
        return service.getUserById(id)?.toJSON() ?: "User with id $id not found."
    }

    suspend fun getUserByEmail(email: String): String {
        return service.getUserByMail(email)?.toJSON() ?: "User with email $email not found."
    }

    suspend fun getUserByEmailForLogin(email: String): UserDTO? {
        return service.getUserByMail(email)
    }

    suspend fun getUserByPhone(phone: String): String {
        return service.getUserByPhone(phone)?.toJSON() ?: "User with phone $phone not found."
    }

    suspend fun getUserByPhoneForLogin(phone: String): UserDTO? {
        return service.getUserByPhone(phone)
    }

    suspend fun insertUser(dto: UserDTO): String {
        return service.createUser(dto).toJSON()
    }

    suspend fun deleteUser(dto: UserDTO): String {
        val result = service.deleteUser(dto)
        return if (result) dto.toJSON()
        else "Could not delete User with id ${dto.id}"
    }
}
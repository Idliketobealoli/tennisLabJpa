package services

import dto.UserDTO
import mappers.UserMapper
import models.User
import models.enums.Profile
import repositories.UserRepository
import java.util.UUID

/**
 * @author Daniel Rodriguez Muñoz
 *
 * Clase encargada de llamar a las operaciones del repositorio correspondientes y
 * pasar el resultado de las mismas a DTO usando para ello
 * el mapper y el repositorio de User.
 */
open class UserService: BaseService<User, UUID, UserRepository>(UserRepository()) {
    val mapper = UserMapper()

    suspend fun getAllUsers(): List<UserDTO> {
        return mapper.toDTO(this.findAll().toList())
    }

    suspend fun getAllUsersByPerfil(role: Profile): List<UserDTO> {
        return mapper.toDTO(repository.findByPerfil(role).toList())
    }

    suspend fun getUserById(id: UUID): UserDTO? {
        return this.findById(id)?.let { mapper.toDTO(it) }
    }

    suspend fun getUserByMail(mail: String): UserDTO? {
        return repository.findByEmail(mail)?.let { mapper.toDTO(it) }
    }

    suspend fun getUserByPhone(phone: String): UserDTO? {
        return repository.findByPhone(phone)?.let { mapper.toDTO(it) }
    }

    suspend fun createUser(user: UserDTO): UserDTO {
        return mapper.toDTO(this.insert(mapper.fromDTO(user)))
    }

    suspend fun deleteUser(user: UserDTO): Boolean {
        return this.delete(mapper.fromDTO(user))
    }
}
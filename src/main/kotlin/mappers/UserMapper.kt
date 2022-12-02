package mappers

import dto.UserDTO
import entities.UserDao
import models.User
import models.enums.Profile
import util.encode

/**
 * @author Iván Azagra Troya
 * Este Kotlin.file crea la función que recoge las entidades DAO
 * para devolver la entidades de la clase User
 */

fun UserDao.fromUserDaoToUser(): User {
    return User(
        id = id.value,
        nombre = nombre,
        apellido =apellido,
        telefono = telefono,
        email = email,
        password = password,
        perfil = perfil
    )
}

class UserMapper: BaseMapper<User, UserDTO>() {
    override fun fromDTO(item: UserDTO): User {
        return User(
            id = item.id,
            nombre = item.nombre,
            apellido = item.apellido,
            telefono = item.telefono,
            email = item.email,
            password = encode(item.password),
            perfil = item.perfil.name
        )
    }

    override fun toDTO(item: User): UserDTO {
        return UserDTO(
            id = item.id,
            nombre = item.nombre,
            apellido = item.apellido,
            telefono = item.telefono,
            email = item.email,
            password = item.password,
            perfil = Profile.parseProfile(item.perfil)
        )
    }
}
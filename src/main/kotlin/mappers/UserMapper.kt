package mappers

import dto.UserDTO
import models.User
import models.enums.Profile
import util.encode

/**
 * @author Iván Azagra Troya
 * Este Kotlin.file crea la función que recoge las entidades DAO
 * para devolver la entidades de la clase User
 */
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
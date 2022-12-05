package mappers

import dto.UserDTO
import models.User
import models.enums.Profile
import util.encode

/**
 * @author Daniel Rodriguez Muñoz
 *
 * Clase que hereda de BaseMapper y se encarga de pasar de
 * DTO a Modelo y de Modelo a DTO.
 * Tambien codifica la contraseña del usuario al pasarlo de
 * DTO a Modelo.
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
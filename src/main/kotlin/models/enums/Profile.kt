package models.enums

/**
 * @author Daniel Rodriguez Muñoz
 * Enumerador de los tipos de perfil que puede tener un usuario
 */
enum class Profile(value: String) {
    ADMIN("ADMIN"),
    WORKER("WORKER"),
    CLIENT("CLIENT");

    /**
     * Este parseador hace falta para pasarle el valor del tipo
     * de perfil a la base de datos, ya que lo reflejará como un varchar
     * @throws IllegalArgumentException
     */
    companion object {
        fun parseProfile(value: String): Profile{
            return when (value) {
                "ADMIN" -> ADMIN
                "WORKER" -> WORKER
                "CLIENT" -> CLIENT
                else -> throw IllegalArgumentException("Unknown profile: $value")
            }
        }
    }
}
package models.enums

/**
 * @author Iván Azagra Troya
 * Enumerador de los tipos de maquinas
 * @throws IllegalArgumentException cuando no se trata de un tipo de maquina reconocida
 */
enum class TipoMaquina(value: String) {
    PERSONALIZADORA("PERSONALIZADORA"),
    ENCORDADORA("ENCORDADORA");

    companion object {
        fun parseTipoMaquina(value: String): TipoMaquina {
            return when (value) {
                "PERSONALIZADORA" -> PERSONALIZADORA
                "ENCORDADORA" -> ENCORDADORA
                else -> throw IllegalArgumentException("Unknown machine type: $value")
            }
        }
    }
}
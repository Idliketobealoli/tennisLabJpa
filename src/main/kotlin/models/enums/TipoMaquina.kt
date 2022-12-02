package models.enums

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
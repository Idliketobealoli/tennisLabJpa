package models.enums

enum class TipoTarea(value: String) {
    PERSONALIZACION("PERSONALIZACION"),
    ENCORDADO("ENCORDADO"),
    ADQUISICION("ADQUISICION");

    companion object {
        fun parseTipoTarea(value: String): TipoTarea {
            return when (value) {
                "ADQUISICION" -> ADQUISICION
                "ENCORDADO" -> ENCORDADO
                "PERSONALIZACION" -> PERSONALIZACION
                else -> throw IllegalArgumentException("Unknown task type: $value")
            }
        }
    }
}
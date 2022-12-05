package models.enums

/**
 * @author Daniel Rodriguez Muñoz
 * Enumerador de los tipos de tareas
 * @throws IllegalArgumentException cuando no se trata de un tipo de tarea reconocida
 */
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
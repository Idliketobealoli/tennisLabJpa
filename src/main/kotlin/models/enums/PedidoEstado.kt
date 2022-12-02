package models.enums

/**
 * @author Iván Azagra Troya
 * Enumerador de los tipos de estados en los que se puede encontrar el pedido
 * @throws IllegalArgumentException cuando no se trata de un tipo de estado reconocido
 */
enum class PedidoEstado(value: String){
    RECIBIDO("RECIBIDO"),
    PROCESO("PROCESO"),
    TERMINADO("TERMINADO");

    companion object {
        fun parseTipoEstado(value: String): PedidoEstado {
            return when (value) {
                "RECIBIDO" -> RECIBIDO
                "PROCESO" -> PROCESO
                "TERMINADO" -> TERMINADO
                else -> throw IllegalArgumentException("Unknown status: $value")
            }
        }
    }
}
package dto

/**
 * @author Daniel Rodriguez Muñoz
 *
 * Interfaz que implementarán las maquinasDTO para que
 * se simule una falsa herencia en los DTO.
 * El motivo por el cual existe es porque algunos controladores
 * nececitan devolver listas de Maquina o Maquina, sin importar si
 * son un tipo especifico de maquina.
 */
interface MaquinaDTO {
    fun toJSON(): String
}
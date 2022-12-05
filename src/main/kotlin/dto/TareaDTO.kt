package dto

/**
 * @author Ivan Azagra Troya
 *
 * Interfaz que implementarán las tareasDTO para que
 * se simule una falsa herencia en los DTO.
 * El motivo por el cual existe es porque algunos controladores
 * nececitan devolver listas de Tareas o Tareas, sin importar si
 * son un tipo especifico de tarea.
 */
interface TareaDTO {
    fun toJSON(): String
}
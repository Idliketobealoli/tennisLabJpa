package mappers

/**
 * @author Daniel Rodriguez Muñoz
 *
 * Clase abstracta que obliga a los mappers a
 * implementar un fromDTO y un toDTO, pero ya
 * implementa de base esas funciones para listas de
 * objetos, ahorrandoselas a los mappers.
 */
abstract class BaseMapper<T,DTO> {
    fun fromDTO(items: List<DTO>): List<T> {
        return items.map { fromDTO(it) }
    }
    fun toDTO(items: List<T>): List<DTO> {
        return items.map { toDTO(it) }
    }

    abstract fun fromDTO(item: DTO) : T
    abstract fun toDTO(item: T) : DTO
}
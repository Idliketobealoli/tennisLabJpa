package services

import dto.EncordadoDTO
import dto.TareaDTO
import mappers.TareaMapper
import models.Encordado
import repositories.EncordadoRepository
import java.util.UUID

/**
 * @author Daniel Rodriguez Muñoz
 *
 * Clase encargada de llamar a las operaciones del repositorio correspondientes y
 * pasar el resultado de las mismas a DTO usando para ello
 * el mapper y el repositorio de Encordado.
 */
open class EncordadoService: BaseService<Encordado, UUID, EncordadoRepository>(
    EncordadoRepository()) {
    val mapper = TareaMapper()

    suspend fun getAllEncordados(): List<EncordadoDTO> {
        return mapper.toEncordadoDTO(this.findAll().toList())
    }

    suspend fun getEncordadoById(id: UUID): EncordadoDTO? {
        return this.findById(id)?.let { mapper.toEncordadoDTO(it) }
    }

    suspend fun createEncordado(encordado: EncordadoDTO): TareaDTO {
        return mapper.toDTO(this.insert(mapper.fromEncordadoDTO(encordado)))
    }

    suspend fun deleteEncordado(encordado: EncordadoDTO): Boolean {
        return this.delete(mapper.fromEncordadoDTO(encordado))
    }
}
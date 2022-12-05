package services

import dto.PersonalizacionDTO
import dto.TareaDTO
import mappers.TareaMapper
import models.Personalizacion
import repositories.PersonalizacionRepository
import java.util.UUID

/**
 * @author Daniel Rodriguez Muñoz
 *
 * Clase encargada de llamar a las operaciones del repositorio correspondientes y
 * pasar el resultado de las mismas a DTO usando para ello
 * el mapper y el repositorio de Personalizacion.
 */
open class PersonalizacionService: BaseService<Personalizacion, UUID, PersonalizacionRepository>(
    PersonalizacionRepository()) {
    val mapper = TareaMapper()

    suspend fun getAllPersonalizaciones(): List<PersonalizacionDTO> {
        return mapper.toPersonalizacionDTO(this.findAll().toList())
    }

    suspend fun getPersonalizacionById(id: UUID): PersonalizacionDTO? {
        return this.findById(id)?.let { mapper.toPersonalizacionDTO(it) }
    }

    suspend fun createPersonalizacion(personalizacion: PersonalizacionDTO): TareaDTO {
        return mapper.toDTO(this.insert(mapper.fromPersonalizacionDTO(personalizacion)))
    }

    suspend fun deletePersonalizacion(personalizacion: PersonalizacionDTO): Boolean {
        return this.delete(mapper.fromPersonalizacionDTO(personalizacion))
    }
}
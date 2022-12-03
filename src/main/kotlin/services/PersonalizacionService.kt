package services

import dto.PersonalizacionDTO
import dto.TareaDTO
import mappers.TareaMapper
import models.Personalizacion
import models.Tarea
import models.enums.TipoTarea
import repositories.PersonalizacionRepository
import repositories.TareaRepository
import java.util.UUID

class PersonalizacionService: BaseService<Personalizacion, UUID, PersonalizacionRepository>(
    PersonalizacionRepository()) {
    val tareaRepo = TareaRepository()
    val mapper = TareaMapper()

    suspend fun getAllPersonalizaciones(): List<PersonalizacionDTO> {
        return mapper.toPersonalizacionDTO(this.findAll().toList())
    }

    suspend fun getPersonalizacionById(id: UUID): PersonalizacionDTO? {
        return this.findById(id)?.let { mapper.toPersonalizacionDTO(it) }
    }

    suspend fun createPersonalizacion(personalizacion: PersonalizacionDTO): TareaDTO {
        val tarea = Tarea(
            id = personalizacion.id,
            raqueta = personalizacion.raqueta,
            precio = personalizacion.precio,
            user = personalizacion.user,
            pedido = personalizacion.pedido,
            tipoTarea = TipoTarea.PERSONALIZACION
        )
        //tareaRepo.create(tarea)
        return mapper.toDTO(this.insert(mapper.fromPersonalizacionDTO(personalizacion)))
    }

    suspend fun deletePersonalizacion(personalizacion: PersonalizacionDTO): Boolean {
        return this.delete(mapper.fromPersonalizacionDTO(personalizacion))
    }
}
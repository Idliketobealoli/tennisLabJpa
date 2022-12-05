package services

import dto.AdquisicionDTO
import dto.TareaDTO
import mappers.TareaMapper
import models.Adquisicion
import repositories.AdquisicionRepository
import java.util.UUID

/**
 * @author Ivan Azagra Troya
 *
 * Clase encargada de llamar a las operaciones del repositorio correspondientes y
 * pasar el resultado de las mismas a DTO usando para ello
 * el mapper y el repositorio de Adquisicion.
 */
open class AdquisicionService: BaseService<Adquisicion, UUID, AdquisicionRepository>(
    AdquisicionRepository()) {
    val mapper = TareaMapper()

    suspend fun getAllAdquisiciones(): List<AdquisicionDTO> {
        return mapper.toAdquisicionDTO(this.findAll().toList())
    }

    suspend fun getAdquisicionById(id: UUID): AdquisicionDTO? {
        return this.findById(id)?.let { mapper.toAdquisicionDTO(it) }
    }

    suspend fun createAdquisicion(adquisicion: AdquisicionDTO): TareaDTO {
        return mapper.toDTO(this.insert(mapper.fromAdquisicionDTO(adquisicion)))
    }

    suspend fun deleteAdquisicion(adquisicion: AdquisicionDTO): Boolean {
        return this.delete(mapper.fromAdquisicionDTO(adquisicion))
    }
}
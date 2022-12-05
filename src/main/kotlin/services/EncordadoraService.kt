package services

import dto.EncordadoraDTO
import dto.MaquinaDTO
import mappers.MaquinaMapper
import models.Encordadora
import repositories.EncordadoraRepository
import java.util.*

/**
 * @author Ivan Azagra Troya
 *
 * Clase encargada de llamar a las operaciones del repositorio correspondientes y
 * pasar el resultado de las mismas a DTO usando para ello
 * el mapper y el repositorio de Encordadora.
 */
open class EncordadoraService: BaseService<Encordadora, UUID, EncordadoraRepository>(
    EncordadoraRepository()) {
    val mapper = MaquinaMapper()

    suspend fun getAllEncordadoras(): List<EncordadoraDTO> {
        return mapper.toEncordadoraDTO(this.findAll().toList())
    }

    suspend fun getEncordadoraById(id: UUID): EncordadoraDTO? {
        return this.findById(id)?.let { mapper.toEncordadoraDTO(it) }
    }

    suspend fun createEncordadora(encordadora: EncordadoraDTO): MaquinaDTO {
        return mapper.toDTO(this.insert(mapper.fromEncordadoraDTO(encordadora)))
    }

    suspend fun deleteEncordadora(encordadora: EncordadoraDTO): Boolean {
        return this.delete(mapper.fromEncordadoraDTO(encordadora))
    }
}
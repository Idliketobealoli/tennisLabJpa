package services

import dto.EncordadoraDTO
import dto.MaquinaDTO
import mappers.MaquinaMapper
import models.Encordadora
import repositories.EncordadoraRepository
import java.util.*

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
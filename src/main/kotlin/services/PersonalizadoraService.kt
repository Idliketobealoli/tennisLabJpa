package services

import dto.MaquinaDTO
import dto.PersonalizadoraDTO
import mappers.MaquinaMapper
import models.Personalizadora
import repositories.PersonalizadoraRepository
import java.util.UUID

open class PersonalizadoraService: BaseService<Personalizadora, UUID, PersonalizadoraRepository>(
    PersonalizadoraRepository()) {
    val mapper = MaquinaMapper()

    suspend fun getAllPersonalizadoras(): List<PersonalizadoraDTO> {
        return mapper.toPersonalizadoraDTO(this.findAll().toList())
    }

    suspend fun getPersonalizadoraById(id: UUID): PersonalizadoraDTO? {
        return this.findById(id)?.let { mapper.toPersonalizadoraDTO(it) }
    }

    suspend fun createPersonalizadora(personalizadora: PersonalizadoraDTO): MaquinaDTO {
        return mapper.toDTO(this.insert(mapper.fromPersonalizadoraDTO(personalizadora)))
    }

    suspend fun deletePersonalizadora(personalizadora: PersonalizadoraDTO): Boolean {
        return this.delete(mapper.fromPersonalizadoraDTO(personalizadora))
    }
}
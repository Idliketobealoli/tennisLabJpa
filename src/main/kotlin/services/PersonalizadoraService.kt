package services

import dto.MaquinaDTO
import dto.PersonalizadoraDTO
import mappers.MaquinaMapper
import models.Maquina
import models.Personalizadora
import models.enums.TipoMaquina
import java.util.UUID

class PersonalizadoraService: BaseService<Personalizadora, UUID, PersonalizadoraRepository>(
    PersonalizadoraRepository()) {
    val maquinaRepo = MaquinaRepository()
    val mapper = MaquinaMapper()

    suspend fun getAllPersonalizadoras(): List<PersonalizadoraDTO> {
        return mapper.toPersonalizadoraDTO(this.findAll().toList())
    }

    suspend fun getPersonalizadoraById(id: UUID): PersonalizadoraDTO? {
        return this.findById(id)?.let { mapper.toPersonalizadoraDTO(it) }
    }

    suspend fun createPersonalizadora(personalizadora: PersonalizadoraDTO): MaquinaDTO {
        val maquina = Maquina(
            id = personalizadora.id,
            modelo = personalizadora.modelo,
            marca = personalizadora.marca,
            fechaAdquisicion = personalizadora.fechaAdquisicion,
            numeroSerie = personalizadora.numeroSerie,
            tipoMaquina = TipoMaquina.PERSONALIZADORA
        )
        maquinaRepo.create(maquina)
        return mapper.toDTO(this.insert(mapper.fromPersonalizadoraDTO(personalizadora)))
    }

    suspend fun deletePersonalizadora(personalizadora: PersonalizadoraDTO): Boolean {
        return this.delete(mapper.fromPersonalizadoraDTO(personalizadora))
    }
}
package services

import dto.EncordadoraDTO
import dto.MaquinaDTO
import mappers.MaquinaMapper
import models.Encordadora
import models.Maquina
import models.enums.TipoMaquina
import repositories.EncordadoraRepository
import repositories.MaquinaRepository
import java.util.*

class EncordadoraService: BaseService<Encordadora, UUID, EncordadoraRepository>(
    EncordadoraRepository()) {
    val maquinaRepo = MaquinaRepository()
    val mapper = MaquinaMapper()

    suspend fun getAllEncordadoras(): List<EncordadoraDTO> {
        return mapper.toEncordadoraDTO(this.findAll().toList())
    }

    suspend fun getEncordadoraById(id: UUID): EncordadoraDTO? {
        return this.findById(id)?.let { mapper.toEncordadoraDTO(it) }
    }

    suspend fun createEncordadora(encordadora: EncordadoraDTO): MaquinaDTO {
        val maquina = Maquina(
            id = encordadora.id,
            modelo = encordadora.modelo,
            marca = encordadora.marca,
            fechaAdquisicion = encordadora.fechaAdquisicion,
            numeroSerie = encordadora.numeroSerie,
            tipoMaquina = TipoMaquina.ENCORDADORA
        )
        //maquinaRepo.create(maquina)
        return mapper.toDTO(this.insert(mapper.fromEncordadoraDTO(encordadora)))
    }

    suspend fun deleteEncordadora(encordadora: EncordadoraDTO): Boolean {
        return this.delete(mapper.fromEncordadoraDTO(encordadora))
    }
}
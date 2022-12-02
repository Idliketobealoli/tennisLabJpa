package services

import dto.EncordadoDTO
import dto.TareaDTO
import mappers.TareaMapper
import models.Encordado
import models.Tarea
import models.enums.TipoTarea
import java.util.UUID

class EncordadoService: BaseService<Encordado, UUID, EncordadoRepository>(
    EncordadoRepository()) {
    val tareaRepo = TareaRepository()
    val mapper = TareaMapper()

    suspend fun getAllEncordados(): List<EncordadoDTO> {
        return mapper.toEncordadoDTO(this.findAll().toList())
    }

    suspend fun getEncordadoById(id: UUID): EncordadoDTO? {
        return this.findById(id)?.let { mapper.toEncordadoDTO(it) }
    }

    suspend fun createEncordado(encordado: EncordadoDTO): TareaDTO {
        val tarea = Tarea(
            id = encordado.id,
            raqueta = encordado.raqueta,
            precio = encordado.precio,
            user = encordado.user,
            tipoTarea = TipoTarea.ENCORDADO
        )
        tareaRepo.create(tarea)
        return mapper.toDTO(this.insert(mapper.fromEncordadoDTO(encordado)))
    }

    suspend fun deleteEncordado(encordado: EncordadoDTO): Boolean {
        return this.delete(mapper.fromEncordadoDTO(encordado))
    }
}
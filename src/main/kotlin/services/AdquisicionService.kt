package services

import dto.AdquisicionDTO
import dto.TareaDTO
import mappers.TareaMapper
import models.Adquisicion
import models.Tarea
import models.enums.TipoTarea
import repositories.AdquisicionRepository
import repositories.TareaRepository
import java.util.UUID

class AdquisicionService: BaseService<Adquisicion, UUID, AdquisicionRepository>(
    AdquisicionRepository()) {
    val tareaRepo = TareaRepository()
    val mapper = TareaMapper()

    suspend fun getAllAdquisiciones(): List<AdquisicionDTO> {
        return mapper.toAdquisicionDTO(this.findAll().toList())
    }

    suspend fun getAdquisicionById(id: UUID): AdquisicionDTO? {
        return this.findById(id)?.let { mapper.toAdquisicionDTO(it) }
    }

    suspend fun createAdquisicion(adquisicion: AdquisicionDTO): TareaDTO {
        val tarea = Tarea(
            id = adquisicion.id,
            raqueta = adquisicion.raqueta,
            precio = adquisicion.precio,
            user = adquisicion.user,
            pedido = adquisicion.pedido,
            tipoTarea = TipoTarea.ADQUISICION
        )
        //tareaRepo.create(tarea)
        return mapper.toDTO(this.insert(mapper.fromAdquisicionDTO(adquisicion)))
    }

    suspend fun deleteAdquisicion(adquisicion: AdquisicionDTO): Boolean {
        return this.delete(mapper.fromAdquisicionDTO(adquisicion))
    }
}
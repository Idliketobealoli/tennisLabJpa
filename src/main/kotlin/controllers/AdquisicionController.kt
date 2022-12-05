package controllers

import dto.AdquisicionDTO
import services.AdquisicionService
import java.util.*

/**
 * @author Daniel Rodriguez Muñoz
 *
 * Controlador de Adquisicion. Emplea el servicio para realizar todas las operaciones
 * que necesitemos de esta entidad, devolviendo los resultados como JSON.
 */
object AdquisicionController {
    var service = AdquisicionService()

    suspend fun findAllAdquisiciones(): String {
        return service.getAllAdquisiciones().toString()
    }

    suspend fun getAdquisicionById(id: UUID): String {
        return service.getAdquisicionById(id)?.toJSON() ?: "Adquisicion with id $id not found."
    }

    suspend fun insertAdquisicion(dto: AdquisicionDTO): String {
        return service.createAdquisicion(dto).toJSON()
    }

    suspend fun deleteAdquisicion(dto: AdquisicionDTO): String {
        val result = service.deleteAdquisicion(dto)
        return if (result) dto.toJSON()
        else "Could not delete Adquisicion with id ${dto.id}"
    }
}
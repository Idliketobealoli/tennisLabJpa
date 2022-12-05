package controllers

import dto.EncordadoraDTO
import services.EncordadoraService
import java.util.*

/**
 * @author Daniel Rodriguez Muñoz
 *
 * Controlador de Encordadora. Emplea el servicio para realizar todas las operaciones
 * que necesitemos de esta entidad, devolviendo los resultados como JSON.
 */
object EncordadoraController {
    var service = EncordadoraService()

    suspend fun findAllEncordadoras(): String {
        return service.getAllEncordadoras().toString()
    }

    suspend fun findAllManuales(bool: Boolean): String {
        return service.getAllEncordadoras().filter { it.isManual == bool }.toString()
    }

    suspend fun getEncordadoraById(id: UUID): String {
        return service.getEncordadoraById(id)?.toJSON() ?: "Encordadora with id $id not found."
    }

    suspend fun insertEncordadora(dto: EncordadoraDTO): String {
        return service.createEncordadora(dto).toJSON()
    }

    suspend fun deleteEncordadora(dto: EncordadoraDTO): String {
        val result = service.deleteEncordadora(dto)
        return if (result) dto.toJSON()
        else "Could not delete Encordadora with id ${dto.id}"
    }
}
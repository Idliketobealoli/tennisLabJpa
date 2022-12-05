package controllers

import dto.PersonalizadoraDTO
import services.PersonalizadoraService
import java.util.*

/**
 * @author Daniel Rodriguez Muñoz
 *
 * Controlador de Personalizadora. Emplea el servicio para realizar todas las operaciones
 * que necesitemos de esta entidad, devolviendo los resultados como JSON.
 */
object PersonalizadoraController {
    var service = PersonalizadoraService()

    suspend fun findAllPersonalizadoras(): String {
        return service.getAllPersonalizadoras().toString()
    }

    suspend fun findAllManeuverability(bool: Boolean): String {
        return service.getAllPersonalizadoras().filter { it.measuresManeuverability == bool }.toString()
    }

    suspend fun findAllRigidity(bool: Boolean): String {
        return service.getAllPersonalizadoras().filter { it.measuresRigidity == bool }.toString()
    }

    suspend fun findAllBalance(bool: Boolean): String {
        return service.getAllPersonalizadoras().filter { it.measuresBalance == bool }.toString()
    }

    suspend fun getPersonalizadoraById(id: UUID): String {
        return service.getPersonalizadoraById(id)?.toJSON() ?: "Personalizadora with id $id not found."
    }

    suspend fun insertPersonalizadora(dto: PersonalizadoraDTO): String {
        return service.createPersonalizadora(dto).toJSON()
    }

    suspend fun deletePersonalizadora(dto: PersonalizadoraDTO): String {
        val result = service.deletePersonalizadora(dto)
        return if (result) dto.toJSON()
        else "Could not delete Personalizadora with id ${dto.id}"
    }
}
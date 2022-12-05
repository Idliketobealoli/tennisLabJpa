package controllers

import dto.TurnoDTO
import services.TurnoService
import java.util.*

/**
 * @author Ivan Azagra Troya
 *
 * Controlador de Turno. Emplea el servicio para realizar todas las operaciones
 * que necesitemos de esta entidad, devolviendo los resultados como JSON.
 */
object TurnoController {
    var service = TurnoService()

    suspend fun findAllTurnos(): String {
        return service.getAllTurnos().toString()
    }

    suspend fun findAllTurnosSortedByFecha(): String {
        return service.getAllTurnos().sortedBy { it.horaInicio }.toString()
    }

    suspend fun getTurnoById(id: UUID): String {
        return service.getTurnoById(id)?.toJSON() ?: "Turno with id $id not found."
    }

    suspend fun insertTurno(dto: TurnoDTO): String {
        return service.createTurno(dto).toJSON()
    }

    suspend fun deleteTurno(dto: TurnoDTO): String {
        val result = service.deleteTurno(dto)
        return if (result) dto.toJSON()
        else "Could not delete Turno with id ${dto.id}"
    }
}
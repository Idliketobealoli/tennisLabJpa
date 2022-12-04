package services

import dto.TurnoDTO
import mappers.TurnoMapper
import models.Turno
import repositories.TurnoRepository
import java.util.UUID

open class TurnoService: BaseService<Turno, UUID, TurnoRepository>(
    TurnoRepository()) {
    val mapper = TurnoMapper()

    suspend fun getAllTurnos(): List<TurnoDTO> {
        return mapper.toDTO(this.findAll().toList())
    }

    suspend fun getTurnoById(id: UUID): TurnoDTO? {
        return this.findById(id)?.let { mapper.toDTO(it) }
    }

    suspend fun createTurno(turno: TurnoDTO): TurnoDTO {
        return mapper.toDTO(this.insert(mapper.fromDTO(turno)))
    }

    suspend fun deleteTurno(turno: TurnoDTO): Boolean {
        return this.delete(mapper.fromDTO(turno))
    }
}
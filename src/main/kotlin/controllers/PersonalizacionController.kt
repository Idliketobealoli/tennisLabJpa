package controllers

import dto.PersonalizacionDTO
import services.PersonalizacionService
import java.util.*

object PersonalizacionController {
    var service = PersonalizacionService()

    suspend fun findAllPersonalizaciones(): String {
        return service.getAllPersonalizaciones().toString()
    }

    suspend fun getPersonalizacionById(id: UUID): String {
        return service.getPersonalizacionById(id)?.toJSON() ?: "Personalizacion with id $id not found."
    }

    suspend fun insertPersonalizacion(dto: PersonalizacionDTO): String {
        return service.createPersonalizacion(dto).toJSON()
    }

    suspend fun deletePersonalizacion(dto: PersonalizacionDTO): String {
        val result = service.deletePersonalizacion(dto)
        return if (result) dto.toJSON()
        else "Could not delete Personalizacion with id ${dto.id}"
    }
}
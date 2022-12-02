package controllers

import dto.AdquisicionDTO
import dto.EncordadoDTO
import dto.PersonalizacionDTO
import dto.TareaDTO
import services.AdquisicionService
import services.EncordadoService
import services.PersonalizacionService
import java.util.UUID

object TareaController {
    var aService = AdquisicionService()
    var eService = EncordadoService()
    var pService = PersonalizacionService()

    suspend fun findAllTareas(): String {
        val adquisiciones = aService.getAllAdquisiciones()
        val encordados = eService.getAllEncordados()
        val personalizaciones = pService.getAllPersonalizaciones()
        val tareas: MutableList<TareaDTO> = mutableListOf()
        adquisiciones.forEach { tareas.add(it) }
        encordados.forEach { tareas.add(it) }
        personalizaciones.forEach { tareas.add(it) }
        return tareas.toList().toString()
    }

    suspend fun getTareaById(id: UUID): String {
        var busqueda: TareaDTO? = aService.getAdquisicionById(id)
        return if (busqueda != null) {
            AdquisicionController.getAdquisicionById(id)
        }
        else {
            busqueda = eService.getEncordadoById(id)
            if (busqueda != null) {
                EncordadoController.getEncordadoById(id)
            }
            else {
                busqueda = pService.getPersonalizacionById(id)
                if (busqueda != null) {
                    PersonalizacionController.getPersonalizacionById(id)
                }
                else "Tarea with id $id not found."
            }
        }
    }

    suspend fun insertTarea(dto: TareaDTO): String {
        return when (dto) {
            is AdquisicionDTO -> AdquisicionController.insertAdquisicion(dto)
            is EncordadoDTO -> EncordadoController.insertEncordado(dto)
            is PersonalizacionDTO -> PersonalizacionController.insertPersonalizacion(dto)
            else -> "Error at TareaController.insertTarea: DTO not supported."
        }
    }

    suspend fun deleteTarea(dto: TareaDTO): String {
        return when (dto) {
            is AdquisicionDTO -> AdquisicionController.deleteAdquisicion(dto)
            is EncordadoDTO -> EncordadoController.deleteEncordado(dto)
            is PersonalizacionDTO -> PersonalizacionController.deletePersonalizacion(dto)
            else -> "Error at TareaController.deleteTarea: DTO not supported."
        }
    }
}
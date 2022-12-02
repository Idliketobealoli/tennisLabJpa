package controllers

import dto.EncordadoraDTO
import dto.MaquinaDTO
import dto.PersonalizadoraDTO
import services.EncordadoraService
import services.PersonalizadoraService
import java.time.LocalDate
import java.util.*

object MaquinaController {
    var pService = PersonalizadoraService()
    var eService = EncordadoraService()

    suspend fun findAllMaquinas(): String {
        val personalizadoras = pService.getAllPersonalizadoras()
        val encordadoras = eService.getAllEncordadoras()
        val maquinas: MutableList<MaquinaDTO> = mutableListOf()
        personalizadoras.forEach { maquinas.add(it) }
        encordadoras.forEach { maquinas.add(it) }
        return maquinas.toList().toString()
    }

    suspend fun getMaquinaById(id: UUID): String {
        var busqueda: MaquinaDTO? = pService.getPersonalizadoraById(id)
        return if (busqueda != null) {
            PersonalizadoraController.getPersonalizadoraById(id)
        } else {
            busqueda = eService.getEncordadoraById(id)
            if (busqueda != null) {
                EncordadoraController.getEncordadoraById(id)
            } else "Maquina with id $id not found."
        }
    }

    suspend fun getMaquinaBySerialNumber(sNum: String): String {
        val personalizadoras = pService.getAllPersonalizadoras().filter { it.numeroSerie.contentEquals(sNum) }
        val encordadoras = eService.getAllEncordadoras().filter { it.numeroSerie.contentEquals(sNum) }
        val maquinas: MutableList<MaquinaDTO> = mutableListOf()
        personalizadoras.forEach { maquinas.add(it) }
        encordadoras.forEach { maquinas.add(it) }
        return maquinas.toList().firstOrNull()?.toString() ?: "Maquina with serial number $sNum not found."
    }

    suspend fun getMaquinaBySerialNumberForCreation(sNum: String): MaquinaDTO? {
        val encordadoras = eService.getAllEncordadoras().filter { it.numeroSerie == sNum }
        val personalizadoras = pService.getAllPersonalizadoras().filter { it.numeroSerie == sNum }
        val maquinas: MutableList<MaquinaDTO> = mutableListOf()
        personalizadoras.forEach { maquinas.add(it) }
        encordadoras.forEach { maquinas.add(it) }
        return maquinas.toList().firstOrNull()
    }

    suspend fun getMaquinaByModel(model: String): String {
        val personalizadoras = pService.getAllPersonalizadoras().filter { it.modelo.contentEquals(model) }
        val encordadoras = eService.getAllEncordadoras().filter { it.modelo.contentEquals(model) }
        val maquinas: MutableList<MaquinaDTO> = mutableListOf()
        personalizadoras.forEach { maquinas.add(it) }
        encordadoras.forEach { maquinas.add(it) }
        return maquinas.toList().toString()
    }

    suspend fun getMaquinaByBrand(brand: String): String {
        val personalizadoras = pService.getAllPersonalizadoras().filter { it.marca.contentEquals(brand) }
        val encordadoras = eService.getAllEncordadoras().filter { it.marca.contentEquals(brand) }
        val maquinas: MutableList<MaquinaDTO> = mutableListOf()
        personalizadoras.forEach { maquinas.add(it) }
        encordadoras.forEach { maquinas.add(it) }
        return maquinas.toList().toString()
    }

    suspend fun findAllMaquinasByAcquisitionDate(date: LocalDate, operador: String): String {
        lateinit var personalizadoras: List<PersonalizadoraDTO>
        lateinit var encordadoras: List<EncordadoraDTO>
        when (operador) {
            ">" -> {
                personalizadoras = pService.getAllPersonalizadoras().filter { it.fechaAdquisicion > date }
                encordadoras = eService.getAllEncordadoras().filter { it.fechaAdquisicion > date }
            }
            "<" -> {
                personalizadoras = pService.getAllPersonalizadoras().filter { it.fechaAdquisicion < date }
                encordadoras = eService.getAllEncordadoras().filter { it.fechaAdquisicion < date }
            }
            else -> {
                personalizadoras = listOf()
                encordadoras = listOf()
            }
        }
        val maquinas: MutableList<MaquinaDTO> = mutableListOf()
        personalizadoras.forEach { maquinas.add(it) }
        encordadoras.forEach { maquinas.add(it) }
        return maquinas.toList().toString()
    }

    suspend fun insertMaquina(dto: MaquinaDTO): String {
        return when (dto) {
            is PersonalizadoraDTO -> PersonalizadoraController.insertPersonalizadora(dto)
            is EncordadoraDTO -> EncordadoraController.insertEncordadora(dto)
            else -> "Error at MaquinaController.insertMaquina: DTO not supported."
        }
    }

    suspend fun deleteMaquina(dto: MaquinaDTO): String {
        return when (dto) {
            is PersonalizadoraDTO -> PersonalizadoraController.deletePersonalizadora(dto)
            is EncordadoraDTO -> EncordadoraController.deleteEncordadora(dto)
            else -> "Error at MaquinaController.deleteMaquina: DTO not supported."
        }
    }
}
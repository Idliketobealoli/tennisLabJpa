package mappers

import dto.EncordadoraDTO
import dto.MaquinaDTO
import dto.PersonalizadoraDTO
import entities.EncordadoraDao
import entities.MaquinaDao
import entities.PersonalizadoraDao
import exceptions.MapperException
import models.*
import models.enums.TipoMaquina
import org.jetbrains.exposed.dao.UUIDEntityClass
import repositories.encordadora.EncordadoraRepositoryImpl
import repositories.maquina.MaquinaRepositoryImpl
import repositories.personalizadora.PersonalizadoraRepositoryImpl
import java.time.LocalDate

/**
 * @author Iván Azagra Troya
 * Este Kotlin.file crea las funciones que recogen las entidades DAO
 * de las diferentes máquinas para devolver la clase Maquina POKO
 */

// No se puede hacer un fromMaquinasDaoToMaquinas porque la clase
// Maquina es abstracta, por lo que no puede instanciar un objeto de la misma

suspend fun MaquinaDao.fromMaquinaDaoToMaquina() :Maquina {
    return when (TipoMaquina.parseTipoMaquina(tipoMaquina)) {
        TipoMaquina.ENCORDADORA -> EncordadoraDao(id).fromEncordadoraDaoToEncordadora()
        TipoMaquina.PERSONALIZADORA -> PersonalizadoraDao(id).fromPersonalizadoraDaoToPersonalizadora()
    }
}

fun MaquinaDao.fromMaquinaDaoToMaquina(
    modelo: String,
    marca: String,
    fechaAdquisicion: LocalDate,
    numeroSerie: String
): Maquina {
    return when (TipoMaquina.parseTipoMaquina(tipoMaquina)) {
        TipoMaquina.ENCORDADORA -> EncordadoraDao(id).fromEncordadoraDaoToEncordadora(modelo, marca, fechaAdquisicion, numeroSerie)
        TipoMaquina.PERSONALIZADORA -> PersonalizadoraDao(id).fromPersonalizadoraDaoToPersonalizadora(modelo, marca, fechaAdquisicion, numeroSerie)
    }
}

fun MaquinaDao.fromMaquinaDaoToMaquina(
    maquinaDao: UUIDEntityClass<MaquinaDao>
): Maquina {
    return when (TipoMaquina.parseTipoMaquina(tipoMaquina)) {
        TipoMaquina.ENCORDADORA -> EncordadoraDao(id).fromEncordadoraDaoToEncordadora(maquinaDao)
        TipoMaquina.PERSONALIZADORA -> PersonalizadoraDao(id).fromPersonalizadoraDaoToPersonalizadora(maquinaDao)
    }
}

fun EncordadoraDao.fromEncordadoraDaoToEncordadora(
    modelo: String,
    marca: String,
    fechaAdquisicion: LocalDate,
    numeroSerie: String
): Encordadora {
    return Encordadora(
        id = id.value,
        modelo = modelo,
        marca = marca,
        fechaAdquisicion = fechaAdquisicion,
        numeroSerie = numeroSerie,
        isManual = isManual,
        maxTension = maxTension,
        minTension = minTension
    )
}

suspend fun EncordadoraDao.fromEncordadoraDaoToEncordadora(): Encordadora {
    val maquina = EncordadoraRepositoryImpl(EncordadoraDao, MaquinaDao)
        .findById(id.value).await() ?: throw MapperException()
    return Encordadora(
        id = id.value,
        modelo = maquina.modelo,
        marca = maquina.marca,
        fechaAdquisicion = maquina.fechaAdquisicion,
        numeroSerie = maquina.numeroSerie,
        isManual = maquina.isManual,
        maxTension = maquina.maxTension,
        minTension = maquina.minTension
    )
}

fun EncordadoraDao.fromEncordadoraDaoToEncordadora(
    maquinaDao: UUIDEntityClass<MaquinaDao>
): Encordadora {
    val maquina = maquinaDao.findById(id.value) ?: throw MapperException()
    //val encordadora = EncordadoraDao.findById(id.value) ?: throw MapperException()
    return Encordadora(
        id = id.value,
        modelo = maquina.modelo,
        marca = maquina.marca,
        fechaAdquisicion = maquina.fechaAdquisicion,
        numeroSerie = maquina.numeroSerie,
        isManual = isManual,
        maxTension = maxTension,
        minTension = minTension
    )
}

fun PersonalizadoraDao.fromPersonalizadoraDaoToPersonalizadora(
    modelo: String,
    marca: String,
    fechaAdquisicion: LocalDate,
    numeroSerie: String
): Personalizadora{
    return Personalizadora(
        id = id.value,
        modelo = modelo,
        marca = marca,
        fechaAdquisicion = fechaAdquisicion,
        numeroSerie = numeroSerie,
        measuresManeuverability = measuresManeuverability,
        measuresBalance = measuresBalance,
        measuresRigidity = measuresRigidity
    )
}

suspend fun PersonalizadoraDao.fromPersonalizadoraDaoToPersonalizadora(): Personalizadora{
    val maquina = PersonalizadoraRepositoryImpl(PersonalizadoraDao, MaquinaDao)
        .findById(id.value).await() ?: throw MapperException()
    return Personalizadora(
        id = id.value,
        modelo = maquina.modelo,
        marca = maquina.marca,
        fechaAdquisicion = maquina.fechaAdquisicion,
        numeroSerie = maquina.numeroSerie,
        measuresManeuverability = maquina.measuresManeuverability,
        measuresBalance = maquina.measuresBalance,
        measuresRigidity = maquina.measuresRigidity
    )
}

fun PersonalizadoraDao.fromPersonalizadoraDaoToPersonalizadora(
    maquinaDao: UUIDEntityClass<MaquinaDao>
): Personalizadora{
    val maquina = maquinaDao.findById(id.value) ?: throw MapperException()
    return Personalizadora(
        id = id.value,
        modelo = maquina.modelo,
        marca = maquina.marca,
        fechaAdquisicion = maquina.fechaAdquisicion,
        numeroSerie = maquina.numeroSerie,
        measuresManeuverability = measuresManeuverability,
        measuresBalance = measuresBalance,
        measuresRigidity = measuresRigidity
    )
}

class MaquinaMapper: BaseMapper<Maquina,MaquinaDTO>() {
    override fun fromDTO(item: MaquinaDTO): Maquina {
        return when (item) {
            is EncordadoraDTO -> fromEncordadoraDTO(item)
            is PersonalizadoraDTO -> fromPersonalizadoraDTO(item)
            else -> throw Exception()
        }
    }

    fun fromEncordadoraDTO(item: EncordadoraDTO): Encordadora {
        return Encordadora(
            id = item.id,
            modelo = item.modelo,
            marca = item.marca,
            fechaAdquisicion = item.fechaAdquisicion,
            numeroSerie = item.numeroSerie,
            isManual = item.isManual,
            maxTension = item.maxTension,
            minTension = item.minTension
        )
    }

    fun fromPersonalizadoraDTO(item: PersonalizadoraDTO): Personalizadora {
        return Personalizadora(
            id = item.id,
            modelo = item.modelo,
            marca = item.marca,
            fechaAdquisicion = item.fechaAdquisicion,
            numeroSerie = item.numeroSerie,
            measuresRigidity = item.measuresRigidity,
            measuresManeuverability = item.measuresManeuverability,
            measuresBalance = item.measuresBalance
        )
    }


    override fun toDTO(item: Maquina): MaquinaDTO {
        return when (item) {
            is Personalizadora -> toPersonalizadoraDTO(item)
            is Encordadora -> toEncordadoraDTO(item)
            else -> throw Exception()
        }
    }

    fun toPersonalizadoraDTO(item: Personalizadora): PersonalizadoraDTO {
        return PersonalizadoraDTO(
            id = item.id,
            modelo = item.modelo,
            marca = item.marca,
            fechaAdquisicion = item.fechaAdquisicion,
            numeroSerie = item.numeroSerie,
            measuresManeuverability = item.measuresManeuverability,
            measuresBalance = item.measuresBalance,
            measuresRigidity = item.measuresRigidity
        )
    }

    fun toEncordadoraDTO(item: Encordadora): EncordadoraDTO {
        return EncordadoraDTO(
            id = item.id,
            modelo = item.modelo,
            marca = item.marca,
            fechaAdquisicion = item.fechaAdquisicion,
            numeroSerie = item.numeroSerie,
            isManual = item.isManual,
            maxTension = item.maxTension,
            minTension = item.minTension
        )
    }

    fun fromEncordadoraDTO(items: List<EncordadoraDTO>): List<Encordadora> {
        return items.map { fromEncordadoraDTO(it) }
    }
    fun toEncordadoraDTO(items: List<Encordadora>): List<EncordadoraDTO> {
        return items.map { toEncordadoraDTO(it) }
    }

    fun fromPersonalizadoraDTO(items: List<PersonalizadoraDTO>): List<Personalizadora> {
        return items.map { fromPersonalizadoraDTO(it) }
    }
    fun toPersonalizadoraDTO(items: List<Personalizadora>): List<PersonalizadoraDTO> {
        return items.map { toPersonalizadoraDTO(it) }
    }
}
package mappers

import dto.EncordadoraDTO
import dto.MaquinaDTO
import dto.PersonalizadoraDTO
import exceptions.MapperException
import models.*

/**
 * @author Ivan Azagra Troya
 *
 * Clase que hereda de BaseMapper y se encarga de pasar de
 * DTO a Modelo y de Modelo a DTO.
 */
class MaquinaMapper: BaseMapper<Maquina,MaquinaDTO>() {
    override fun fromDTO(item: MaquinaDTO): Maquina {
        return when (item) {
            is EncordadoraDTO -> fromEncordadoraDTO(item)
            is PersonalizadoraDTO -> fromPersonalizadoraDTO(item)
            else -> throw MapperException()
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
            else -> throw MapperException()
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
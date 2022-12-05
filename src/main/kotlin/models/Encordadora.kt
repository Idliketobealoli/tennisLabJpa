package models

import models.enums.TipoMaquina
import java.time.LocalDate
import java.util.*
import javax.persistence.*

/**
 * @author Daniel Rodriguez Muñoz
 *
 * Clase POKO de Encordadora, que será
 * traducida a una tabla en la Base de Datos.
 */
@Entity
@Table(name = "encordadoras")
@NamedQuery(name = "Encordadora.findAll", query = "select e from Encordadora e")
class Encordadora():Maquina() {
    var isManual: Boolean = false
    var maxTension: Double = 0.0
    var minTension: Double = 0.0
    constructor(
        id: UUID?,
        modelo: String,
        marca: String,
        fechaAdquisicion: LocalDate?,
        numeroSerie: String,
        isManual: Boolean,
        maxTension: Double,
        minTension: Double
    ): this() {
        this.id = id ?: UUID.randomUUID()
        this.modelo = modelo
        this.marca = marca
        this.fechaAdquisicion = fechaAdquisicion ?: LocalDate.now()
        this.numeroSerie = numeroSerie
        this.isManual = isManual
        this.maxTension = maxTension
        this.minTension = minTension
        this.tipoMaquina = TipoMaquina.ENCORDADORA
    }
}
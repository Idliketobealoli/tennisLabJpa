package models

import com.google.gson.GsonBuilder
import models.enums.TipoMaquina
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "encordadoras")
@NamedQuery(name = "Encordadora.findAll", query = "select e from Encordadora e")
class Encordadora():Maquina() {
    @Id
    @Column(name = "id")
    override var id = super.id
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
        this.id = id ?: super.id
        this.modelo = modelo
        this.marca = marca
        this.fechaAdquisicion = fechaAdquisicion ?: LocalDate.now()
        this.numeroSerie = numeroSerie
        this.isManual = isManual
        this.maxTension = maxTension
        this.minTension = minTension
        this.tipoMaquina = TipoMaquina.ENCORDADORA
    }

    constructor(
        id: UUID?,
        isManual: Boolean,
        maxTension: Double,
        minTension: Double
    ): this() {
        this.id = id ?: super.id
        this.isManual = isManual
        this.maxTension = maxTension
        this.minTension = minTension
        this.tipoMaquina = TipoMaquina.ENCORDADORA
    }
}
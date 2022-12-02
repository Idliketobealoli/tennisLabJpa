package models

import com.google.gson.GsonBuilder
import models.enums.TipoMaquina
import java.time.LocalDate
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.NamedQuery
import javax.persistence.Table

@Entity
@Table(name = "personalizadoras")
@NamedQuery(name = "Personalizadora.findAll", query = "select p from Personalizadora p")
class Personalizadora(): Maquina() {
    @Id
    @Column(name = "id")
    override var id = super.id
    var measuresManeuverability: Boolean = false
    var measuresBalance: Boolean = false
    var measuresRigidity: Boolean = false
    constructor(
        id: UUID?,
        modelo: String,
        marca: String,
        fechaAdquisicion: LocalDate?,
        numeroSerie: String,
        measuresRigidity: Boolean,
        measuresManeuverability: Boolean,
        measuresBalance: Boolean
    ): this() {
        this.id = id ?: super.id
        this.modelo = modelo
        this.marca = marca
        this.fechaAdquisicion = fechaAdquisicion ?: LocalDate.now()
        this.numeroSerie = numeroSerie
        this.measuresRigidity = measuresRigidity
        this.measuresBalance = measuresBalance
        this.measuresManeuverability = measuresManeuverability
        this.tipoMaquina = TipoMaquina.PERSONALIZADORA
    }

    constructor(
        id: UUID?,
        measuresRigidity: Boolean,
        measuresManeuverability: Boolean,
        measuresBalance: Boolean
    ) : this() {
        this.id = id ?: super.id
        this.measuresRigidity = measuresRigidity
        this.measuresBalance = measuresBalance
        this.measuresManeuverability = measuresManeuverability
        this.tipoMaquina = TipoMaquina.PERSONALIZADORA
    }
}

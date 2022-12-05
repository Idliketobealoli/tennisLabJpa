package models

import models.enums.TipoMaquina
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Type
import java.time.LocalDate
import java.util.*
import javax.persistence.*

/**
 * @author Ivan Azagra Troya
 *
 * Clase POKO de Maquina, que será
 * traducida a una tabla en la Base de Datos.
 */
@Entity
@Table(name = "maquinas")
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQuery(name = "Maquina.findAll", query = "select m from Maquina m")
class Maquina() {
    @Id
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator",
    )
    @Column(name = "id")
    @Type(type = "uuid-char")
    lateinit var id: UUID
    lateinit var modelo: String
    lateinit var marca: String

    @Column(name = "fecha_adquisicion")
    @Type(type = "org.hibernate.type.LocalDateType")
    lateinit var fechaAdquisicion: LocalDate
    lateinit var numeroSerie: String
    lateinit var tipoMaquina: TipoMaquina

    constructor(
        id: UUID?,
        modelo: String,
        marca: String,
        fechaAdquisicion:LocalDate?,
        numeroSerie: String,
        tipoMaquina: TipoMaquina
    ) : this(){
        this.id = id ?: UUID.randomUUID()
        this.modelo = modelo
        this.marca = marca
        this.fechaAdquisicion = fechaAdquisicion ?: LocalDate.now()
        this.numeroSerie = numeroSerie
        this.tipoMaquina = tipoMaquina
    }
}

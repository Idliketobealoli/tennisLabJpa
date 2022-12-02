package models

import com.google.gson.GsonBuilder
import models.enums.TipoMaquina
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Type
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "maquinas")
@NamedQuery(name = "Maquina.findAll", query = "select m from Maquina m")
open class Maquina() {
    @Id
    @Column(name = "id")
    open lateinit var id: UUID
    lateinit var modelo: String
    lateinit var marca: String

    @Column(name = "fecha_adquisicion")
    @Type(type = "org.hibernate.type.LocalDateType")
    @CreationTimestamp
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

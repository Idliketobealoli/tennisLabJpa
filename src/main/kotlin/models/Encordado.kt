package models

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import models.enums.TipoTarea
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "encordados")
@NamedQuery(name = "Encordado.findAll", query = "select e from Encordado e")
class Encordado():Tarea() {
    @Id
    @Column(name = "id")
    override var id = super.id
    var tensionHorizontal: Double = 0.0

    @ManyToOne
    @JoinColumn(name = "c_horizontal_id", referencedColumnName = "id", nullable = false)
    lateinit var cordajeHorizontal: Producto
    var tensionVertical: Double = 0.0

    @ManyToOne
    @JoinColumn(name = "c_vertical_id", referencedColumnName = "id", nullable = false)
    lateinit var cordajeVertical: Producto
    var dosNudos: Boolean = false

    constructor(
        id: UUID?,
        raqueta: Producto,
        user: User,
        tensionHorizontal: Double,
        cordajeHorizontal: Producto,
        tensionVertical: Double,
        cordajeVertical: Producto,
        dosNudos: Boolean
    ) : this() {
        this.id = id ?: UUID.randomUUID()
        this.raqueta = raqueta
        this.user = user
        this.tensionHorizontal = tensionHorizontal
        this.tensionVertical = tensionVertical
        this.cordajeHorizontal = cordajeHorizontal
        this.cordajeVertical = cordajeVertical
        this.dosNudos = dosNudos
        this.precio = (15.0+cordajeHorizontal.precio+cordajeVertical.precio)
        this.tipoTarea = TipoTarea.ENCORDADO
    }

    constructor(
        id: UUID?,
        tensionHorizontal: Double,
        cordajeHorizontal: Producto,
        tensionVertical: Double,
        cordajeVertical: Producto,
        dosNudos: Boolean
    ) : this() {
        this.id = id ?: UUID.randomUUID()
        this.tensionHorizontal = tensionHorizontal
        this.tensionVertical = tensionVertical
        this.cordajeHorizontal = cordajeHorizontal
        this.cordajeVertical = cordajeVertical
        this.dosNudos = dosNudos
        this.precio = (15.0+cordajeHorizontal.precio+cordajeVertical.precio)
        this.tipoTarea = TipoTarea.ENCORDADO
    }
}

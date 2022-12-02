package models

import com.google.gson.annotations.Expose
import models.enums.TipoTarea
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "adquisiciones")
@NamedQuery(name = "Adquisicion.findAll", query = "select a from Adquisicion a")
class Adquisicion(): Tarea() {
    @Id
    @Column(name = "id")
    override var id = super.id

    @ManyToOne
    @JoinColumn(name = "p_adquirido_id", referencedColumnName = "id", nullable = false)
    @Expose
    lateinit var productoAdquirido: Producto

    constructor(
        id: UUID?,
        raqueta: Producto,
        user: User,
        productoAdquirido: Producto,
        precio: Double
    ) : this() {
        this.id = id ?: UUID.randomUUID()
        this.raqueta = raqueta
        this.user = user
        this.productoAdquirido = productoAdquirido
        this.precio = precio
        this.tipoTarea = TipoTarea.ADQUISICION
    }

    constructor(
        id: UUID?,
        productoAdquirido: Producto,
        precio: Double
    ) : this() {
        this.id = id ?: UUID.randomUUID()
        this.productoAdquirido = productoAdquirido
        this.precio = precio
        this.tipoTarea = TipoTarea.ADQUISICION
    }
}

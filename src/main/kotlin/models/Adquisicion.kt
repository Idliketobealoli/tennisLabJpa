package models

import com.google.gson.annotations.Expose
import models.enums.TipoTarea
import java.util.*
import javax.persistence.*

/**
 * @author Daniel Rodriguez Muñoz
 *
 * Clase POKO de Adquisicion, que será
 * traducida a una tabla en la Base de Datos.
 */
@Entity
@Table(name = "adquisiciones")
@NamedQuery(name = "Adquisicion.findAll", query = "select a from Adquisicion a")
class Adquisicion(): Tarea() {
    @ManyToOne
    @JoinColumn(name = "p_adquirido_id", referencedColumnName = "id", nullable = false)
    @Expose
    lateinit var productoAdquirido: Producto

    constructor(
        id: UUID?,
        raqueta: Producto,
        user: User,
        pedido: Pedido,
        productoAdquirido: Producto,
        precio: Double
    ) : this() {
        this.id = id ?: UUID.randomUUID()
        this.raqueta = raqueta
        this.user = user
        this.pedido = pedido
        this.productoAdquirido = productoAdquirido
        this.precio = precio
        this.tipoTarea = TipoTarea.ADQUISICION
    }
}

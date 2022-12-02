package models

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import models.enums.TipoTarea
import java.util.*
import javax.persistence.*

/**
 * @author Iván Azagra Troya
 * Clase abstracta de la entidad tarea con un identificador
 * y el producto que se pasará
 */

@Entity
@Table(name = "tareas")
@NamedQuery(name = "Tarea.findAll", query = "select t from Tarea t")
open class Tarea(){
    @Id
    @Column(name = "id")
    open lateinit var id: UUID

    @ManyToOne
    @JoinColumn(name = "producto_id", referencedColumnName = "id", nullable = false)
    lateinit var raqueta: Producto
    open var precio: Double = 0.0

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    lateinit var user: User
    lateinit var tipoTarea: TipoTarea

    @ManyToOne
    @JoinColumn(name = "pedido_id", referencedColumnName = "id", nullable = false)
    lateinit var pedido: Pedido

    constructor(
        id: UUID?,
        raqueta: Producto,
        precio: Double?,
        user: User,
        tipoTarea: TipoTarea
    ) : this () {
        this.id = id ?: UUID.randomUUID()
        this.raqueta = raqueta
        this.precio = precio ?: 0.0
        this.user = user
        this.tipoTarea = tipoTarea
    }
}

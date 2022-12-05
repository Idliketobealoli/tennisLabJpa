package models

import models.enums.TipoTarea
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*

/**
 * @author Daniel Rodriguez Muñoz
 *
 * Clase POKO de Tarea, que será
 * traducida a una tabla en la Base de Datos.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "tareas")
@NamedQuery(name = "Tarea.findAll", query = "select t from Tarea t")
class Tarea(){
    @Id
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator",
    )
    @Column(name = "id")
    @Type(type = "uuid-char")
    lateinit var id: UUID

    @ManyToOne
    @JoinColumn(name = "producto_id", referencedColumnName = "id", nullable = false)
    lateinit var raqueta: Producto
    var precio: Double = 0.0

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
        pedido: Pedido,
        tipoTarea: TipoTarea
    ) : this () {
        this.id = id ?: UUID.randomUUID()
        this.raqueta = raqueta
        this.precio = precio ?: 0.0
        this.user = user
        this.pedido = pedido
        this.tipoTarea = tipoTarea
    }
}

package models

import models.enums.TipoTarea
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*

/**
 * @author Iván Azagra Troya
 * Clase abstracta de la entidad tarea con un identificador
 * y el producto que se pasará
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

    @ManyToOne//(cascade = [CascadeType.DETACH])
    @JoinColumn(name = "producto_id", referencedColumnName = "id", nullable = false)
    lateinit var raqueta: Producto
    var precio: Double = 0.0

    @ManyToOne//(cascade = [CascadeType.DETACH])
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    lateinit var user: User
    lateinit var tipoTarea: TipoTarea

    @ManyToOne//(cascade = [CascadeType.DETACH])
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

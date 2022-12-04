package models

import models.enums.PedidoEstado
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Type
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "pedidos")
@NamedQuery(name = "Pedido.findAll", query = "select p from Pedido p")
class Pedido() {
    @Id
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator",
    )
    @Column(name = "id")
    @Type(type = "uuid-char")
    lateinit var id: UUID
    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false)
    lateinit var client: User
    lateinit var state: PedidoEstado

    @Column(name = "fecha_entrada")
    @Type(type = "org.hibernate.type.LocalDateType")
    @CreationTimestamp
    lateinit var fechaEntrada: LocalDate
    @Column(name = "fecha_programada")
    @Type(type = "org.hibernate.type.LocalDateType")
    @CreationTimestamp
    lateinit var fechaProgramada: LocalDate
    @Column(name = "fecha_salida")
    @Type(type = "org.hibernate.type.LocalDateType")
    @CreationTimestamp
    lateinit var fechaSalida: LocalDate
    @Column(name = "fecha_entrega")
    @Type(type = "org.hibernate.type.LocalDateType")
    @CreationTimestamp
    lateinit var fechaEntrega: LocalDate
    var precio: Double = 0.0

    constructor(
        id: UUID?,
        client: User,
        state: PedidoEstado,
        fechaEntrada: LocalDate?,
        fechaProgramada: LocalDate,
        fechaSalida: LocalDate,
        fechaEntrega: LocalDate?,
        precio: Double
    ): this() {
        this.id = id ?: UUID.randomUUID()
        this.client = client
        this.state = state
        this.fechaEntrada = fechaEntrada ?: LocalDate.now()
        this.fechaProgramada = fechaProgramada
        this.fechaSalida = fechaSalida
        this.fechaEntrega = fechaEntrega ?: fechaSalida
        this.precio = precio
    }
}

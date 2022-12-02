package models

import com.google.gson.GsonBuilder
import models.enums.PedidoEstado
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Type
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "pedidos")
@NamedQuery(name = "Pedido.findAll", query = "select p from Pedido p")
class Pedido() {
    @Id
    @Column(name = "id")
    lateinit var id: UUID
    @OneToMany(mappedBy = "pedido", orphanRemoval = true, fetch = FetchType.LAZY)
    lateinit var tareas: List<Tarea>
    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false)
    lateinit var client: User
    @OneToMany(mappedBy = "pedido", orphanRemoval = true, fetch = FetchType.LAZY)
    lateinit var turnos: List<Turno>
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
        tareas: List<Tarea>, // todo SizedIterable
        client: User,
        turnos: List<Turno>, // todo SizedIterable
        state: PedidoEstado,
        fechaEntrada: LocalDate?,
        fechaProgramada: LocalDate,
        fechaSalida: LocalDate,
        fechaEntrega: LocalDate?
    ): this() {
        this.id = id ?: UUID.randomUUID()
        this.tareas = tareas
        this.client = client
        this.turnos = turnos
        this.state = state
        this.fechaEntrada = fechaEntrada ?: LocalDate.now()
        this.fechaProgramada = fechaProgramada
        this.fechaSalida = fechaSalida
        this.fechaEntrega = fechaEntrega ?: fechaSalida
        this.precio = tareas.sumOf { it.precio }
    }
}

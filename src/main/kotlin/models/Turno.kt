package models

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Type
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "turnos")
@NamedQuery(name = "Turno.findAll", query = "select t from Turno t")
class Turno() {
    @Id
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator",
    )
    @Column(name = "id")
    @Type(type = "uuid-char")
    lateinit var id: UUID

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    lateinit var worker: User

    @ManyToOne
    @JoinColumn(name = "maquina_id", referencedColumnName = "id", nullable = false)
    lateinit var maquina: Maquina

    @Column(name = "hora_inicio")
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    @CreationTimestamp
    lateinit var horaInicio: LocalDateTime

    @Column(name = "hora_fin")
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    @CreationTimestamp
    lateinit var horaFin: LocalDateTime
    var numPedidosActivos: Int = 1

    @ManyToOne
    @JoinColumn(name = "tarea1_id", referencedColumnName = "id", nullable = false)
    lateinit var tarea1: Tarea

    @ManyToOne
    @JoinColumn(name = "tarea2_id", referencedColumnName = "id", nullable = true)
    var tarea2: Tarea? = null

    @ManyToOne
    @JoinColumn(name = "pedido_id", referencedColumnName = "id", nullable = false)
    lateinit var pedido: Pedido

    constructor(
        id: UUID?,
        worker: User,
        maquina: Maquina,
        horaInicio: LocalDateTime,
        horaFin: LocalDateTime?,
        tarea1: Tarea,
        tarea2: Tarea?,
        pedido: Pedido
    ) : this() {
        this.id = id ?: UUID.randomUUID()
        this.worker = worker
        this.maquina = maquina
        this.horaInicio = horaInicio
        this.horaFin = horaFin ?: this.horaInicio.plusHours(4L)
        this.tarea1 = tarea1
        this.tarea2 = tarea2
        this.pedido = pedido

        if (this.tarea2 != null) {
            numPedidosActivos++
        }
    }
}
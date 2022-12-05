package dto

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import models.Tarea
import models.Turno
import models.User
import models.enums.PedidoEstado
import java.time.LocalDate
import java.util.*

/**
 * @author Ivan Azagra Troya
 *
 * Clase POKO de Pedido usada en el programa.
 * Consta de un metodo toString() overrideado que
 * devuelve el objeto como JSON, asi como un metodo
 * fromJSON y un metodo toJSON.
 * Tiene campos calculados automaticamente.
 */
class PedidoDTO() {
    @Expose
    lateinit var id: UUID
    @Expose lateinit var tareas: List<Tarea>
    @Expose lateinit var client: User
    lateinit var turnos: List<Turno>
    @Expose lateinit var state: PedidoEstado
    lateinit var fechaEntrada: LocalDate
    lateinit var fechaProgramada: LocalDate
    lateinit var fechaSalida: LocalDate
    lateinit var fechaEntrega: LocalDate
    @Expose lateinit var fechaEntradaString: String
    @Expose lateinit var fechaProgramadaString: String
    @Expose lateinit var fechaSalidaString: String
    @Expose lateinit var fechaEntregaString: String
    @Expose var precio: Double = 0.0

    constructor(
        id: UUID?,
        tareas: List<Tarea>?,
        client: User,
        turnos: List<Turno>?,
        state: PedidoEstado,
        fechaEntrada: LocalDate?,
        fechaProgramada: LocalDate,
        fechaSalida: LocalDate,
        fechaEntrega: LocalDate?
    ) : this() {
        this.id = id ?: UUID.randomUUID()
        this.tareas = tareas ?: listOf()
        this.client = client
        this.turnos = turnos ?: listOf()
        this.state = state
        this.fechaEntrada = fechaEntrada ?: LocalDate.now()
        this.fechaProgramada = fechaProgramada
        this.fechaSalida = fechaSalida
        this.fechaEntrega = fechaEntrega ?: fechaSalida
        this.fechaEntradaString = this.fechaEntrada.toString()
        this.fechaProgramadaString = this.fechaProgramada.toString()
        this.fechaSalidaString = this.fechaSalida.toString()
        this.fechaEntregaString = this.fechaEntrega.toString()
        this.precio = this.tareas.sumOf { it.precio }
    }

    fun fromJSON(json: String): PedidoDTO? {
        return Gson().fromJson(json, PedidoDTO::class.java)
    }

    fun toJSON(): String {
        return GsonBuilder().setPrettyPrinting()
            .excludeFieldsWithoutExposeAnnotation()
            .create().toJson(this)
    }

    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting()
            .excludeFieldsWithoutExposeAnnotation()
            .create().toJson(this)
    }
}
package mappers

import dto.PedidoDTO
import kotlinx.coroutines.runBlocking
import models.Pedido
import models.Tarea
import models.Turno
import repositories.TareaRepository
import repositories.TurnoRepository

class PedidoMapper: BaseMapper<Pedido, PedidoDTO>() {
    override fun fromDTO(item: PedidoDTO): Pedido {
        return Pedido(
            id = item.id,
            client = item.client,
            state = item.state,
            fechaEntrada = item.fechaEntrada,
            fechaProgramada = item.fechaProgramada,
            fechaSalida = item.fechaSalida,
            fechaEntrega = item.fechaEntrega,
            precio = item.precio
        )
    }

    override fun toDTO(item: Pedido): PedidoDTO {
        val tRepo = TareaRepository()
        val turRepo = TurnoRepository()
        val tareas = mutableListOf<Tarea>()
        val turnos = mutableListOf<Turno>()
        runBlocking {
            tareas.addAll(tRepo.readAll().filter { it.pedido.id == item.id })
            turnos.addAll(turRepo.readAll().filter { it.pedido.id == item.id })
        }
        return PedidoDTO(
            id = item.id,
            tareas = tareas,
            client = item.client,
            turnos = turnos,
            state = item.state,
            fechaEntrada = item.fechaEntrada,
            fechaProgramada = item.fechaProgramada,
            fechaSalida = item.fechaSalida,
            fechaEntrega = item.fechaEntrega
        )
    }
}
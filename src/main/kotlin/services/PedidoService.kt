package services

import dto.PedidoDTO
import mappers.PedidoMapper
import models.Pedido
import repositories.PedidoRepository
import repositories.TareaRepository
import repositories.TurnoRepository
import java.util.UUID

/**
 * @author Ivan Azagra Troya
 *
 * Clase encargada de llamar a las operaciones del repositorio correspondientes y
 * pasar el resultado de las mismas a DTO usando para ello
 * el mapper y el repositorio de Pedido.
 */
open class PedidoService : BaseService<Pedido, UUID, PedidoRepository>(
    PedidoRepository()
) {
    val mapper = PedidoMapper()
    val tareaRepo = TareaRepository()
    val turnoRepo = TurnoRepository()

    suspend fun getAllPedidos(): List<PedidoDTO> {
        return mapper.toDTO(this.findAll().toList())
    }

    suspend fun getPedidoById(id: UUID): PedidoDTO? {
        return this.findById(id)?.let { mapper.toDTO(it) }
    }

    suspend fun createPedido(pedido: PedidoDTO): PedidoDTO {
        pedido.tareas.forEach { tareaRepo.create(it) }
        pedido.turnos.forEach { turnoRepo.create(it) }
        return mapper.toDTO(this.insert(mapper.fromDTO(pedido)))
    }

    suspend fun deletePedido(pedido: PedidoDTO): Boolean {
        return this.delete(mapper.fromDTO(pedido))
    }
}
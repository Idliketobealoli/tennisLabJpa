package services

import dto.PedidoDTO
import mappers.PedidoMapper
import models.Pedido
import java.util.UUID

class PedidoService : BaseService<Pedido, UUID, PedidoRepository>(
    PedidoRepository()) {
    val mapper = PedidoMapper()

    suspend fun getAllPedidos(): List<PedidoDTO> {
        return mapper.toDTO(this.findAll().toList())
    }

    suspend fun getPedidoById(id: UUID): PedidoDTO? {
        return this.findById(id)?.let { mapper.toDTO(it) }
    }

    suspend fun createPedido(pedido: PedidoDTO): PedidoDTO {
        return mapper.toDTO(this.insert(mapper.fromDTO(pedido)))
    }

    suspend fun deletePedido(pedido: PedidoDTO): Boolean {
        return this.delete(mapper.fromDTO(pedido))
    }
}
package controllers

import dto.PedidoDTO
import dto.UserDTO
import models.enums.PedidoEstado
import services.PedidoService
import java.util.*

object PedidoController {
    var service = PedidoService()

    suspend fun findAllPedidos(): String {
        return service.getAllPedidos().toString()
    }

    suspend fun findAllPedidosFromUser(user: UserDTO): String {
        return service.getAllPedidos().filter { it.client.id == user.id }.toString()
    }

    suspend fun findAllPedidosWithEstado(state: PedidoEstado): String {
        return service.getAllPedidos().filter { it.state == state }.toString()
    }

    suspend fun getPedidoById(id: UUID): String {
        return service.getPedidoById(id)?.toJSON() ?: "Pedido with id $id not found."
    }

    suspend fun insertPedido(dto: PedidoDTO): String {
        return service.createPedido(dto).toJSON()
    }

    suspend fun deletePedido(dto: PedidoDTO): String {
        val result = service.deletePedido(dto)
        return if (result) dto.toJSON()
        else "Could not delete Pedido with id ${dto.id}"
    }
}
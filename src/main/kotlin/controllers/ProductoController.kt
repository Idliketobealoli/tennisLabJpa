package controllers

import dto.ProductoDTO
import models.enums.TipoProducto
import services.ProductoService
import java.util.*

object ProductoController {
    var service = ProductoService()

    suspend fun findAllProductos(): String {
        return service.getAllProductos().toString()
    }

    suspend fun findAllProductosDisponibles(): String {
        return service.getAllProductos().filter { it.stock > 0 }.toString()
    }

    suspend fun getProductoById(id: UUID): String {
        return service.getProductoById(id)?.toJSON() ?: "Producto with id $id not found."
    }

    suspend fun getProductosByTipo(tipo: TipoProducto): String {
        return service.getAllProductos().filter { it.tipoProducto == tipo }.toString()
    }

    suspend fun insertProducto(dto: ProductoDTO): String {
        return service.createProducto(dto).toJSON()
    }

    suspend fun deleteProducto(dto: ProductoDTO): String {
        val result = service.deleteProducto(dto)
        return if (result) dto.toJSON()
        else "Could not delete Producto with id ${dto.id}"
    }
}
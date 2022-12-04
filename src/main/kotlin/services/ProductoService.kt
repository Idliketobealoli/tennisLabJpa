package services

import dto.ProductoDTO
import mappers.ProductoMapper
import models.Producto
import repositories.ProductoRepository
import java.util.*

open class ProductoService: BaseService<Producto, UUID, ProductoRepository>(
    ProductoRepository()) {
    val mapper = ProductoMapper()

    suspend fun getAllProductos(): List<ProductoDTO> {
        return mapper.toDTO(this.findAll().toList())
    }

    suspend fun getProductoById(id: UUID): ProductoDTO? {
        return this.findById(id)?.let { mapper.toDTO(it) }
    }

    suspend fun createProducto(user: ProductoDTO): ProductoDTO {
        return mapper.toDTO(this.insert(mapper.fromDTO(user)))
    }

    suspend fun deleteProducto(user: ProductoDTO): Boolean {
        return this.delete(mapper.fromDTO(user))
    }
}
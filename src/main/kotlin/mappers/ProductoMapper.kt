package mappers

import dto.ProductoDTO
import entities.ProductoDao
import models.Producto
import models.enums.TipoProducto

fun ProductoDao.fromProductoDaoToProducto(): Producto{
    return Producto(
        id = id.value,
        tipoProducto = TipoProducto.parseTipoProducto(tipoProducto),
        marca = marca,
        modelo = modelo,
        precio = precio,
        stock = stock
    )
}

class ProductoMapper: BaseMapper<Producto,ProductoDTO>() {
    override fun fromDTO(item: ProductoDTO): Producto {
        return Producto(
            id = item.id,
            tipoProducto = item.tipoProducto,
            marca = item.marca,
            modelo = item.modelo,
            precio = item.precio,
            stock = item.stock
        )
    }

    override fun toDTO(item: Producto): ProductoDTO {
        return ProductoDTO(
            id = item.id,
            tipoProducto = item.tipoProducto,
            marca = item.marca,
            modelo = item.modelo,
            precio = item.precio,
            stock = item.stock
        )
    }
}
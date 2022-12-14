package mappers

import dto.ProductoDTO
import models.Producto

/**
 * @author Ivan Azagra Troya
 *
 * Clase que hereda de BaseMapper y se encarga de pasar de
 * DTO a Modelo y de Modelo a DTO.
 */
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
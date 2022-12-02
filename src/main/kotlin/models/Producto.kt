package models

import models.enums.TipoProducto
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.NamedQuery
import javax.persistence.Table

/**
 * @author Iván Azagra Troya
 * Clase POKO de la entidad producto
 */

@Entity
@Table(name = "productos")
@NamedQuery(name = "Producto.findAll", query = "select p from Producto p")
class Producto() {
    @Id
    @Column(name = "id")
    lateinit var id: UUID
    lateinit var tipoProducto: TipoProducto
    lateinit var marca: String
    lateinit var modelo: String
    var precio: Double = 0.0
    var stock: Int = 0

    constructor(
        id: UUID?,
        tipoProducto: TipoProducto,
        marca: String,
        modelo: String,
        precio: Double,
        stock: Int?
    ): this() {
        this.id = id ?: UUID.randomUUID()
        this.tipoProducto = tipoProducto
        this.marca = marca
        this.modelo = modelo
        this.precio = precio
        this.stock = stock ?: 0
    }
}

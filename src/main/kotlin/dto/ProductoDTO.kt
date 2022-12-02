package dto

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import models.enums.TipoProducto
import util.toLocalMoney
import java.util.*

class ProductoDTO() {
    @Expose lateinit var id: UUID
    @Expose lateinit var tipoProducto: TipoProducto
    @Expose lateinit var marca: String
    @Expose lateinit var modelo: String
    @Expose var precio: Double = 0.0
    @Expose var stock: Int = 0

    constructor(
        id: UUID?,
        tipoProducto: TipoProducto,
        marca: String,
        modelo: String,
        precio: Double,
        stock: Int?
    ) : this () {
        this.id = id ?: UUID.randomUUID()
        this.tipoProducto = tipoProducto
        this.marca = marca
        this.modelo = modelo
        this.precio = precio
        this.stock = stock ?: 0
    }

    fun fromJSON(json: String): ProductoDTO? {
        return Gson().fromJson(json, ProductoDTO::class.java)
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
package dto

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import models.Pedido
import models.Producto
import models.User
import java.util.*

/**
 * @author Ivan Azagra Troya
 *
 * Clase POKO de Adquisicion usada en el programa.
 * Consta de un metodo toString() overrideado que
 * devuelve el objeto como JSON, asi como un metodo
 * fromJSON y un metodo toJSON.
 */
class AdquisicionDTO(): TareaDTO {
    @Expose lateinit var id: UUID
    @Expose lateinit var raqueta: Producto
    @Expose lateinit var user: User
    @Expose lateinit var pedido: Pedido
    @Expose lateinit var productoAdquirido: Producto
    @Expose var precio: Double = 0.0

    constructor(
        id: UUID?,
        raqueta: Producto,
        user: User,
        pedido: Pedido,
        productoAdquirido: Producto
    ) : this() {
        this.id = id ?: UUID.randomUUID()
        this.raqueta = raqueta
        this.user = user
        this.pedido = pedido
        this.productoAdquirido = productoAdquirido
        this.precio = productoAdquirido.precio
    }

    fun fromJSON(json: String): AdquisicionDTO? {
        return Gson().fromJson(json, AdquisicionDTO::class.java)
    }

    override fun toJSON(): String {
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
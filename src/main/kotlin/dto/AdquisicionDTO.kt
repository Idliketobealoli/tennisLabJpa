package dto

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import models.Producto
import models.User
import java.util.*

class AdquisicionDTO(): TareaDTO {
    @Expose lateinit var id: UUID
    @Expose lateinit var raqueta: Producto
    @Expose lateinit var user: User
    @Expose lateinit var productoAdquirido: Producto
    @Expose var precio: Double = 0.0

    constructor(
        id: UUID?,
        raqueta: Producto,
        user: User,
        productoAdquirido: Producto
    ) : this() {
        this.id = id ?: UUID.randomUUID()
        this.raqueta = raqueta
        this.user = user
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
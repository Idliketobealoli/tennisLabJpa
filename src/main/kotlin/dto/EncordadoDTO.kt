package dto

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import models.Producto
import models.User
import util.toLocalMoney
import java.util.*

class EncordadoDTO(): TareaDTO {
    @Expose
    lateinit var id: UUID
    @Expose lateinit var raqueta: Producto
    @Expose lateinit var user: User
    @Expose var tensionHorizontal: Double = 0.0
    @Expose lateinit var cordajeHorizontal: Producto
    @Expose var tensionVertical: Double = 0.0
    @Expose lateinit var cordajeVertical: Producto
    @Expose var dosNudos: Boolean = true
    @Expose var precio: Double = 0.0

    constructor(
        id: UUID?,
        raqueta: Producto,
        user: User,
        tensionHorizontal: Double,
        cordajeHorizontal: Producto,
        tensionVertical: Double,
        cordajeVertical: Producto,
        dosNudos: Boolean
    ) : this() {
        this.id = id ?: UUID.randomUUID()
        this.raqueta = raqueta
        this.user = user
        this.tensionHorizontal = tensionHorizontal
        this.cordajeHorizontal = cordajeHorizontal
        this.tensionVertical = tensionVertical
        this.cordajeVertical = cordajeVertical
        this.dosNudos = dosNudos
        this.precio = (15.0+cordajeHorizontal.precio+cordajeVertical.precio)
    }

    fun fromJSON(json: String): EncordadoDTO? {
        return Gson().fromJson(json, EncordadoDTO::class.java)
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
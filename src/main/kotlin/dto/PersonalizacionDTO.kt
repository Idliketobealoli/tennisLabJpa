package dto

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import models.Pedido
import models.Producto
import models.User
import java.util.*

class PersonalizacionDTO(): TareaDTO {
    @Expose lateinit var id: UUID
    @Expose lateinit var raqueta: Producto
    @Expose lateinit var user: User
    @Expose lateinit var pedido: Pedido
    @Expose var peso: Int = 0
    @Expose var balance: Double = 0.0
    @Expose var rigidez: Int = 0
    @Expose val precio: Double = 60.0

    constructor(
        id: UUID?,
        raqueta: Producto,
        user: User,
        pedido: Pedido,
        peso: Int,
        balance: Double,
        rigidez: Int
    ) : this() {
        this.id = id ?: UUID.randomUUID()
        this.raqueta = raqueta
        this.user = user
        this.pedido = pedido
        this.peso = peso
        this.balance = balance
        this.rigidez = rigidez
    }

    fun fromJSON(json: String): PersonalizacionDTO? {
        return Gson().fromJson(json, PersonalizacionDTO::class.java)
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
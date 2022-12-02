package dto

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import models.enums.Profile
import java.util.UUID

class UserDTO() {
    @Expose
    lateinit var id: UUID
    @Expose
    lateinit var nombre: String
    @Expose
    lateinit var apellido: String
    lateinit var telefono: String
    @Expose
    lateinit var email: String
    lateinit var password: String
    @Expose
    lateinit var perfil: Profile

    constructor(
        id: UUID?,
        nombre: String,
        apellido: String,
        telefono: String,
        email: String,
        password: String,
        perfil: Profile
    ) :this() {
        this.id = id ?: UUID.randomUUID()
        this.nombre = nombre
        this.apellido = apellido
        this.telefono = telefono
        this.email = email
        this.password = password
        this.perfil = perfil
    }

    fun fromJSON(json: String): UserDTO? {
        return Gson().fromJson(json, UserDTO::class.java)
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
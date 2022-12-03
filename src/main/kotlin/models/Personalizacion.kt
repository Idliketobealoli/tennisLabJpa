package models

import models.enums.TipoTarea
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "personalizaciones")
@NamedQuery(name = "Personalizacion.findAll", query = "select p from Personalizacion p")
class Personalizacion(): Tarea() {
    var peso: Int = 0
    var balance: Double = 0.0
    var rigidez: Int = 0

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
        this.precio = 60.0
        this.tipoTarea = TipoTarea.PERSONALIZACION
    }
}

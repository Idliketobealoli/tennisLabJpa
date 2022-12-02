package models

import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.NamedQuery
import javax.persistence.Table

@Entity
@Table(name = "users")
@NamedQuery(name = "User.findAll", query = "select u from User u")
class User() {
    @Id
    @Column(name = "id")
    lateinit var id:UUID
    lateinit var nombre: String
    lateinit var apellido: String
    lateinit var telefono: String
    lateinit var email: String
    lateinit var password: String
    lateinit var perfil: String

    constructor(
        id: UUID?,
        nombre: String,
        apellido: String,
        telefono: String,
        email: String,
        password: String,
        perfil: String
    ) : this(){
        this.id = id ?: UUID.randomUUID()
        this.nombre = nombre
        this.apellido = apellido
        this.telefono = telefono
        this.email = email
        this.password = password
        this.perfil = perfil
    }
}
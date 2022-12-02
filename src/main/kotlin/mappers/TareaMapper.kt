package mappers

import dto.*
import entities.*
import exceptions.MapperException
import models.*
import models.enums.TipoTarea
import org.jetbrains.exposed.dao.UUIDEntityClass
import repositories.adquisicion.AdquisicionRepositoryImpl
import repositories.encordado.EncordadoRepositoryImpl
import repositories.personalizacion.PersonalizacionRepositoryImpl
import repositories.tarea.TareaRepositoryImpl


/**
 * @author Iván Azagra Troya
 * Este Kotlin.file crea las funciones que recogen las entidades DAO de las diferentes tareas
 * para devolver la clase
 */

// TODO al pasarle por parámetro el atributo puede dar error al tener que ser introducido desde otro punto de la app

suspend fun TareaDao.fromTareaDaoToTarea(): Tarea {
    return when (TipoTarea.parseTipoTarea(tipoTarea)) {
        TipoTarea.PERSONALIZACION -> PersonalizacionDao(id).fromPersonalizacionDaoToPersonalizacion()
        TipoTarea.ENCORDADO -> EncordadoDao(id).fromEncordadoDaoToEncordado()
        TipoTarea.ADQUISICION -> AdquisicionDao(id).fromAdquisicionDaoToAdquisicion()
    }
}

fun TareaDao.fromTareaDaoToTarea(
    raqueta: Producto,
    user: User
): Tarea {
    return when (TipoTarea.parseTipoTarea(tipoTarea)) {
        TipoTarea.PERSONALIZACION -> PersonalizacionDao(id).fromPersonalizacionDaoToPersonalizacion(raqueta, user)
        TipoTarea.ENCORDADO -> EncordadoDao(id).fromEncordadoDaoToEncordado(raqueta, user)
        TipoTarea.ADQUISICION -> AdquisicionDao(id).fromAdquisicionDaoToAdquisicion(raqueta, user)
    }
}

fun TareaDao.fromTareaDaoToTarea(
    tareaDao: UUIDEntityClass<TareaDao>
): Tarea {
    return when (TipoTarea.parseTipoTarea(tipoTarea)) {
        TipoTarea.PERSONALIZACION -> PersonalizacionDao(id).fromPersonalizacionDaoToPersonalizacion(tareaDao)
        TipoTarea.ENCORDADO -> EncordadoDao(id).fromEncordadoDaoToEncordado(tareaDao)
        TipoTarea.ADQUISICION -> AdquisicionDao(id).fromAdquisicionDaoToAdquisicion(tareaDao)
    }
}

fun PersonalizacionDao.fromPersonalizacionDaoToPersonalizacion(
    tareaDao: UUIDEntityClass<TareaDao>): Personalizacion {
    val tarea = tareaDao.findById(id.value) ?: throw MapperException()
    return Personalizacion(
        id = id.value,
        raqueta = tarea.raqueta.fromProductoDaoToProducto(),
        user = tarea.user.fromUserDaoToUser(),
        peso = peso,
        balance = balance,
        rigidez = rigidez
    )
}

fun PersonalizacionDao.fromPersonalizacionDaoToPersonalizacion(
    raqueta: Producto,
    user: User
): Personalizacion {
    return Personalizacion(
        id = id.value,
        raqueta = raqueta,
        user = user,
        peso = peso,
        balance = balance,
        rigidez = rigidez
    )
}

suspend fun PersonalizacionDao.fromPersonalizacionDaoToPersonalizacion(): Personalizacion {
    val tarea = PersonalizacionRepositoryImpl(PersonalizacionDao, TareaDao)
        .findById(id.value).await() ?: throw MapperException()
    return Personalizacion(
        id = id.value,
        raqueta = tarea.raqueta,
        user = tarea.user,
        peso = tarea.peso,
        balance = tarea.balance,
        rigidez = tarea.rigidez
    )
}

fun EncordadoDao.fromEncordadoDaoToEncordado(
    tareaDao: UUIDEntityClass<TareaDao>
): Encordado {
    val tarea = tareaDao.findById(id.value) ?: throw MapperException()
    return Encordado(
        id = id.value,
        raqueta = tarea.raqueta.fromProductoDaoToProducto(),
        user = tarea.user.fromUserDaoToUser(),
        tensionHorizontal = tensionHorizontal,
        cordajeHorizontal = cordajeHorizontal.fromProductoDaoToProducto(),
        tensionVertical = tensionVertical,
        cordajeVertical = cordajeVertical.fromProductoDaoToProducto(),
        dosNudos = dosNudos
    )
}

suspend fun EncordadoDao.fromEncordadoDaoToEncordado(): Encordado {
    val tarea = EncordadoRepositoryImpl(TareaDao, ProductoDao, EncordadoDao)
        .findById(id.value).await() ?: throw MapperException()
    return Encordado(
        id = id.value,
        raqueta = tarea.raqueta,
        user = tarea.user,
        tensionHorizontal = tarea.tensionHorizontal,
        cordajeHorizontal = tarea.cordajeHorizontal,
        tensionVertical = tarea.tensionVertical,
        cordajeVertical = tarea.cordajeVertical,
        dosNudos = tarea.dosNudos
    )
}

fun EncordadoDao.fromEncordadoDaoToEncordado(
    raqueta: Producto,
    user: User
): Encordado {
    return Encordado(
        id = id.value,
        raqueta = raqueta,
        user = user,
        tensionHorizontal = tensionHorizontal,
        cordajeHorizontal = cordajeHorizontal.fromProductoDaoToProducto(),
        tensionVertical = tensionVertical,
        cordajeVertical = cordajeVertical.fromProductoDaoToProducto(),
        dosNudos = dosNudos
    )
}

fun AdquisicionDao.fromAdquisicionDaoToAdquisicion(
    tareaDao: UUIDEntityClass<TareaDao>
): Adquisicion {
    val tarea = tareaDao.findById(id.value) ?: throw Exception()
    return Adquisicion(
        id = id.value,
        raqueta = tarea.raqueta.fromProductoDaoToProducto(),
        user = tarea.user.fromUserDaoToUser(),
        productoAdquirido = productoAdquirido.fromProductoDaoToProducto(),
        precio = precio
    )
}

suspend fun AdquisicionDao.fromAdquisicionDaoToAdquisicion(): Adquisicion {
    val tarea = AdquisicionRepositoryImpl(AdquisicionDao, TareaDao, ProductoDao)
        .findById(id.value).await() ?: throw MapperException()
    return Adquisicion(
        id = id.value,
        raqueta = tarea.raqueta,
        user = tarea.user,
        productoAdquirido = tarea.productoAdquirido,
        precio = tarea.precio
    )
}

fun AdquisicionDao.fromAdquisicionDaoToAdquisicion(
    raqueta: Producto,
    user: User
): Adquisicion {
    return Adquisicion(
        id = id.value,
        raqueta = raqueta,
        user = user,
        productoAdquirido = productoAdquirido.fromProductoDaoToProducto(),
        precio = precio
    )
}


class TareaMapper:BaseMapper<Tarea, TareaDTO>() {
    override fun fromDTO(item: TareaDTO): Tarea {
        return when (item) {
            is PersonalizacionDTO -> fromPersonalizacionDTO(item)
            is EncordadoDTO -> fromEncordadoDTO(item)
            is AdquisicionDTO -> fromAdquisicionDTO(item)
            else -> throw Exception()
        }
    }

    fun fromAdquisicionDTO(item: AdquisicionDTO): Adquisicion {
        return Adquisicion(
            id = item.id,
            raqueta = item.raqueta,
            user = item.user,
            productoAdquirido = item.productoAdquirido,
            precio = item.precio
        )
    }

    fun fromEncordadoDTO(item: EncordadoDTO): Encordado {
        return Encordado(
            id = item.id,
            raqueta = item.raqueta,
            user = item.user,
            tensionHorizontal = item.tensionHorizontal,
            cordajeHorizontal = item.cordajeHorizontal,
            tensionVertical = item.tensionVertical,
            cordajeVertical = item.cordajeVertical,
            dosNudos = item.dosNudos
        )
    }

    fun fromPersonalizacionDTO(item: PersonalizacionDTO): Personalizacion {
        return Personalizacion(
            id = item.id,
            raqueta = item.raqueta,
            user = item.user,
            peso = item.peso,
            balance = item.balance,
            rigidez = item.rigidez
        )
    }

    override fun toDTO(item: Tarea): TareaDTO {
        return when (item) {
            is Personalizacion -> toPersonalizacionDTO(item)
            is Encordado -> toEncordadoDTO(item)
            is Adquisicion -> toAdquisicionDTO(item)
            else -> throw Exception()
        }
    }

    fun toAdquisicionDTO(item: Adquisicion): AdquisicionDTO {
        return AdquisicionDTO(
            id = item.id,
            raqueta = item.raqueta,
            user = item.user,
            productoAdquirido = item.productoAdquirido
        )
    }

    fun toEncordadoDTO(item: Encordado): EncordadoDTO {
        return EncordadoDTO(
            id = item.id,
            raqueta = item.raqueta,
            user = item.user,
            tensionHorizontal = item.tensionHorizontal,
            cordajeHorizontal = item.cordajeHorizontal,
            tensionVertical = item.tensionVertical,
            cordajeVertical = item.cordajeVertical,
            dosNudos = item.dosNudos
        )
    }

    fun toPersonalizacionDTO(item: Personalizacion): PersonalizacionDTO {
        return PersonalizacionDTO(
            id = item.id,
            raqueta = item.raqueta,
            user = item.user,
            peso = item.peso,
            balance = item.balance,
            rigidez = item.rigidez
        )
    }

    fun fromEncordadoDTO(items: List<EncordadoDTO>): List<Encordado> {
        return items.map { fromEncordadoDTO(it) }
    }
    fun toEncordadoDTO(items: List<Encordado>): List<EncordadoDTO> {
        return items.map { toEncordadoDTO(it) }
    }

    fun fromPersonalizacionDTO(items: List<PersonalizacionDTO>): List<Personalizacion> {
        return items.map { fromPersonalizacionDTO(it) }
    }
    fun toPersonalizacionDTO(items: List<Personalizacion>): List<PersonalizacionDTO> {
        return items.map { toPersonalizacionDTO(it) }
    }

    fun fromAdquisicionDTO(items: List<AdquisicionDTO>): List<Adquisicion> {
        return items.map { fromAdquisicionDTO(it) }
    }
    fun toAdquisicionDTO(items: List<Adquisicion>): List<AdquisicionDTO> {
        return items.map { toAdquisicionDTO(it) }
    }
}
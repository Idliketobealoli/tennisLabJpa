package repositories

import db.HibernateManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import models.Personalizacion
import java.util.*
import javax.persistence.TypedQuery

class PersonalizacionRepository: ICRUDRepository<Personalizacion, UUID> {
    override suspend fun readAll(): List<Personalizacion> = withContext(Dispatchers.IO) {
        var result = mutableListOf<Personalizacion>()
        HibernateManager.transaction {
            val query: TypedQuery<Personalizacion> = HibernateManager.manager.createNamedQuery("Personalizacion.findAll", Personalizacion::class.java)
            result = query.resultList
        }
        result
    }

    override suspend fun findById(id: UUID): Personalizacion? = withContext(Dispatchers.IO) {
        var result: Personalizacion? = null
        HibernateManager.transaction {
            result = HibernateManager.manager.find(Personalizacion::class.java, id)
        }
        result
    }

    override suspend fun create(entity: Personalizacion): Personalizacion = withContext(Dispatchers.IO) {
        HibernateManager.transaction {
            HibernateManager.manager.merge(entity)
        }
        entity
    }

    override suspend fun delete(entity: Personalizacion): Boolean = withContext(Dispatchers.IO) {
        var result = false
        HibernateManager.transaction {
            val personalizacion = HibernateManager.manager.find(Personalizacion::class.java, entity.id)
            personalizacion?.let {
                HibernateManager.manager.remove(it)
                result = true
            }
        }
        result
    }
}
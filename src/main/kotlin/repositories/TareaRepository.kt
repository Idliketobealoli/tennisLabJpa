package repositories

import db.HibernateManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import models.Tarea
import java.util.*
import javax.persistence.TypedQuery

class TareaRepository: ICRUDRepository<Tarea, UUID> {
    override suspend fun readAll(): List<Tarea> = withContext(Dispatchers.IO) {
        var result = mutableListOf<Tarea>()
        HibernateManager.transaction {
            val query: TypedQuery<Tarea> = HibernateManager.manager.createNamedQuery("Tarea.findAll", Tarea::class.java)
            result = query.resultList
        }
        result
    }

    override suspend fun findById(id: UUID): Tarea? = withContext(Dispatchers.IO) {
        var result: Tarea? = null
        HibernateManager.transaction {
            result = HibernateManager.manager.find(Tarea::class.java, id)
        }
        result
    }

    override suspend fun create(entity: Tarea): Tarea = withContext(Dispatchers.IO) {
        HibernateManager.transaction {
            HibernateManager.manager.merge(entity)
        }
        entity
    }

    override suspend fun delete(entity: Tarea): Boolean = withContext(Dispatchers.IO) {
        var result = false
        HibernateManager.transaction {
            val tarea = HibernateManager.manager.find(Tarea::class.java, entity.id)
            tarea?.let {
                HibernateManager.manager.remove(it)
                result = true
            }
        }
        result
    }
}
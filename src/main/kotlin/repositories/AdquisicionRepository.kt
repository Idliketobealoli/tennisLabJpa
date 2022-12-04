package repositories

import db.HibernateManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import models.Adquisicion
import java.util.*
import javax.persistence.TypedQuery

class AdquisicionRepository: ICRUDRepository<Adquisicion, UUID> {
    override suspend fun readAll(): List<Adquisicion> = withContext(Dispatchers.IO) {
        var result = mutableListOf<Adquisicion>()
        HibernateManager.transaction {
            val query: TypedQuery<Adquisicion> = HibernateManager.manager.createNamedQuery("Adquisicion.findAll", Adquisicion::class.java)
            result = query.resultList
        }
        result
    }

    override suspend fun findById(id: UUID): Adquisicion? = withContext(Dispatchers.IO) {
        var result: Adquisicion? = null
        HibernateManager.transaction {
            result = HibernateManager.manager.find(Adquisicion::class.java, id)
        }
        result
    }

    override suspend fun create(entity: Adquisicion): Adquisicion = withContext(Dispatchers.IO) {
        HibernateManager.transaction {
            HibernateManager.manager.merge(entity)
        }
        entity
    }

    override suspend fun delete(entity: Adquisicion): Boolean = withContext(Dispatchers.IO) {
        var result = false
        HibernateManager.transaction {
            val adquisicion = HibernateManager.manager.find(Adquisicion::class.java, entity.id)
            adquisicion?.let {
                HibernateManager.manager.remove(it)
                result = true
            }
        }
        result
    }
}
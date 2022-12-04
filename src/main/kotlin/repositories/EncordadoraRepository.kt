package repositories

import db.HibernateManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import models.Encordadora
import java.util.*
import javax.persistence.TypedQuery

class EncordadoraRepository: ICRUDRepository<Encordadora, UUID> {
    override suspend fun readAll(): List<Encordadora> = withContext(Dispatchers.IO) {
        var result = mutableListOf<Encordadora>()
        HibernateManager.transaction {
            val query: TypedQuery<Encordadora> = HibernateManager.manager.createNamedQuery("Encordadora.findAll", Encordadora::class.java)
            result = query.resultList
        }
        result
    }

    override suspend fun findById(id: UUID): Encordadora? = withContext(Dispatchers.IO) {
        var result: Encordadora? = null
        HibernateManager.transaction {
            result = HibernateManager.manager.find(Encordadora::class.java, id)
        }
        result
    }

    override suspend fun create(entity: Encordadora): Encordadora = withContext(Dispatchers.IO) {
        HibernateManager.transaction {
            HibernateManager.manager.merge(entity)
        }
        entity
    }

    override suspend fun delete(entity: Encordadora): Boolean = withContext(Dispatchers.IO) {
        var result = false
        HibernateManager.transaction {
            val encordadora = HibernateManager.manager.find(Encordadora::class.java, entity.id)
            encordadora?.let {
                HibernateManager.manager.remove(it)
                result = true
            }
        }
        result
    }
}
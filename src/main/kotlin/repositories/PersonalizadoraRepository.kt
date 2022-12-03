package repositories

import db.HibernateManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import models.Personalizadora
import java.util.*
import javax.persistence.TypedQuery

class PersonalizadoraRepository: ICRUDRepository<Personalizadora, UUID> {
    override suspend fun readAll(): List<Personalizadora> = withContext(Dispatchers.IO) {
        var result = mutableListOf<Personalizadora>()
        HibernateManager.transaction {
            val query: TypedQuery<Personalizadora> = HibernateManager.manager.createNamedQuery("Personalizadora.findAll", Personalizadora::class.java)
            result = query.resultList
        }
        result
    }

    override suspend fun findById(id: UUID): Personalizadora? = withContext(Dispatchers.IO) {
        var result: Personalizadora? = null
        HibernateManager.transaction {
            result = HibernateManager.manager.find(Personalizadora::class.java, id)
        }
        result
    }

    override suspend fun create(entity: Personalizadora): Personalizadora = withContext(Dispatchers.IO) {
        HibernateManager.transaction {
            //HibernateManager.manager.merge(entity)
            ///*
            val personalizadora = HibernateManager.manager.find(Personalizadora::class.java, entity.id)
            personalizadora?.let { HibernateManager.manager.merge(entity) }
                .run { HibernateManager.manager.persist(entity) }
            //HibernateManager.manager.flush()
            // */
        }
        entity
    }

    override suspend fun delete(entity: Personalizadora): Boolean = withContext(Dispatchers.IO) {
        var result = false
        HibernateManager.transaction {
            val personalizadora = HibernateManager.manager.find(Personalizadora::class.java, entity.id)
            personalizadora?.let {
                HibernateManager.manager.remove(it)
                result = true
            }
        }
        result
    }
}
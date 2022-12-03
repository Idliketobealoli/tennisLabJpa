package repositories

import db.HibernateManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import models.Turno
import java.util.UUID
import javax.persistence.TypedQuery

class TurnoRepository: ICRUDRepository<Turno, UUID> {
    override suspend fun readAll(): List<Turno> = withContext(Dispatchers.IO) {
        var result = mutableListOf<Turno>()
        HibernateManager.transaction {
            val query: TypedQuery<Turno> = HibernateManager.manager.createNamedQuery("Turno.findAll", Turno::class.java)
            result = query.resultList
        }
        result
    }

    override suspend fun findById(id: UUID): Turno? = withContext(Dispatchers.IO) {
        var result: Turno? = null
        HibernateManager.transaction {
            result = HibernateManager.manager.find(Turno::class.java, id)
        }
        result
    }

    override suspend fun create(entity: Turno): Turno = withContext(Dispatchers.IO) {
        HibernateManager.transaction {
            //HibernateManager.manager.merge(entity)
            ///*
            val turno = HibernateManager.manager.find(Turno::class.java, entity.id)
            turno?.let { HibernateManager.manager.merge(entity) }
                .run { HibernateManager.manager.persist(entity) }
            // */
            //HibernateManager.manager.flush()
        }
        entity
    }

    override suspend fun delete(entity: Turno): Boolean = withContext(Dispatchers.IO) {
        var result = false
        HibernateManager.transaction {
            val turno = HibernateManager.manager.find(Turno::class.java, entity.id)
            turno?.let {
                HibernateManager.manager.remove(it)
                result = true
            }
        }
        result
    }
}
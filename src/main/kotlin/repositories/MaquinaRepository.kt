package repositories

import db.HibernateManager
import db.HibernateManager.manager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import models.Maquina
import java.util.*
import javax.persistence.TypedQuery

class MaquinaRepository: ICRUDRepository<Maquina, UUID> {
    override suspend fun readAll(): List<Maquina> = withContext(Dispatchers.IO) {
        var result = mutableListOf<Maquina>()
        HibernateManager.transaction {
            val query: TypedQuery<Maquina> = manager.createNamedQuery("Maquina.findAll", Maquina::class.java)
            result = query.resultList
        }
        result
    }

    override suspend fun findById(id: UUID): Maquina? = withContext(Dispatchers.IO) {
        var result: Maquina? = null
        HibernateManager.transaction {
            result = manager.find(Maquina::class.java, id)
        }
        result
    }

    override suspend fun create(entity: Maquina): Maquina = withContext(Dispatchers.IO) {
        HibernateManager.transaction {
            manager.merge(entity)
        }
        entity
    }

    override suspend fun delete(entity: Maquina): Boolean = withContext(Dispatchers.IO) {
        var result = false
        HibernateManager.transaction {
            val maquina = manager.find(Maquina::class.java, entity.id)
            maquina?.let {
                manager.remove(it)
                result = true
            }
        }
        result
    }
}
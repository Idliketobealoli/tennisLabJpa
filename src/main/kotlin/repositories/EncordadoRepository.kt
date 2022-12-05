package repositories

import db.HibernateManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import models.Encordado
import java.util.*
import javax.persistence.TypedQuery

/**
 * @author Ivan Azagra Troya
 *
 * Clase encargada de hacer las operaciones CRUD en la base de datos.
 * Implementa ICRUDRepository.
 */
class EncordadoRepository: ICRUDRepository<Encordado, UUID> {
    override suspend fun readAll(): List<Encordado> = withContext(Dispatchers.IO) {
        var result = mutableListOf<Encordado>()
        HibernateManager.transaction {
            val query: TypedQuery<Encordado> = HibernateManager.manager.createNamedQuery("Encordado.findAll", Encordado::class.java)
            result = query.resultList
        }
        result
    }

    override suspend fun findById(id: UUID): Encordado? = withContext(Dispatchers.IO) {
        var result: Encordado? = null
        HibernateManager.transaction {
            result = HibernateManager.manager.find(Encordado::class.java, id)
        }
        result
    }

    override suspend fun create(entity: Encordado): Encordado = withContext(Dispatchers.IO) {
        HibernateManager.transaction {
            HibernateManager.manager.merge(entity)
        }
        entity
    }

    override suspend fun delete(entity: Encordado): Boolean = withContext(Dispatchers.IO) {
        var result = false
        HibernateManager.transaction {
            val encordado = HibernateManager.manager.find(Encordado::class.java, entity.id)
            encordado?.let {
                HibernateManager.manager.remove(it)
                result = true
            }
        }
        result
    }
}
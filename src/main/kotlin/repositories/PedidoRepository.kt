package repositories

import db.HibernateManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import models.Pedido
import java.util.*
import javax.persistence.TypedQuery

/**
 * @author Daniel Rodriguez Muñoz
 *
 * Clase encargada de hacer las operaciones CRUD en la base de datos.
 * Implementa ICRUDRepository.
 */
class PedidoRepository: ICRUDRepository<Pedido, UUID> {
    override suspend fun readAll(): List<Pedido> = withContext(Dispatchers.IO) {
        var result = mutableListOf<Pedido>()
        HibernateManager.transaction {
            val query: TypedQuery<Pedido> = HibernateManager.manager.createNamedQuery("Pedido.findAll", Pedido::class.java)
            result = query.resultList
        }
        result
    }

    override suspend fun findById(id: UUID): Pedido? = withContext(Dispatchers.IO) {
        var result: Pedido? = null
        HibernateManager.transaction {
            result = HibernateManager.manager.find(Pedido::class.java, id)
        }
        result
    }

    override suspend fun create(entity: Pedido): Pedido = withContext(Dispatchers.IO) {
        HibernateManager.transaction {
            HibernateManager.manager.merge(entity)
        }
        entity
    }

    override suspend fun delete(entity: Pedido): Boolean = withContext(Dispatchers.IO) {
        var result = false
        HibernateManager.transaction {
            val pedido = HibernateManager.manager.find(Pedido::class.java, entity.id)
            pedido?.let {
                HibernateManager.manager.remove(it)
                result = true
            }
        }
        result
    }
}
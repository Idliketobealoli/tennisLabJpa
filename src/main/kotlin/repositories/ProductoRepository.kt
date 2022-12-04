package repositories

import db.HibernateManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import models.Producto
import java.util.*
import javax.persistence.TypedQuery

class ProductoRepository: ICRUDRepository<Producto, UUID> {
    override suspend fun readAll(): List<Producto> = withContext(Dispatchers.IO) {
        var result = mutableListOf<Producto>()
        HibernateManager.transaction {
            val query: TypedQuery<Producto> = HibernateManager.manager.createNamedQuery("Producto.findAll", Producto::class.java)
            result = query.resultList
        }
        result
    }

    override suspend fun findById(id: UUID): Producto? = withContext(Dispatchers.IO) {
        var result: Producto? = null
        HibernateManager.transaction {
            result = HibernateManager.manager.find(Producto::class.java, id)
        }
        result
    }

    override suspend fun create(entity: Producto): Producto = withContext(Dispatchers.IO) {
        HibernateManager.transaction {
            HibernateManager.manager.merge(entity)
        }
        entity
    }

    override suspend fun delete(entity: Producto): Boolean = withContext(Dispatchers.IO) {
        var result = false
        HibernateManager.transaction {
            val producto = HibernateManager.manager.find(Producto::class.java, entity.id)
            producto?.let {
                HibernateManager.manager.remove(it)
                result = true
            }
        }
        result
    }
}
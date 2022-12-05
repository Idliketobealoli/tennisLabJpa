package repositories

import db.HibernateManager
import db.HibernateManager.manager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import models.User
import models.enums.Profile
import java.util.UUID
import javax.persistence.TypedQuery

/**
 * @author Daniel Rodriguez Muñoz
 *
 * Clase encargada de hacer las operaciones CRUD en la base de datos.
 * Implementa ICRUDRepository.
 */
class UserRepository: ICRUDRepository<User, UUID> {
    override suspend fun readAll(): List<User> = withContext(Dispatchers.IO) {
        var result = mutableListOf<User>()
        HibernateManager.transaction {
            val query: TypedQuery<User> = manager.createNamedQuery("User.findAll", User::class.java)
            result = query.resultList
        }
        result
    }

    suspend fun findByPerfil(profile: Profile): List<User> = withContext(Dispatchers.IO) {
        var result = mutableListOf<User>()
        HibernateManager.transaction {
            val query: TypedQuery<User> = manager.createQuery(
                "select u from User u where u.perfil = :profile", User::class.java)
            query.setParameter("profile", profile)
            result = query.resultList
        }
        result
    }

    override suspend fun findById(id: UUID): User? = withContext(Dispatchers.IO) {
        var result:User? = null
        HibernateManager.transaction {
            result = manager.find(User::class.java, id)
        }
        result
    }

    suspend fun findByEmail(email: String): User? = withContext(Dispatchers.IO) {
        var result = mutableListOf<User>()
        HibernateManager.transaction {
            val query: TypedQuery<User> = manager.createQuery(
                "select u from User u where u.email = :mail", User::class.java)
            query.setParameter("mail", email)
            result = query.resultList
        }
        result.firstOrNull()
    }

    suspend fun findByPhone(phone: String): User? = withContext(Dispatchers.IO) {
        var result = mutableListOf<User>()
        HibernateManager.transaction {
            val query: TypedQuery<User> = manager.createQuery(
                "select u from User u where u.telefono = :phone", User::class.java)
            query.setParameter("phone", phone)
            result = query.resultList
        }
        result.firstOrNull()
    }

    override suspend fun create(entity: User): User = withContext(Dispatchers.IO) {
        HibernateManager.transaction {
            manager.merge(entity)
        }
        entity
    }

    override suspend fun delete(entity: User): Boolean = withContext(Dispatchers.IO) {
        var result = false
        HibernateManager.transaction {
            val user = manager.find(User::class.java, entity.id)
            user?.let {
                manager.remove(it)
                result = true
            }
        }
        result
    }
}
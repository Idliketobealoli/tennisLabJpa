package services

import repositories.ICRUDRepository

/**
 * @author Daniel Rodriguez Muñoz
 *
 * Clase abstracta que actua como base de los servicios.
 */
abstract class BaseService<T, ID, R : ICRUDRepository<T, ID>>(rep: R) {
    val repository = rep

    suspend fun findAll(): List<T> {
        return repository.readAll()
    }

    suspend fun findById(id: ID): T? {
        return repository.findById(id)
    }

    suspend fun insert(t: T): T {
        return repository.create(t)
    }

    suspend fun delete(t: T): Boolean {
        return repository.delete(t)
    }
}
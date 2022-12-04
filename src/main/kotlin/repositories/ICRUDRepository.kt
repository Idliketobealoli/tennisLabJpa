package repositories

interface ICRUDRepository<T, ID> {
    suspend fun readAll(): List<T>
    suspend fun findById(id: ID): T?
    suspend fun create(entity: T): T
    suspend fun delete(entity: T): Boolean
}
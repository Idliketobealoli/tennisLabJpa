package repositories

/**
 * @author Daniel Rodriguez Muñoz
 *
 * Interfaz basica de los repositorios que sirve como contrato
 * de que los repositorios deberan tener por lo menos las operaciones
 * CRUD basicas para su modelo y tipo de ID.
 */
interface ICRUDRepository<T, ID> {
    suspend fun readAll(): List<T>
    suspend fun findById(id: ID): T?
    suspend fun create(entity: T): T
    suspend fun delete(entity: T): Boolean
}
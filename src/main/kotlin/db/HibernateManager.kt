package db

import mu.KotlinLogging
import java.io.Closeable
import java.sql.SQLException
import javax.persistence.EntityManager
import javax.persistence.EntityTransaction
import javax.persistence.Persistence

/**
 * @author Ivan Azagra Troya
 *
 * Controlador de la Base de Datos.
 * Puede abrir y cerrar conexiones con la misma, asi como iniciar, commitear y
 * hacer rollbacks de las transacciones.
 */
val logger = KotlinLogging.logger {  }
object HibernateManager: Closeable {
    private var entityManagerFactory = Persistence.createEntityManagerFactory("default")
    var manager: EntityManager
    var transaction: EntityTransaction

    init {
        manager = entityManagerFactory.createEntityManager()
        transaction = manager.transaction
    }

    fun open() {
        logger.debug { "Starting EntityManagerFactory" }
        manager = entityManagerFactory.createEntityManager()
        transaction = manager.transaction
    }

    override fun close() {
        logger.debug { "Closing EntityManager" }
        manager.close()
    }

    fun query(operations: () -> Unit) {
        open()
        try {
            operations()
        } catch (e: SQLException) {
            logger.error("Error at query: ${e.message}")
        } finally {
            close()
        }
    }

    fun transaction(operations: () -> Unit) {
        open()
        try {
            logger.debug { "Initiating transaction" }
            transaction.begin()
            operations()
            transaction.commit()
            logger.debug { "Ended transaction"}
        } catch (e: SQLException) {
            transaction.rollback()
            logger.error("Error at transaction: ${e.message}")
        } finally {
            close()
        }
    }
}
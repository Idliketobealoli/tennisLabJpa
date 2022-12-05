package repositories

import config.ApplicationProperties
import db.HibernateManager
import kotlinx.coroutines.runBlocking
import models.User
import models.enums.Profile
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import java.util.*

/**
 * @author Daniel Rodriguez Muñoz
 *
 * Clase de testeo de integracion de UserRepository
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryTest {
    private val repository = UserRepository()

    val admin = User(
        id = UUID.fromString("93a98d69-0000-1111-0000-05b596ea83ba"),
        nombre = "loli",
        apellido = "test",
        telefono = "123456789",
        email = "loli@test.com",
        password = "lolitest",
        perfil = Profile.ADMIN.name)

    /**
     * Inicializacion de la base de datos para testing.
     */
    companion object {
        @JvmStatic
        @BeforeAll
        fun initialize() {
            val properties = ApplicationProperties()
            println("Reading properties file ${properties.readProperty("app.title")}")
            HibernateManager.open()
            HibernateManager.close()
        }
    }

    @BeforeEach
    fun setUp() = runBlocking {
        repository.create(admin)
        println("set up completed.")
    }

    @AfterEach
    fun tearDown() = runBlocking {
        repository.delete(admin)
        println("teared down successfully.")
    }

    @Test
    @DisplayName("find by id")
    fun findById() = runBlocking {
        val res = repository.findById(admin.id)
        assertEquals(admin.id, res?.id)
    }

    @Test
    @DisplayName("find by email")
    fun findByMail() = runBlocking {
        val res = repository.findByEmail(admin.email)
        assertAll( {
            assertEquals(admin.id, res?.id)
            assertEquals(admin.email, res?.email)
        } )
    }

    @Test
    @DisplayName("find by phone")
    fun findByPhone() = runBlocking {
        val res = repository.findByPhone(admin.telefono)
        assertAll( {
            assertEquals(admin.id, res?.id)
            assertEquals(admin.telefono, res?.telefono)
        } )
    }

    @Test
    @DisplayName("find all")
    fun findAll() = runBlocking {
        val list = repository.readAll()
        val res = list.find { it.id == admin.id }
        assertEquals(admin.id, res?.id)
    }

    @Test
    @DisplayName("find by id inexistente")
    fun findByIdInexistente() = runBlocking {
        val res = repository.findById(UUID.fromString("93a98d69-0000-1112-0000-05b596ea83ba"))
        assertNull(res)
    }

    @Test
    @DisplayName("insert")
    fun insert() = runBlocking {
        val res = repository.create(admin)
        assertEquals(admin.id, res.id)
    }

    @Test
    @DisplayName("delete")
    fun delete() = runBlocking {
        val res1 = repository.delete(admin)
        val res2 = repository.findById(admin.id)
        assertAll( {
            assertNull(res2)
            assertTrue(res1)
        } )
    }
}
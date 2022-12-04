package repositories

import config.ApplicationProperties
import db.DataLoader
import db.HibernateManager
import kotlinx.coroutines.runBlocking
import models.Pedido
import models.User
import models.enums.PedidoEstado
import models.enums.Profile
import org.junit.jupiter.api.*
import java.time.LocalDate
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PedidoReositoryTest {
    val repository = PedidoRepository()

    val client = User(
        id = UUID.fromString("93a98d69-0006-48a7-b34f-05b596ea839a"),
        nombre = "Maria",
        apellido = "Martinez",
        telefono = "632120281",
        email = "email2@email.com",
        password = "contra",
        perfil = Profile.CLIENT.name
    )
    val pedido = Pedido(
        id = UUID.fromString("93a98d69-0010-48a7-b34f-05b596ea8acc"),
        client = client,
        state = PedidoEstado.PROCESO,
        fechaEntrada = LocalDate.parse("2013-10-10"),
        fechaProgramada = LocalDate.parse("2023-12-12"),
        fechaSalida = LocalDate.parse("2023-12-12"),
        fechaEntrega = null,
        precio = 0.0
    )

    companion object {
        @JvmStatic
        @BeforeAll
        fun initialize() = runBlocking {
            val properties = ApplicationProperties()
            println("Reading properties file ${properties.readProperty("app.title")}")
            HibernateManager.open()
            HibernateManager.close()

            val dataLoader = DataLoader()
            val userRepo = UserRepository()
            println("Cargando datos iniciales...")
            dataLoader.users().forEach { userRepo.create(it) }
        }
    }

    @BeforeEach
    fun setUp() = runBlocking {
        repository.create(pedido)
        println("set up completed.")
    }

    @AfterEach
    fun tearDown() = runBlocking {
        repository.delete(pedido)
        println("teared down successfully.")
    }

    @Test
    @DisplayName("find by id")
    fun findById() = runBlocking {
        val res = repository.findById(pedido.id)
        Assertions.assertEquals(pedido.id, res?.id)
    }

    @Test
    @DisplayName("find all")
    fun findAll() = runBlocking {
        val list = repository.readAll()
        val res = list.find { it.id == pedido.id }
        Assertions.assertEquals(pedido.id, res?.id)
    }

    @Test
    @DisplayName("find by id inexistente")
    fun findByIdInexistente() = runBlocking {
        val res = repository.findById(UUID.fromString("93a98d69-0000-1112-0000-05b596ea83ba"))
        Assertions.assertNull(res)
    }

    @Test
    @DisplayName("insert")
    fun insert() = runBlocking {
        val res = repository.create(pedido)
        Assertions.assertEquals(pedido.id, res.id)
    }

    @Test
    @DisplayName("delete")
    fun delete() = runBlocking {
        val res1 = repository.delete(pedido)
        val res2 = repository.findById(pedido.id)
        assertAll( {
            Assertions.assertNull(res2)
            Assertions.assertTrue(res1)
        } )
    }
}
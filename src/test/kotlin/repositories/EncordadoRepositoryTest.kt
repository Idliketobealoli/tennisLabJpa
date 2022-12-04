package repositories

import config.ApplicationProperties
import db.DataLoader
import db.HibernateManager
import kotlinx.coroutines.runBlocking
import models.Encordado
import models.Pedido
import models.Producto
import models.User
import models.enums.PedidoEstado
import models.enums.Profile
import models.enums.TipoProducto
import org.junit.jupiter.api.*
import java.time.LocalDate
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EncordadoRepositoryTest {
    val repository = EncordadoRepository()

    val raqueta = Producto(
        id = UUID.fromString("93a98d69-0001-48a7-b34f-05b596ea83ba"),
        tipoProducto = TipoProducto.RAQUETAS,
        marca = "MarcaRaqueta",
        modelo = "ModeloRaqueta",
        precio = 150.5,
        stock = 3
    )
    val cordaje = Producto(
        id = UUID.fromString("93a98d69-0005-48a7-b34f-05b596ea839b"),
        tipoProducto = TipoProducto.CORDAJES,
        marca = "CordajEx",
        modelo = "C945-Alpha",
        precio = 53.1,
        stock = 4
    )
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
    val tarea = Encordado(
        id = UUID.fromString("93a98d69-0014-48a7-b34f-05b596ea8aaa"),
        raqueta = raqueta,
        user = client,
        tensionHorizontal = 25.5,
        cordajeHorizontal = cordaje,
        tensionVertical = 23.1,
        cordajeVertical = cordaje,
        dosNudos = true,
        pedido = pedido
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
            val pedidoRepo = PedidoRepository()
            val userRepo = UserRepository()
            val productoRepo = ProductoRepository()
            println("Cargando datos iniciales...")
            dataLoader.users().forEach { userRepo.create(it) }
            dataLoader.productos().forEach { productoRepo.create(it) }
            dataLoader.pedidos().forEach { pedidoRepo.create(it) }
        }
    }

    @BeforeEach
    fun setUp() = runBlocking {
        repository.create(tarea)
        println("set up completed.")
    }

    @AfterEach
    fun tearDown() = runBlocking {
        repository.delete(tarea)
        println("teared down successfully.")
    }

    @Test
    @DisplayName("find by id")
    fun findById() = runBlocking {
        val res = repository.findById(tarea.id)
        Assertions.assertEquals(tarea.id, res?.id)
    }

    @Test
    @DisplayName("find all")
    fun findAll() = runBlocking {
        val list = repository.readAll()
        val res = list.find { it.id == tarea.id }
        Assertions.assertEquals(tarea.id, res?.id)
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
        val res = repository.create(tarea)
        Assertions.assertEquals(tarea.id, res.id)
    }

    @Test
    @DisplayName("delete")
    fun delete() = runBlocking {
        val res1 = repository.delete(tarea)
        val res2 = repository.findById(tarea.id)
        assertAll( {
            Assertions.assertNull(res2)
            Assertions.assertTrue(res1)
        } )
    }
}
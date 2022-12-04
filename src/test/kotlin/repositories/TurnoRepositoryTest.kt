package repositories

import config.ApplicationProperties
import db.DataLoader
import db.HibernateManager
import kotlinx.coroutines.runBlocking
import models.*
import models.enums.PedidoEstado
import models.enums.Profile
import models.enums.TipoProducto
import org.junit.jupiter.api.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TurnoRepositoryTest {
    private val repository = TurnoRepository()

    val client = User(
        id = UUID.fromString("93a98d69-0006-48a7-b34f-05b596ea839a"),
        nombre = "Maria",
        apellido = "Martinez",
        telefono = "632120281",
        email = "email2@email.com",
        password = "contra",
        perfil = Profile.CLIENT.name
    )
    val worker = User(
        id = UUID.fromString("93a98d69-0007-48a7-b34f-05b596ea839c"),
        nombre = "Luis",
        apellido = "Martinez",
        telefono = "632950281",
        email = "email@email.com",
        password = "estacontraseñanoestaensha512",
        perfil = Profile.WORKER.name
    )
    val raqueta = Producto(
        id = UUID.fromString("93a98d69-0001-48a7-b34f-05b596ea83ba"),
        tipoProducto = TipoProducto.RAQUETAS,
        marca = "MarcaRaqueta",
        modelo = "ModeloRaqueta",
        precio = 150.5,
        stock = 3
    )
    val producto1 = Producto(
        id = UUID.fromString("93a98d69-0002-48a7-b34f-05b596ea83ac"),
        tipoProducto = TipoProducto.ANTIVIBRADORES,
        marca = "MarcaX",
        modelo = "ModeloX",
        precio = 10.5,
        stock = 5
    )
    val personalizadora1 = Personalizadora(
        id = UUID.fromString("93a98d69-0008-48a7-b34f-05b596ea83bb"),
        modelo = "RTX-3080TI",
        marca = "Nvidia",
        fechaAdquisicion = LocalDate.parse("2022-11-10"),
        numeroSerie = "123456789X",
        measuresRigidity = false,
        measuresBalance = true,
        measuresManeuverability = true
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
    val adquisicion1 = Adquisicion(
        id = UUID.fromString("93a98d69-0011-48a7-b34f-05b596ea83ca"),
        raqueta = raqueta,
        user = client,
        productoAdquirido = producto1,
        pedido = pedido,
        precio = producto1.precio
    )
    val personalizacion = Personalizacion(
        id = UUID.fromString("93a98d69-0015-48a7-b34f-05b596ea8aab"),
        raqueta = raqueta,
        user = client,
        peso = 890,
        balance = 15.4,
        rigidez = 4,
        pedido = pedido
    )
    val turno = Turno(
        id = UUID.fromString("93a98d69-0019-48a7-b34f-05b596ea8abc"),
        worker = worker,
        maquina = personalizadora1,
        horaInicio = LocalDateTime.of(2002,10,14,10,9),
        horaFin = null,
        tarea1 = personalizacion,
        tarea2 = adquisicion1,
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
            val maquinaRepo = MaquinaRepository()
            val tareaRepo = TareaRepository()
            println("Cargando datos iniciales...")
            dataLoader.users().forEach { userRepo.create(it) }
            dataLoader.productos().forEach { productoRepo.create(it) }
            dataLoader.pedidos().forEach { pedidoRepo.create(it) }
            dataLoader.maquinas().forEach { maquinaRepo.create(it) }
            dataLoader.tareas().forEach { tareaRepo.create(it) }
        }
    }

    @BeforeEach
    fun setUp() = runBlocking {
        repository.create(turno)
        println("set up completed.")
    }

    @AfterEach
    fun tearDown() = runBlocking {
        repository.delete(turno)
        println("teared down successfully.")
    }

    @Test
    @DisplayName("find by id")
    fun findById() = runBlocking {
        val res = repository.findById(turno.id)
        Assertions.assertEquals(turno.id, res?.id)
    }

    @Test
    @DisplayName("find all")
    fun findAll() = runBlocking {
        val list = repository.readAll()
        val res = list.find { it.id == turno.id }
        Assertions.assertEquals(turno.id, res?.id)
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
        val res = repository.create(turno)
        Assertions.assertEquals(turno.id, res.id)
    }

    @Test
    @DisplayName("delete")
    fun delete() = runBlocking {
        val res1 = repository.delete(turno)
        val res2 = repository.findById(turno.id)
        assertAll( {
            Assertions.assertNull(res2)
            Assertions.assertTrue(res1)
        } )
    }
}
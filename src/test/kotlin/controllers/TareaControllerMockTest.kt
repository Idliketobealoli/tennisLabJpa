package controllers

import dto.AdquisicionDTO
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import models.Pedido
import models.Producto
import models.User
import models.enums.PedidoEstado
import models.enums.Profile
import models.enums.TipoProducto
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import services.AdquisicionService
import services.EncordadoService
import services.PersonalizacionService
import java.time.LocalDate
import java.util.*

/**
 * @author Daniel Rodriguez Muñoz
 *
 * Clase de testeo unitario de TareaController con MockK.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
class TareaControllerMockTest {
    private val aServiceMock: AdquisicionService = mockk()
    private val eServiceMock: EncordadoService = mockk()
    private val pServiceMock: PersonalizacionService = mockk()
    private var controller = TareaController

    val raqueta = Producto(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea83ba"),
        tipoProducto = TipoProducto.RAQUETAS,
        marca = "MarcaRaqueta",
        modelo = "ModeloRaqueta",
        precio = 150.5,
        stock = 3)
    val producto1 = Producto(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea83ac"),
        tipoProducto = TipoProducto.ANTIVIBRADORES,
        marca = "MarcaX",
        modelo = "ModeloX",
        precio = 10.5,
        stock = 5)
    val client = User(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea839a"),
        nombre = "Maria",
        apellido = "Martinez",
        telefono = "632120281",
        email = "email2@email.com",
        password = "contra",
        perfil = Profile.CLIENT.name)
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
    val tarea = AdquisicionDTO(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea83ca"),
        raqueta = raqueta,
        user = client,
        productoAdquirido = producto1,
        pedido = pedido
    )
    val tareaJson = """
        {
          "id": "93a98d69-6da6-48a7-b34f-05b596ea83ca",
          "raqueta": {},
          "user": {},
          "pedido": {},
          "productoAdquirido": {},
          "precio": 10.5
        }
    """.trimIndent()

    init {
        MockKAnnotations.init(this)
        controller.aService = aServiceMock
        controller.eService = eServiceMock
        controller.pService = pServiceMock
    }

    @Test
    @DisplayName("get all")
    fun findAll() = runBlocking {
        coEvery { aServiceMock.getAllAdquisiciones() } returns listOf(tarea)
        coEvery { eServiceMock.getAllEncordados() } returns listOf()
        coEvery { pServiceMock.getAllPersonalizaciones() } returns listOf()

        val res = runBlocking { controller.findAllTareas() }
        assertEquals("[$tareaJson]", res)
    }

    @Test
    @DisplayName("get all void")
    fun findAllNE() = runBlocking {
        coEvery { aServiceMock.getAllAdquisiciones() } returns listOf()
        coEvery { eServiceMock.getAllEncordados() } returns listOf()
        coEvery { pServiceMock.getAllPersonalizaciones() } returns listOf()

        val res = runBlocking { controller.findAllTareas() }
        assertEquals("[]", res)
    }

    @Test
    @DisplayName("get by id nonexistent")
    fun findByIdNE() = runBlocking {
        coEvery { aServiceMock.getAdquisicionById(tarea.id) } returns null
        coEvery { eServiceMock.getEncordadoById(tarea.id) } returns null
        coEvery { pServiceMock.getPersonalizacionById(tarea.id) } returns null

        val res = runBlocking { controller.getTareaById(tarea.id) }
        assertEquals("Tarea with id ${tarea.id} not found.", res)
    }
}
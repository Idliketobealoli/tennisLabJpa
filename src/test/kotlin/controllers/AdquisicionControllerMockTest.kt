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
import java.time.LocalDate
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
class AdquisicionControllerMockTest {
    private var controller = AdquisicionController
    private val serviceMock: AdquisicionService = mockk()

    val producto = Producto(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea83aa"),
        tipoProducto = TipoProducto.FUNDAS,
        marca = "MarcaZ",
        modelo = "ModeloZ",
        precio = 36.4,
        stock = 8
    )
    val raqueta = Producto(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea83ba"),
        tipoProducto = TipoProducto.RAQUETAS,
        marca = "MarcaRaqueta",
        modelo = "ModeloRaqueta",
        precio = 150.5,
        stock = 3)
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
    val adquisicion = AdquisicionDTO(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea83ca"),
        raqueta = raqueta,
        user = client,
        productoAdquirido = producto,
        pedido = pedido
    )

    val adquisicionJson = """
        {
          "id": "93a98d69-6da6-48a7-b34f-05b596ea83ca",
          "raqueta": {},
          "user": {},
          "pedido": {},
          "productoAdquirido": {},
          "precio": 36.4
        }
    """.trimIndent()

    init {
        MockKAnnotations.init(this)
        controller.service = serviceMock
    }

    @Test
    @DisplayName("get all")
    fun findAll() = runBlocking {
        coEvery { serviceMock.getAllAdquisiciones() } returns listOf(adquisicion)

        val res = runBlocking { controller.findAllAdquisiciones() }
        assertEquals("[$adquisicionJson]", res)
    }

    @Test
    @DisplayName("get all void")
    fun findAllNE() = runBlocking {
        coEvery { serviceMock.getAllAdquisiciones() } returns listOf()

        val res = runBlocking { controller.findAllAdquisiciones() }
        assertEquals("[]", res)
    }

    @Test
    @DisplayName("get by id")
    fun findById() = runBlocking {
        coEvery { serviceMock.getAdquisicionById(adquisicion.id) } returns adquisicion

        val res = runBlocking { controller.getAdquisicionById(adquisicion.id) }
        assertEquals(adquisicionJson, res)
    }

    @Test
    @DisplayName("get by id nonexistent")
    fun findByIdNE() = runBlocking {
        coEvery { serviceMock.getAdquisicionById(adquisicion.id) } returns null

        val res = runBlocking { controller.getAdquisicionById(adquisicion.id) }
        assertEquals("Adquisicion with id ${adquisicion.id} not found.", res)
    }

    @Test
    @DisplayName("insert")
    fun insert() = runBlocking {
        coEvery { serviceMock.createAdquisicion(adquisicion) } returns adquisicion

        val res = runBlocking { controller.insertAdquisicion(adquisicion) }
        assertEquals(adquisicionJson, res)
    }

    @Test
    @DisplayName("delete correct")
    fun delete() = runBlocking {
        coEvery { serviceMock.deleteAdquisicion(adquisicion) } returns true

        val res = runBlocking { controller.deleteAdquisicion(adquisicion) }
        assertEquals(adquisicionJson, res)
    }

    @Test
    @DisplayName("delete incorrect")
    fun deleteInc() = runBlocking {
        coEvery { serviceMock.deleteAdquisicion(adquisicion) } returns false

        val res = runBlocking { controller.deleteAdquisicion(adquisicion) }
        assertEquals("Could not delete Adquisicion with id ${adquisicion.id}", res)
    }
}
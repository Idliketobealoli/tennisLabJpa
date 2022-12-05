package controllers

import dto.EncordadoDTO
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
import services.EncordadoService
import java.time.LocalDate
import java.util.*

/**
 * @author Ivan Azagra Troya
 *
 * Clase de testeo unitario de EncordadoController con MockK.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
class EncordadoControllerMockTest {
    private var controller = EncordadoController
    private val serviceMock: EncordadoService = mockk()

    val cordaje = Producto(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea839b"),
        tipoProducto = TipoProducto.CORDAJES,
        marca = "CordajEx",
        modelo = "C945-Alpha",
        precio = 53.1,
        stock = 4
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
    val encordado = EncordadoDTO(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea8aaa"),
        raqueta = raqueta,
        user = client,
        tensionHorizontal = 25.5,
        cordajeHorizontal = cordaje,
        tensionVertical = 23.1,
        cordajeVertical = cordaje,
        dosNudos = true,
        pedido = pedido
    )

    val encordadoJson = """
        {
          "id": "93a98d69-6da6-48a7-b34f-05b596ea8aaa",
          "raqueta": {},
          "user": {},
          "pedido": {},
          "tensionHorizontal": 25.5,
          "cordajeHorizontal": {},
          "tensionVertical": 23.1,
          "cordajeVertical": {},
          "dosNudos": true,
          "precio": 121.19999999999999
        }
    """.trimIndent()

    init {
        MockKAnnotations.init(this)
        controller.service = serviceMock
    }

    @Test
    @DisplayName("get all")
    fun findAll() = runBlocking {
        coEvery { serviceMock.getAllEncordados() } returns listOf(encordado)

        val res = runBlocking { controller.findAllEncordados() }
        assertEquals("[$encordadoJson]", res)
    }

    @Test
    @DisplayName("get all void")
    fun findAllNE() = runBlocking {
        coEvery { serviceMock.getAllEncordados() } returns listOf()

        val res = runBlocking { controller.findAllEncordados() }
        assertEquals("[]", res)
    }

    @Test
    @DisplayName("get by id")
    fun findById() = runBlocking {
        coEvery { serviceMock.getEncordadoById(encordado.id) } returns encordado

        val res = runBlocking { controller.getEncordadoById(encordado.id) }
        assertEquals(encordadoJson, res)
    }

    @Test
    @DisplayName("get by id nonexistent")
    fun findByIdNE() = runBlocking {
        coEvery { serviceMock.getEncordadoById(encordado.id) } returns null

        val res = runBlocking { controller.getEncordadoById(encordado.id) }
        assertEquals("Encordado with id ${encordado.id} not found.", res)
    }

    @Test
    @DisplayName("insert")
    fun insert() = runBlocking {
        coEvery { serviceMock.createEncordado(encordado) } returns encordado

        val res = runBlocking { controller.insertEncordado(encordado) }
        assertEquals(encordadoJson, res)
    }

    @Test
    @DisplayName("delete correct")
    fun delete() = runBlocking {
        coEvery { serviceMock.deleteEncordado(encordado) } returns true

        val res = runBlocking { controller.deleteEncordado(encordado) }
        assertEquals(encordadoJson, res)
    }

    @Test
    @DisplayName("delete incorrect")
    fun deleteInc() = runBlocking {
        coEvery { serviceMock.deleteEncordado(encordado) } returns false

        val res = runBlocking { controller.deleteEncordado(encordado) }
        assertEquals("Could not delete Encordado with id ${encordado.id}", res)
    }
}
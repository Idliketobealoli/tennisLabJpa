package controllers

import dto.PersonalizacionDTO
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
import services.PersonalizacionService
import java.time.LocalDate
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
class PersonalizacionControllerMockTest {
    private var controller = PersonalizacionController
    private val serviceMock: PersonalizacionService = mockk()

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
    val personalizacion = PersonalizacionDTO(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea8aab"),
        raqueta = raqueta,
        user = client,
        peso = 890,
        balance = 15.4,
        rigidez = 4,
        pedido = pedido
    )

    val personalizacionJson = """
        {
          "id": "93a98d69-6da6-48a7-b34f-05b596ea8aab",
          "raqueta": {},
          "user": {},
          "pedido": {},
          "peso": 890,
          "balance": 15.4,
          "rigidez": 4,
          "precio": 60.0
        }
    """.trimIndent()

    init {
        MockKAnnotations.init(this)
        controller.service = serviceMock
    }

    @Test
    @DisplayName("get all")
    fun findAll() = runBlocking {
        coEvery { serviceMock.getAllPersonalizaciones() } returns listOf(personalizacion)

        val res = runBlocking { controller.findAllPersonalizaciones() }
        assertEquals("[$personalizacionJson]", res)
    }

    @Test
    @DisplayName("get all void")
    fun findAllNE() = runBlocking {
        coEvery { serviceMock.getAllPersonalizaciones() } returns listOf()

        val res = runBlocking { controller.findAllPersonalizaciones() }
        assertEquals("[]", res)
    }

    @Test
    @DisplayName("get by id")
    fun findById() = runBlocking {
        coEvery { serviceMock.getPersonalizacionById(personalizacion.id) } returns personalizacion

        val res = runBlocking { controller.getPersonalizacionById(personalizacion.id) }
        assertEquals(personalizacionJson, res)
    }

    @Test
    @DisplayName("get by id nonexistent")
    fun findByIdNE() = runBlocking {
        coEvery { serviceMock.getPersonalizacionById(personalizacion.id) } returns null

        val res = runBlocking { controller.getPersonalizacionById(personalizacion.id) }
        assertEquals("Personalizacion with id ${personalizacion.id} not found.", res)
    }

    @Test
    @DisplayName("insert")
    fun insert() = runBlocking {
        coEvery { serviceMock.createPersonalizacion(personalizacion) } returns personalizacion

        val res = runBlocking { controller.insertPersonalizacion(personalizacion) }
        assertEquals(personalizacionJson, res)
    }

    @Test
    @DisplayName("delete correct")
    fun delete() = runBlocking {
        coEvery { serviceMock.deletePersonalizacion(personalizacion) } returns true

        val res = runBlocking { controller.deletePersonalizacion(personalizacion) }
        assertEquals(personalizacionJson, res)
    }

    @Test
    @DisplayName("delete incorrect")
    fun deleteInc() = runBlocking {
        coEvery { serviceMock.deletePersonalizacion(personalizacion) } returns false

        val res = runBlocking { controller.deletePersonalizacion(personalizacion) }
        assertEquals("Could not delete Personalizacion with id ${personalizacion.id}", res)
    }
}
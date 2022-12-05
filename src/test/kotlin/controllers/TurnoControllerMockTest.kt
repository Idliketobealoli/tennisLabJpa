package controllers

import dto.TurnoDTO
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import models.*
import models.enums.PedidoEstado
import models.enums.Profile
import models.enums.TipoProducto
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import services.TurnoService
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

/**
 * @author Ivan Azagra Troya
 *
 * Clase de testeo unitario de TurnoController con MockK.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
class TurnoControllerMockTest {
    private val serviceMock: TurnoService = mockk()
    private var controller = TurnoController

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
    val tarea1 = Adquisicion(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea83ca"),
        raqueta = raqueta,
        user = client,
        productoAdquirido = producto1,
        precio = producto1.precio,
        pedido = pedido
    )
    val maquina = Encordadora(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea83bc"),
        modelo = "ENC-4070Turbo",
        marca = "Genericosas Marca Registrada",
        fechaAdquisicion = LocalDate.parse("2021-10-01"),
        numeroSerie = "975318642Q",
        isManual = true,
        maxTension = 15.2,
        minTension = 5.1)
    val worker = User(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea839c"),
        nombre = "Luis",
        apellido = "Martinez",
        telefono = "632950281",
        email = "email@email.com",
        password = "estacontraseñanoestaensha512",
        perfil = Profile.WORKER.name)
    val turno = TurnoDTO(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea8abc"),
        worker = worker,
        maquina = maquina,
        horaInicio = LocalDateTime.of(2002,10,14,10,9),
        horaFin = null,
        tarea1 = tarea1,
        tarea2 = null,
        pedido = pedido
    )

    val turnoJson = """
        {
          "id": "93a98d69-6da6-48a7-b34f-05b596ea8abc",
          "worker": {},
          "maquina": {},
          "horaInicioString": "2002-10-14T10:09",
          "horaFinString": "null",
          "numPedidosActivos": 1,
          "tarea1": {
            "productoAdquirido": {}
          },
          "pedido": {}
        }
    """.trimIndent()

    init {
        MockKAnnotations.init(this)
        controller.service = serviceMock
    }

    @Test
    @DisplayName("get all")
    fun findAll() = runBlocking {
        coEvery { serviceMock.getAllTurnos() } returns listOf(turno)

        val res = runBlocking { controller.findAllTurnos() }
        assertEquals("[$turnoJson]", res)
    }

    @Test
    @DisplayName("get all void")
    fun findAllNE() = runBlocking {
        coEvery { serviceMock.getAllTurnos() } returns listOf()

        val res = runBlocking { controller.findAllTurnos() }
        assertEquals("[]", res)
    }

    @Test
    @DisplayName("get by fecha")
    fun findByFecha() = runBlocking {
        coEvery { serviceMock.getAllTurnos() } returns listOf(turno)

        val res = runBlocking { controller.findAllTurnosSortedByFecha() }
        assertEquals("[$turnoJson]", res)
    }

    @Test
    @DisplayName("get by fecha void")
    fun findByFechaNull() = runBlocking {
        coEvery { serviceMock.getAllTurnos() } returns listOf()

        val res = runBlocking { controller.findAllTurnosSortedByFecha() }
        assertEquals("[]", res)
    }

    @Test
    @DisplayName("get by id")
    fun findById() = runBlocking {
        coEvery { serviceMock.getTurnoById(turno.id) } returns turno

        val res = runBlocking { controller.getTurnoById(turno.id) }
        assertEquals(turnoJson, res)
    }

    @Test
    @DisplayName("get by id nonexistent")
    fun findByIdNE() = runBlocking {
        coEvery { serviceMock.getTurnoById(turno.id) } returns null

        val res = runBlocking { controller.getTurnoById(turno.id) }
        assertEquals("Turno with id ${turno.id} not found.", res)
    }

    @Test
    @DisplayName("insert")
    fun insert() = runBlocking {
        coEvery { serviceMock.createTurno(turno) } returns turno

        val res = runBlocking { controller.insertTurno(turno) }
        assertEquals(turnoJson, res)
    }

    @Test
    @DisplayName("delete correct")
    fun delete() = runBlocking {
        coEvery { serviceMock.deleteTurno(turno) } returns true

        val res = runBlocking { controller.deleteTurno(turno) }
        assertEquals(turnoJson, res)
    }

    @Test
    @DisplayName("delete incorrect")
    fun deleteInc() = runBlocking {
        coEvery { serviceMock.deleteTurno(turno) } returns false

        val res = runBlocking { controller.deleteTurno(turno) }
        assertEquals("Could not delete Turno with id ${turno.id}", res)
    }
}
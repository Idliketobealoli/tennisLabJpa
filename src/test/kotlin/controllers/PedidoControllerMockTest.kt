package controllers

import dto.PedidoDTO
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import mappers.UserMapper
import models.*
import models.enums.PedidoEstado
import models.enums.Profile
import models.enums.TipoProducto
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import services.PedidoService
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
class PedidoControllerMockTest {
    private val serviceMock: PedidoService = mockk()
    private var controller = PedidoController

    val client = User(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea839a"),
        nombre = "Maria",
        apellido = "Martinez",
        telefono = "632120281",
        email = "email2@email.com",
        password = "contra",
        perfil = Profile.CLIENT.name
    )
    val worker = User(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea839c"),
        nombre = "Luis",
        apellido = "Martinez",
        telefono = "632950281",
        email = "email@email.com",
        password = "estacontraseñanoestaensha512",
        perfil = Profile.WORKER.name
    )
    val maquina = Personalizadora(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea83bb"),
        modelo = "RTX-3080TI",
        marca = "Nvidia",
        fechaAdquisicion = LocalDate.parse("2022-11-10"),
        numeroSerie = "123456789X",
        measuresRigidity = false,
        measuresBalance = true,
        measuresManeuverability = true
    )
    val raqueta = Producto(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea83ba"),
        tipoProducto = TipoProducto.RAQUETAS,
        marca = "MarcaRaqueta",
        modelo = "ModeloRaqueta",
        precio = 150.5,
        stock = 3
    )
    val producto = Producto(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea83ac"),
        tipoProducto = TipoProducto.ANTIVIBRADORES,
        marca = "MarcaX",
        modelo = "ModeloX",
        precio = 10.5,
        stock = 5
    )
    val p = Pedido(
        id = UUID.fromString("93a98d69-0010-48a7-b34f-05b596ea8acc"),
        client = client,
        state = PedidoEstado.PROCESO,
        fechaEntrada = LocalDate.parse("2013-10-10"),
        fechaProgramada = LocalDate.parse("2023-12-12"),
        fechaSalida = LocalDate.parse("2023-12-12"),
        fechaEntrega = null,
        precio = 0.0
    )
    val tarea = Adquisicion(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea83ca"),
        raqueta = raqueta,
        user = client,
        productoAdquirido = producto,
        precio = producto.precio,
        pedido = p
    )
    val turno = Turno(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea8acb"),
        worker = worker,
        maquina = maquina,
        horaInicio = LocalDateTime.of(2022,5,5,5,5),
        horaFin = null,
        tarea1 = tarea,
        tarea2 = null,
        pedido = p
    )
    val pedido = PedidoDTO(
    id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea8acc"),
    tareas = listOf(tarea),
    client = client,
    turnos = listOf(turno),
    state = PedidoEstado.PROCESO,
    fechaEntrada = LocalDate.parse("2013-10-10"),
    fechaProgramada = turno.horaFin.toLocalDate() ?: LocalDate.parse("2023-12-12"),
    fechaSalida = turno.horaFin.plusDays(5L).toLocalDate() ?: LocalDate.parse("2023-12-12"),
    fechaEntrega = null
    )

    val pedidoJson = """
        {
          "id": "93a98d69-6da6-48a7-b34f-05b596ea8acc",
          "tareas": [
            {}
          ],
          "client": {},
          "state": "PROCESO",
          "fechaEntradaString": "2013-10-10",
          "fechaProgramadaString": "2022-05-05",
          "fechaSalidaString": "2022-05-10",
          "fechaEntregaString": "2022-05-10",
          "precio": 10.5
        }
    """.trimIndent()

    init {
        MockKAnnotations.init(this)
        controller.service = serviceMock
    }

    @Test
    @DisplayName("get all")
    fun findAll() = runBlocking {
        coEvery { serviceMock.getAllPedidos() } returns listOf(pedido)

        val res = runBlocking { controller.findAllPedidos() }
        assertEquals("[$pedidoJson]", res)
    }

    @Test
    @DisplayName("get all void")
    fun findAllNE() = runBlocking {
        coEvery { serviceMock.getAllPedidos() } returns listOf()

        val res = runBlocking { controller.findAllPedidos() }
        assertEquals("[]", res)
    }

    @Test
    @DisplayName("get from user")
    fun findFromUser() = runBlocking {
        coEvery { serviceMock.getAllPedidos() } returns listOf(pedido)

        val res = runBlocking { controller.findAllPedidosFromUser(UserMapper().toDTO(pedido.client)) }
        assertEquals("[$pedidoJson]", res)
    }

    @Test
    @DisplayName("get from user void")
    fun findFromUserNull() = runBlocking {
        coEvery { serviceMock.getAllPedidos() } returns listOf()

        val res = runBlocking { controller.findAllPedidosFromUser(UserMapper().toDTO(pedido.client)) }
        assertEquals("[]", res)
    }

    @Test
    @DisplayName("get with estado")
    fun findWithEstado() = runBlocking {
        coEvery { serviceMock.getAllPedidos() } returns listOf(pedido)

        val res = runBlocking { controller.findAllPedidosWithEstado(PedidoEstado.PROCESO) }
        assertEquals("[$pedidoJson]", res)
    }

    @Test
    @DisplayName("get with estado void")
    fun findWithEstadoNull() = runBlocking {
        coEvery { serviceMock.getAllPedidos() } returns listOf()

        val res = runBlocking { controller.findAllPedidosWithEstado(PedidoEstado.PROCESO) }
        assertEquals("[]", res)
    }

    @Test
    @DisplayName("get by id")
    fun findById() = runBlocking {
        coEvery { serviceMock.getPedidoById(pedido.id) } returns pedido

        val res = runBlocking { controller.getPedidoById(pedido.id) }
        assertEquals(pedidoJson, res)
    }

    @Test
    @DisplayName("get by id nonexistent")
    fun findByIdNE() = runBlocking {
        coEvery { serviceMock.getPedidoById(pedido.id) } returns null

        val res = runBlocking { controller.getPedidoById(pedido.id) }
        assertEquals("Pedido with id ${pedido.id} not found.", res)
    }

    @Test
    @DisplayName("insert")
    fun insert() = runBlocking {
        coEvery { serviceMock.createPedido(pedido) } returns pedido

        val res = runBlocking { controller.insertPedido(pedido) }
        assertEquals(pedidoJson, res)
    }

    @Test
    @DisplayName("delete correct")
    fun delete() = runBlocking {
        coEvery { serviceMock.deletePedido(pedido) } returns true

        val res = runBlocking { controller.deletePedido(pedido) }
        assertEquals(pedidoJson, res)
    }

    @Test
    @DisplayName("delete incorrect")
    fun deleteInc() = runBlocking {
        coEvery { serviceMock.deletePedido(pedido) } returns false

        val res = runBlocking { controller.deletePedido(pedido) }
        assertEquals("Could not delete Pedido with id ${pedido.id}", res)
    }
}
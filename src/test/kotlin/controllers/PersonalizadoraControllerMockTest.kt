package controllers

import dto.PersonalizadoraDTO
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import services.PersonalizadoraService
import java.time.LocalDate
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
class PersonalizadoraControllerMockTest {
    private val serviceMock: PersonalizadoraService = mockk()
    private var controller = PersonalizadoraController

    val maquina = PersonalizadoraDTO(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea83bb"),
        modelo = "RTX-3080TI",
        marca = "Nvidia",
        fechaAdquisicion = LocalDate.parse("2022-11-10"),
        numeroSerie = "123456789X",
        measuresRigidity = false,
        measuresBalance = true,
        measuresManeuverability = true
    )

    val maquinaJson = """
        {
          "id": "93a98d69-6da6-48a7-b34f-05b596ea83bb",
          "modelo": "RTX-3080TI",
          "marca": "Nvidia",
          "fechaAdquisicionString": "2022-11-10",
          "numeroSerie": "123456789X",
          "measuresManeuverability": true,
          "measuresBalance": true,
          "measuresRigidity": false
        }
    """.trimIndent()

    init {
        MockKAnnotations.init(this)
        controller.service = serviceMock
    }

    @Test
    @DisplayName("get all")
    fun findAll() = runBlocking {
        coEvery { serviceMock.getAllPersonalizadoras() } returns listOf(maquina)

        val res = runBlocking { controller.findAllPersonalizadoras() }
        assertEquals("[$maquinaJson]", res)
    }

    @Test
    @DisplayName("get all void")
    fun findAllNE() = runBlocking {
        coEvery { serviceMock.getAllPersonalizadoras() } returns listOf()

        val res = runBlocking { controller.findAllPersonalizadoras() }
        assertEquals("[]", res)
    }

    @Test
    @DisplayName("get maneuverability")
    fun findManeuverability() = runBlocking {
        coEvery { serviceMock.getAllPersonalizadoras() } returns listOf(maquina)

        val res = runBlocking { controller.findAllManeuverability(true) }
        val res2 = runBlocking { controller.findAllManeuverability(false) }
        assertAll(
            { assertEquals("[$maquinaJson]", res) },
            { assertEquals("[]", res2) }
        )
    }

    @Test
    @DisplayName("get maneuverability void")
    fun findManeuverabilityNull() = runBlocking {
        coEvery { serviceMock.getAllPersonalizadoras() } returns listOf()

        val res = runBlocking { controller.findAllManeuverability(true) }
        val res2 = runBlocking { controller.findAllManeuverability(false) }
        assertAll(
            { assertEquals("[]", res) },
            { assertEquals("[]", res2) }
        )
    }

    @Test
    @DisplayName("get balance")
    fun findBalance() = runBlocking {
        coEvery { serviceMock.getAllPersonalizadoras() } returns listOf(maquina)

        val res = runBlocking { controller.findAllBalance(true) }
        val res2 = runBlocking { controller.findAllBalance(false) }
        assertAll(
            { assertEquals("[$maquinaJson]", res) },
            { assertEquals("[]", res2) }
        )
    }

    @Test
    @DisplayName("get balance void")
    fun findBalanceNull() = runBlocking {
        coEvery { serviceMock.getAllPersonalizadoras() } returns listOf()

        val res = runBlocking { controller.findAllBalance(true) }
        val res2 = runBlocking { controller.findAllBalance(false) }
        assertAll(
            { assertEquals("[]", res) },
            { assertEquals("[]", res2) }
        )
    }

    @Test
    @DisplayName("get rigidity")
    fun findRigidity() = runBlocking {
        coEvery { serviceMock.getAllPersonalizadoras() } returns listOf(maquina)

        val res = runBlocking { controller.findAllRigidity(true) }
        val res2 = runBlocking { controller.findAllRigidity(false) }
        assertAll(
            { assertEquals("[]", res) },
            { assertEquals("[$maquinaJson]", res2) }
        )
    }

    @Test
    @DisplayName("get rigidity void")
    fun findRigidityNull() = runBlocking {
        coEvery { serviceMock.getAllPersonalizadoras() } returns listOf()

        val res = runBlocking { controller.findAllRigidity(true) }
        val res2 = runBlocking { controller.findAllRigidity(false) }
        assertAll(
            { assertEquals("[]", res) },
            { assertEquals("[]", res2) }
        )
    }

    @Test
    @DisplayName("get by id")
    fun findById() = runBlocking {
        coEvery { serviceMock.getPersonalizadoraById(maquina.id) } returns maquina

        val res = runBlocking { controller.getPersonalizadoraById(maquina.id) }
        assertEquals(maquinaJson, res)
    }

    @Test
    @DisplayName("get by id nonexistent")
    fun findByIdNE() = runBlocking {
        coEvery { serviceMock.getPersonalizadoraById(maquina.id) } returns null

        val res = runBlocking { controller.getPersonalizadoraById(maquina.id) }
        assertEquals("Personalizadora with id ${maquina.id} not found.", res)
    }

    @Test
    @DisplayName("insert")
    fun insert() = runBlocking {
        coEvery { serviceMock.createPersonalizadora(maquina) } returns maquina

        val res = runBlocking { controller.insertPersonalizadora(maquina) }
        assertEquals(maquinaJson, res)
    }

    @Test
    @DisplayName("delete correct")
    fun delete() = runBlocking {
        coEvery { serviceMock.deletePersonalizadora(maquina) } returns true

        val res = runBlocking { controller.deletePersonalizadora(maquina) }
        assertEquals(maquinaJson, res)
    }

    @Test
    @DisplayName("delete incorrect")
    fun deleteInc() = runBlocking {
        coEvery { serviceMock.deletePersonalizadora(maquina) } returns false

        val res = runBlocking { controller.deletePersonalizadora(maquina) }
        assertEquals("Could not delete Personalizadora with id ${maquina.id}", res)
    }
}
package controllers

import dto.EncordadoraDTO
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
import services.EncordadoraService
import java.time.LocalDate
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
class EncordadoraControllerMockTest {
    private val serviceMock: EncordadoraService = mockk()
    private var controller = EncordadoraController

    val maquina = EncordadoraDTO(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea83bc"),
        modelo = "ENC-4070Turbo",
        marca = "Genericosas Marca Registrada",
        fechaAdquisicion = LocalDate.parse("2021-10-01"),
        numeroSerie = "975318642Q",
        isManual = true,
        maxTension = 15.2,
        minTension = 5.1
    )

    val maquinaJson = """
        {
          "id": "93a98d69-6da6-48a7-b34f-05b596ea83bc",
          "modelo": "ENC-4070Turbo",
          "marca": "Genericosas Marca Registrada",
          "fechaAdquisicionString": "2021-10-01",
          "numeroSerie": "975318642Q",
          "isManual": true,
          "maxTension": 15.2,
          "minTension": 5.1
        }
    """.trimIndent()

    init {
        MockKAnnotations.init(this)
        controller.service = serviceMock
    }

    @Test
    @DisplayName("get all")
    fun findAll() = runBlocking {
        coEvery { serviceMock.getAllEncordadoras() } returns listOf(maquina)

        val res = runBlocking { controller.findAllEncordadoras() }
        assertEquals("[$maquinaJson]", res)
    }

    @Test
    @DisplayName("get all void")
    fun findAllNE() = runBlocking {
        coEvery { serviceMock.getAllEncordadoras() } returns listOf()

        val res = runBlocking { controller.findAllEncordadoras() }
        assertEquals("[]", res)
    }

    @Test
    @DisplayName("get manuales")
    fun findManuales() = runBlocking {
        coEvery { serviceMock.getAllEncordadoras() } returns listOf(maquina)

        val res = runBlocking { controller.findAllManuales(true) }
        val res2 = runBlocking { controller.findAllManuales(false) }
        assertAll(
            { assertEquals("[$maquinaJson]", res) },
            { assertEquals("[]", res2) }
        )
    }

    @Test
    @DisplayName("get maneuverability void")
    fun findManualesNull() = runBlocking {
        coEvery { serviceMock.getAllEncordadoras() } returns listOf()

        val res = runBlocking { controller.findAllManuales(true) }
        val res2 = runBlocking { controller.findAllManuales(false) }
        assertAll(
            { assertEquals("[]", res) },
            { assertEquals("[]", res2) }
        )
    }

    @Test
    @DisplayName("get by id")
    fun findById() = runBlocking {
        coEvery { serviceMock.getEncordadoraById(maquina.id) } returns maquina

        val res = runBlocking { controller.getEncordadoraById(maquina.id) }
        assertEquals(maquinaJson, res)
    }

    @Test
    @DisplayName("get by id nonexistent")
    fun findByIdNE() = runBlocking {
        coEvery { serviceMock.getEncordadoraById(maquina.id) } returns null

        val res = runBlocking { controller.getEncordadoraById(maquina.id) }
        assertEquals("Encordadora with id ${maquina.id} not found.", res)
    }

    @Test
    @DisplayName("insert")
    fun insert() = runBlocking {
        coEvery { serviceMock.createEncordadora(maquina) } returns maquina

        val res = runBlocking { controller.insertEncordadora(maquina) }
        assertEquals(maquinaJson, res)
    }

    @Test
    @DisplayName("delete correct")
    fun delete() = runBlocking {
        coEvery { serviceMock.deleteEncordadora(maquina) } returns true

        val res = runBlocking { controller.deleteEncordadora(maquina) }
        assertEquals(maquinaJson, res)
    }

    @Test
    @DisplayName("delete incorrect")
    fun deleteInc() = runBlocking {
        coEvery { serviceMock.deleteEncordadora(maquina) } returns false

        val res = runBlocking { controller.deleteEncordadora(maquina) }
        assertEquals("Could not delete Encordadora with id ${maquina.id}", res)
    }
}
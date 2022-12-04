package controllers

import dto.EncordadoraDTO
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
import org.junit.jupiter.api.extension.ExtendWith
import services.EncordadoraService
import services.PersonalizadoraService
import java.time.LocalDate
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
class MaquinaControllerMockTest {
    private val eServiceMock: EncordadoraService = mockk()
    private val pServiceMock: PersonalizadoraService = mockk()
    private var controller = MaquinaController

    val personalizadora = PersonalizadoraDTO(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea83bb"),
        modelo = "RTX-3080TI",
        marca = "Nvidia",
        fechaAdquisicion = LocalDate.parse("2022-11-10"),
        numeroSerie = "123456789X",
        measuresRigidity = false,
        measuresBalance = true,
        measuresManeuverability = true
    )

    val encordadora = EncordadoraDTO(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea83bc"),
        modelo = "ENC-4070Turbo",
        marca = "Genericosas Marca Registrada",
        fechaAdquisicion = LocalDate.parse("2021-10-01"),
        numeroSerie = "975318642Q",
        isManual = true,
        maxTension = 15.2,
        minTension = 5.1
    )

    val encordadoraJson = """
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

    val personalizadoraJson = """
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
        controller.eService = eServiceMock
        controller.pService = pServiceMock
    }

    @Test
    @DisplayName("get all")
    fun findAll() = runBlocking {
        coEvery { eServiceMock.getAllEncordadoras() } returns listOf(encordadora)
        coEvery { pServiceMock.getAllPersonalizadoras() } returns listOf(personalizadora)

        val res = runBlocking { controller.findAllMaquinas() }
        assertEquals("[$personalizadoraJson, $encordadoraJson]", res)
    }

    @Test
    @DisplayName("get all void")
    fun findAllNE() = runBlocking {
        coEvery { eServiceMock.getAllEncordadoras() } returns listOf()
        coEvery { pServiceMock.getAllPersonalizadoras() } returns listOf()

        val res = runBlocking { controller.findAllMaquinas() }
        assertEquals("[]", res)
    }

    @Test
    @DisplayName("get by id nonexistent")
    fun findByIdNE() = runBlocking {
        coEvery { eServiceMock.getEncordadoraById(encordadora.id) } returns null
        coEvery { pServiceMock.getPersonalizadoraById(encordadora.id) } returns null

        val res = runBlocking { controller.getMaquinaById(encordadora.id) }
        assertEquals("Maquina with id ${encordadora.id} not found.", res)
    }

    @Test
    @DisplayName("get all model")
    fun findAllmdl() = runBlocking {
        coEvery { eServiceMock.getAllEncordadoras() } returns listOf(encordadora)
        coEvery { pServiceMock.getAllPersonalizadoras() } returns listOf(personalizadora)

        val res = runBlocking { controller.getMaquinaByModel(encordadora.modelo) }
        assertEquals("[$encordadoraJson]", res)
    }

    @Test
    @DisplayName("get all model void")
    fun findAllmdlNE() = runBlocking {
        coEvery { eServiceMock.getAllEncordadoras() } returns listOf()
        coEvery { pServiceMock.getAllPersonalizadoras() } returns listOf()

        val res = runBlocking { controller.getMaquinaByModel(encordadora.modelo) }
        assertEquals("[]", res)
    }

    @Test
    @DisplayName("get all brand")
    fun findAllbrand() = runBlocking {
        coEvery { eServiceMock.getAllEncordadoras() } returns listOf(encordadora)
        coEvery { pServiceMock.getAllPersonalizadoras() } returns listOf(personalizadora)

        val res = runBlocking { controller.getMaquinaByBrand(personalizadora.marca) }
        assertEquals("[$personalizadoraJson]", res)
    }

    @Test
    @DisplayName("get all brand void")
    fun findAllbrandNE() = runBlocking {
        coEvery { eServiceMock.getAllEncordadoras() } returns listOf()
        coEvery { pServiceMock.getAllPersonalizadoras() } returns listOf()

        val res = runBlocking { controller.getMaquinaByBrand(personalizadora.marca) }
        assertEquals("[]", res)
    }

    @Test
    @DisplayName("get all serial number")
    fun findAllSN() = runBlocking {
        coEvery { eServiceMock.getAllEncordadoras() } returns listOf(encordadora)
        coEvery { pServiceMock.getAllPersonalizadoras() } returns listOf(personalizadora)

        val res = runBlocking { controller.getMaquinaBySerialNumber(personalizadora.numeroSerie) }
        assertEquals(personalizadoraJson, res)
    }

    @Test
    @DisplayName("get all serial num void")
    fun findAllSNNE() = runBlocking {
        coEvery { eServiceMock.getAllEncordadoras() } returns listOf()
        coEvery { pServiceMock.getAllPersonalizadoras() } returns listOf()

        val res = runBlocking { controller.getMaquinaBySerialNumber(personalizadora.numeroSerie) }
        assertEquals("Maquina with serial number ${personalizadora.numeroSerie} not found.", res)
    }
}
package repositories

import config.ApplicationProperties
import db.HibernateManager
import kotlinx.coroutines.runBlocking
import models.Personalizadora
import org.junit.jupiter.api.*
import java.time.LocalDate
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonalizadoraRepositoryTest {
    private val repository = PersonalizadoraRepository()

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

    companion object {
        @JvmStatic
        @BeforeAll
        fun initialize() {
            val properties = ApplicationProperties()
            println("Reading properties file ${properties.readProperty("app.title")}")
            HibernateManager.open()
            HibernateManager.close()
        }
    }

    @BeforeEach
    fun setUp() = runBlocking {
        repository.create(maquina)
        println("set up completed.")
    }

    @AfterEach
    fun tearDown() = runBlocking {
        repository.delete(maquina)
        println("teared down successfully.")
    }

    @Test
    @DisplayName("find by id")
    fun findById() = runBlocking {
        val res = repository.findById(maquina.id)
        Assertions.assertEquals(maquina.id, res?.id)
    }

    @Test
    @DisplayName("find all")
    fun findAll() = runBlocking {
        val list = repository.readAll()
        val res = list.find { it.id == maquina.id }
        Assertions.assertEquals(maquina.id, res?.id)
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
        val res = repository.create(maquina)
        Assertions.assertEquals(maquina.id, res.id)
    }

    @Test
    @DisplayName("delete")
    fun delete() = runBlocking {
        val res1 = repository.delete(maquina)
        val res2 = repository.findById(maquina.id)
        assertAll( {
            Assertions.assertNull(res2)
            Assertions.assertTrue(res1)
        } )
    }
}
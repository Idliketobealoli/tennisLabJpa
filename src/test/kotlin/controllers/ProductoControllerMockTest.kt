package controllers

import dto.ProductoDTO
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import models.enums.TipoProducto
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import services.ProductoService
import java.util.*

/**
 * @author Ivan Azagra Troya
 *
 * Clase de testeo unitario de ProductoController con MockK.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
class ProductoControllerMockTest {
    private val serviceMock: ProductoService = mockk()
    private var controller = ProductoController

    val producto = ProductoDTO(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea83aa"),
        tipoProducto = TipoProducto.FUNDAS,
        marca = "MarcaZ",
        modelo = "ModeloZ",
        precio = 36.4,
        stock = 8
    )

    val productoJson = """
        {
          "id": "93a98d69-6da6-48a7-b34f-05b596ea83aa",
          "tipoProducto": "FUNDAS",
          "marca": "MarcaZ",
          "modelo": "ModeloZ",
          "precio": 36.4,
          "stock": 8
        }
    """.trimIndent()

    init {
        MockKAnnotations.init(this)
        controller.service = serviceMock
    }

    @Test
    @DisplayName("get all")
    fun findAll() = runBlocking {
        coEvery { serviceMock.getAllProductos() } returns listOf(producto)

        val res = runBlocking { controller.findAllProductos() }
        assertEquals("[$productoJson]", res)
    }

    @Test
    @DisplayName("get all void")
    fun findAllNE() = runBlocking {
        coEvery { serviceMock.getAllProductos() } returns listOf()

        val res = runBlocking { controller.findAllProductos() }
        assertEquals("[]", res)
    }

    @Test
    @DisplayName("get disponibles")
    fun findDisponibles() = runBlocking {
        coEvery { serviceMock.getAllProductos() } returns listOf(producto)

        val res = runBlocking { controller.findAllProductosDisponibles() }
        assertEquals("[$productoJson]", res)
    }

    @Test
    @DisplayName("get disponibles void")
    fun findDisponoblesNull() = runBlocking {
        coEvery { serviceMock.getAllProductos() } returns listOf()

        val res = runBlocking { controller.findAllProductosDisponibles() }
        assertEquals("[]", res)
    }

    @Test
    @DisplayName("get by id")
    fun findById() = runBlocking {
        coEvery { serviceMock.getProductoById(producto.id) } returns producto

        val res = runBlocking { controller.getProductoById(producto.id) }
        assertEquals(productoJson, res)
    }

    @Test
    @DisplayName("get by id nonexistent")
    fun findByIdNE() = runBlocking {
        coEvery { serviceMock.getProductoById(producto.id) } returns null

        val res = runBlocking { controller.getProductoById(producto.id) }
        assertEquals("Producto with id ${producto.id} not found.", res)
    }

    @Test
    @DisplayName("insert")
    fun insert() = runBlocking {
        coEvery { serviceMock.createProducto(producto) } returns producto

        val res = runBlocking { controller.insertProducto(producto) }
        assertEquals(productoJson, res)
    }

    @Test
    @DisplayName("delete correct")
    fun delete() = runBlocking {
        coEvery { serviceMock.deleteProducto(producto) } returns true

        val res = runBlocking { controller.deleteProducto(producto) }
        assertEquals(productoJson, res)
    }

    @Test
    @DisplayName("delete incorrect")
    fun deleteInc() = runBlocking {
        coEvery { serviceMock.deleteProducto(producto) } returns false

        val res = runBlocking { controller.deleteProducto(producto) }
        assertEquals("Could not delete Producto with id ${producto.id}", res)
    }
}
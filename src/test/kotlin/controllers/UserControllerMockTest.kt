package controllers

import dto.UserDTO
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.*
import models.enums.Profile
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import services.UserService
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
class UserControllerMockTest {
    private val serviceMock: UserService = mockk()
    private var controller = UserController

    val user = UserDTO(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea839c"),
        nombre = "Luis",
        apellido = "Martinez",
        telefono = "632950281",
        email = "email@email.com",
        password = "estacontraseñanoestaensha512",
        perfil = Profile.WORKER
    )
    val userJson = """
        {
          "id": "93a98d69-6da6-48a7-b34f-05b596ea839c",
          "nombre": "Luis",
          "apellido": "Martinez",
          "email": "email@email.com",
          "perfil": "WORKER"
        }
    """.trimIndent()

    init {
        MockKAnnotations.init(this)
        controller.service = serviceMock
    }

    @Test
    @DisplayName("get all")
    fun findAll() = runBlocking {
        coEvery { serviceMock.getAllUsers() } returns listOf(user)

        val res = runBlocking { controller.findAllUsers() }
        assertEquals("[$userJson]", res)
    }

    @Test
    @DisplayName("get all void")
    fun findAllNE() = runBlocking {
        coEvery { serviceMock.getAllUsers() } returns listOf()

        val res = runBlocking { controller.findAllUsers() }
        assertEquals("[]", res)
    }

    @Test
    @DisplayName("get by role")
    fun findByRole() = runBlocking {
        coEvery { serviceMock.getAllUsersByPerfil(user.perfil) } returns listOf(user)

        val res = runBlocking { controller.findAllUsersWithRole(user.perfil) }
        assertEquals("[$userJson]", res)
    }

    @Test
    @DisplayName("get by role nonexistent")
    fun findByRoleNE() = runBlocking {
        coEvery { serviceMock.getAllUsersByPerfil(user.perfil) } returns listOf()

        val res = runBlocking { controller.findAllUsersWithRole(user.perfil) }
        assertEquals("[]", res)
    }

    @Test
    @DisplayName("get by id")
    fun findById() = runBlocking {
        coEvery { serviceMock.getUserById(user.id) } returns user

        val res = runBlocking { controller.getUserById(user.id) }
        assertEquals(userJson, res)
    }

    @Test
    @DisplayName("get by id nonexistent")
    fun findByIdNE() = runBlocking {
        coEvery { serviceMock.getUserById(user.id) } returns null

        val res = runBlocking { controller.getUserById(user.id) }
        assertEquals("User with id ${user.id} not found.", res)
    }

    @Test
    @DisplayName("get by email")
    fun findByEmail() = runBlocking {
        coEvery { serviceMock.getUserByMail(user.email) } returns user

        val res = runBlocking { controller.getUserByEmail(user.email) }
        assertEquals(userJson, res)
    }

    @Test
    @DisplayName("get by email nonexistent")
    fun findByEmailNE() = runBlocking {
        coEvery { serviceMock.getUserByMail(user.email) } returns null

        val res = runBlocking { controller.getUserByEmail(user.email) }
        assertEquals("User with email ${user.email} not found.", res)
    }


    @Test
    @DisplayName("get by email for login")
    fun findByEmail4l() = runBlocking {
        coEvery { serviceMock.getUserByMail(user.email) } returns user

        val res = runBlocking { controller.getUserByEmailForLogin(user.email) }
        assertEquals(user, res)
    }

    @Test
    @DisplayName("get by email nonexistent for login")
    fun findByEmailNE4l() = runBlocking {
        coEvery { serviceMock.getUserByMail(user.email) } returns null

        val res = runBlocking { controller.getUserByEmailForLogin(user.email) }
        assertNull(res)
    }

    @Test
    @DisplayName("get by phone")
    fun findByPhone() = runBlocking {
        coEvery { serviceMock.getUserByPhone(user.telefono) } returns user

        val res = runBlocking { controller.getUserByPhone(user.telefono) }
        assertEquals(userJson, res)
    }

    @Test
    @DisplayName("get by phone nonexistent")
    fun findByPhoneNE() = runBlocking {
        coEvery { serviceMock.getUserByPhone(user.telefono) } returns null

        val res = runBlocking { controller.getUserByPhone(user.telefono) }
        assertEquals("User with phone ${user.telefono} not found.", res)
    }

    @Test
    @DisplayName("get by phone for login")
    fun findByPhone4l() = runBlocking {
        coEvery { serviceMock.getUserByPhone(user.telefono) } returns user

        val res = runBlocking { controller.getUserByPhoneForLogin(user.telefono) }
        assertEquals(user, res)
    }

    @Test
    @DisplayName("get by phone nonexistent for login")
    fun findByPhoneNE4l() = runBlocking {
        coEvery { serviceMock.getUserByPhone(user.telefono) } returns null

        val res = runBlocking { controller.getUserByPhoneForLogin(user.telefono) }
        assertNull(res)
    }

    @Test
    @DisplayName("insert")
    fun insert() = runBlocking {
        coEvery { serviceMock.createUser(user) } returns user

        val res = runBlocking { controller.insertUser(user) }
        assertEquals(userJson, res)
    }

    @Test
    @DisplayName("delete correct")
    fun delete() = runBlocking {
        coEvery { serviceMock.deleteUser(user) } returns true

        val res = runBlocking { controller.deleteUser(user) }
        assertEquals(userJson, res)
    }

    @Test
    @DisplayName("delete incorrect")
    fun deleteInc() = runBlocking {
        coEvery { serviceMock.deleteUser(user) } returns false

        val res = runBlocking { controller.deleteUser(user) }
        assertEquals("Could not delete User with id ${user.id}", res)
    }
}
import config.ApplicationProperties
import controllers.*
import db.DataLoader
import db.HibernateManager
import kotlinx.coroutines.*
import login.login
import login.register
import menu.menu
import mu.KotlinLogging
import repositories.PedidoRepository

val logger = KotlinLogging.logger {  }

/**
 * @author Daniel Rodriguez Muñoz
 *
 * La funcion principal del programa.
 */
fun main(args: Array<String>) {
    runBlocking {
        initDB()

        val dataLoader = DataLoader()

        println("Cargando datos iniciales...")
        dataLoader.getUsers().forEach { UserController.insertUser(it) }
        println("Users loaded.")

        val pedRepo = PedidoRepository()
        dataLoader.pedidos().forEach { pedRepo.create(it) }

        dataLoader.getMaquinas().forEach { MaquinaController.insertMaquina(it) }
        println("Maquinas loaded.")

        dataLoader.getProductos().forEach { ProductoController.insertProducto(it) }
        println("Productos loaded.")

        dataLoader.getTareas().forEach { TareaController.insertTarea(it) }
        println("Tareas loaded.")
        dataLoader.getTurnos().forEach { TurnoController.insertTurno(it) }
        println(TurnoController.findAllTurnos())
        println("Turnos loaded.")
        dataLoader.getPedidos().forEach { PedidoController.insertPedido(it) }
        println("Pedidos loaded.")

        println("Data successfully loaded.")
        println("USERS: ${UserController.findAllUsers()}")
        println("MAQUINAS: ${MaquinaController.findAllMaquinas()}")
        println("PRODUCTOS: ${ProductoController.findAllProductos()}")
        println("TAREAS: ${TareaController.findAllTareas()}")
        println("TURNOS: ${TurnoController.findAllTurnos()}")
        println("PEDIDOS: ${PedidoController.findAllPedidos()}")

        var loginEnter = ""
        println(" - Welcome. Do you want to log in or register? [login/register]")
        while (!loginEnter.contentEquals("login") && ! loginEnter.contentEquals("register")) {
            loginEnter = readln()
        }
        val user = if (loginEnter.contentEquals("login")) async(Dispatchers.Default) { login() } else async(Dispatchers.Default) { register() }

        launch { menu(user.await()) }
    }
}

/**
 * @author Ivan Azagra Troya
 *
 * Funcion de inicializacion de la Base de Datos
 */
fun initDB() {
    val properties = ApplicationProperties()
    logger.debug { "Reading properties file ${properties.readProperty("app.title")}" }
    HibernateManager.open()
    HibernateManager.close()
}
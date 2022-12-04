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

        /*
        println("////////////////////// TODAS ///////////////////////")
        println(MaquinaController.findAllMaquinas())
        println("//////////////// PERSONALIZADORAS //////////////////")
        println(PersonalizadoraController.findAllPersonalizadoras())
        println("////////////////// ENCORDADORAS ////////////////////")
        println(EncordadoraController.findAllEncordadoras())
        println("////////////////// FIN ////////////////////")
        */

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

fun initDB() {
    val properties = ApplicationProperties()
    logger.debug { "Reading properties file ${properties.readProperty("app.title")}" }
    HibernateManager.open()
    HibernateManager.close()
}
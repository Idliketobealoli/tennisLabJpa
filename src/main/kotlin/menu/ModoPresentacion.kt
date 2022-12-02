package menu

import controllers.*
import dto.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitAll
import mappers.*
import models.enums.PedidoEstado
import models.enums.Profile
import models.enums.TipoProducto
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync
import util.waitingText
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

suspend fun modoPresentacion() {
    val pMapper = ProductoMapper()
    val uMapper = UserMapper()
    val mMapper = MaquinaMapper()
    val tMapper = TareaMapper()
    val turMapper = TurnoMapper()

    val newUser = UserDTO(
        id = UUID.fromString(
            "ad000001-0000-0000-0000-000000000001"),
        nombre = "loli",
        apellido = "loli",
        telefono = "170717079",
        email = "loli@uwu.com",
        password = "loli",
        perfil = Profile.ADMIN
    )
    val newMaquina = EncordadoraDTO(
        id = UUID.fromString(
            "ad000001-0000-0000-0000-000000000002"),
        modelo = "nueva maquina",
        marca = "nueva maquina xd",
        fechaAdquisicion = LocalDate.now(),
        numeroSerie = "111111111",
        isManual = true,
        maxTension = 15.2,
        minTension = 5.1
    )
    val newProducto = ProductoDTO(
        id = UUID.fromString(
            "ad000001-0000-0000-0000-000000000003"),
        tipoProducto = TipoProducto.RAQUETAS,
        marca = "MarcaRaqueta",
        modelo = "ModeloRaqueta",
        precio = 150.5,
        stock = 3
    )
    val newTarea = EncordadoDTO(
        id = UUID.fromString(
            "ad000001-0000-0000-0000-000000000004"),
        raqueta = pMapper.fromDTO(newProducto),
        user = uMapper.fromDTO(newUser),
        tensionHorizontal = 0.5,
        cordajeHorizontal = pMapper.fromDTO(newProducto),
        tensionVertical = 1.1,
        cordajeVertical = pMapper.fromDTO(newProducto),
        dosNudos = false
    )
    val newTurno = TurnoDTO(
        id = UUID.fromString(
            "ad000001-0000-0000-0000-000000000005"),
        worker = uMapper.fromDTO(newUser),
        maquina = mMapper.fromDTO(newMaquina),
        horaInicio = LocalDateTime.now(),
        horaFin = null,
        tarea1 = tMapper.fromDTO(newTarea),
        tarea2 = null
    )
    val newPedido = PedidoDTO(
        id = UUID.fromString(
            "ad000001-0000-0000-0000-000000000006"),
        tareas = tMapper.fromDTO(listOf(newTarea)),
        client = uMapper.fromDTO(newUser),
        turnos = turMapper.fromDTO(listOf(newTurno)),
        state = PedidoEstado.TERMINADO,
        fechaEntrada = null,
        fechaProgramada = newTurno.horaFin.toLocalDate(),
        fechaSalida = newTurno.horaFin.plusDays(5L).toLocalDate() ?: LocalDate.now(),
        fechaEntrega = null
    )

    println("*** INSERTS ***")
    val inUser = suspendedTransactionAsync(Dispatchers.IO) { UserController.insertUser(newUser) }
    val inMaquina = suspendedTransactionAsync(Dispatchers.IO) { MaquinaController.insertMaquina(newMaquina) }
    val inProducto = suspendedTransactionAsync(Dispatchers.IO) { ProductoController.insertProducto(newProducto) }
    awaitAll(inUser,inMaquina,inProducto)
    println(inUser)
    println(inMaquina)
    println(inProducto)
    println(suspendedTransactionAsync(Dispatchers.IO) { TareaController.insertTarea(newTarea) }.await())
    println(suspendedTransactionAsync(Dispatchers.IO) { TurnoController.insertTurno(newTurno) }.await())
    println(suspendedTransactionAsync(Dispatchers.IO) { PedidoController.insertPedido(newPedido) }.await())


    println("\n\n\nFIND ALL USERS")
    val users = suspendedTransactionAsync(Dispatchers.IO) { UserController.findAllUsers() }
    waitingText(users)
    println(users.await())

    println("\n\n\nFIND ALL MAQUINAS")
    val maquinas = suspendedTransactionAsync(Dispatchers.IO) { MaquinaController.findAllMaquinas() }
    waitingText(maquinas)
    println(maquinas.await())

    println("\n\n\nFIND ALL PRODUCTOS")
    val productos = suspendedTransactionAsync(Dispatchers.IO) { ProductoController.findAllProductos() }
    waitingText(productos)
    println(productos.await())

    println("\n\n\nFIND ALL TAREAS")
    val tareas = suspendedTransactionAsync(Dispatchers.IO) { TareaController.findAllTareas() }
    waitingText(tareas)
    println(tareas.await())

    println("\n\n\nFIND ALL TURNOS")
    val turnos = suspendedTransactionAsync(Dispatchers.IO) { TurnoController.findAllTurnos() }
    waitingText(turnos)
    println(turnos.await())

    println("\n\n\nFIND ALL PEDIDOS")
    val pedidos = suspendedTransactionAsync(Dispatchers.IO) { PedidoController.findAllPedidos() }
    waitingText(pedidos)
    println(pedidos.await())

    println("\n\n\n*** GET BY ID Y OTROS CAMPOS ***")
    val fbid1 = suspendedTransactionAsync(Dispatchers.IO) { UserController.getUserById(
        UUID.fromString("ad000001-0000-0000-0000-000000000001")) }
    val fbid2 = suspendedTransactionAsync(Dispatchers.IO) { MaquinaController.getMaquinaById(
        UUID.fromString("ad000001-0000-0000-0000-000000000002")) }
    val fbid3 = suspendedTransactionAsync(Dispatchers.IO) { ProductoController.getProductoById(
        UUID.fromString("ad000001-0000-0000-0000-000000000003")) }
    val fbid4 = suspendedTransactionAsync(Dispatchers.IO) { TareaController.getTareaById(
        UUID.fromString("ad000001-0000-0000-0000-000000000004")) }
    val fbid5 = suspendedTransactionAsync(Dispatchers.IO) { TurnoController.getTurnoById(
        UUID.fromString("ad000001-0000-0000-0000-000000000005")) }
    val fbid6 = suspendedTransactionAsync(Dispatchers.IO) { PedidoController.getPedidoById(
        UUID.fromString("ad000001-0000-0000-0000-000000000006")) }
    val fbid7 = suspendedTransactionAsync(Dispatchers.IO) { UserController.getUserByEmail(
        "loli@uwu.com") }
    val fbid8 = suspendedTransactionAsync(Dispatchers.IO) { UserController.getUserByPhone(
        "170717079") }

    val fbids = listOf(fbid1, fbid2, fbid3, fbid4, fbid5, fbid6, fbid7, fbid8)
    fbids.forEach { println(it.await()) }



    val newUser2 = UserDTO(
        id = UUID.fromString(
            "ad000001-0000-0000-0000-000000000001"),
        nombre = "cambiado",
        apellido = "cambiado",
        telefono = "170717079",
        email = "loli@uwu.com",
        password = "loli",
        perfil = Profile.ADMIN
    )
    val newMaquina2 = EncordadoraDTO(
        id = UUID.fromString(
            "ad000001-0000-0000-0000-000000000002"),
        modelo = "nueva maquina cambiada",
        marca = "nueva maquina xd cambiada",
        fechaAdquisicion = LocalDate.now(),
        numeroSerie = "111111111",
        isManual = true,
        maxTension = 15.2,
        minTension = 5.1
    )
    val newProducto2 = ProductoDTO(
        id = UUID.fromString(
            "ad000001-0000-0000-0000-000000000003"),
        tipoProducto = TipoProducto.RAQUETAS,
        marca = "MarcaRaqueta cambiada",
        modelo = "ModeloRaqueta cambiada",
        precio = 999.5,
        stock = 3
    )
    val newTarea2 = EncordadoDTO(
        id = UUID.fromString(
            "ad000001-0000-0000-0000-000000000004"),
        raqueta = pMapper.fromDTO(newProducto2),
        user = uMapper.fromDTO(newUser2),
        tensionHorizontal = 10.5,
        cordajeHorizontal = pMapper.fromDTO(newProducto2),
        tensionVertical = 21.1,
        cordajeVertical = pMapper.fromDTO(newProducto2),
        dosNudos = true
    )
    val newTurno2 = TurnoDTO(
        id = UUID.fromString(
            "ad000001-0000-0000-0000-000000000005"),
        worker = uMapper.fromDTO(newUser2),
        maquina = mMapper.fromDTO(newMaquina2),
        horaInicio = LocalDateTime.now(),
        horaFin = LocalDateTime.now().plusHours(10L),
        tarea1 = tMapper.fromDTO(newTarea2),
        tarea2 = null
    )
    val newPedido2 = PedidoDTO(
        id = UUID.fromString(
            "ad000001-0000-0000-0000-000000000006"),
        tareas = tMapper.fromDTO(listOf(newTarea2)),
        client = uMapper.fromDTO(newUser2),
        turnos = turMapper.fromDTO(listOf(newTurno2)),
        state = PedidoEstado.RECIBIDO,
        fechaEntrada = null,
        fechaProgramada = newTurno2.horaFin.toLocalDate(),
        fechaSalida = newTurno2.horaFin.plusDays(10L).toLocalDate() ?: LocalDate.now(),
        fechaEntrega = newTurno2.horaFin.plusDays(15L).toLocalDate()
    )

    println("*** UPDATES ***")
    val inUser2 = suspendedTransactionAsync(Dispatchers.IO) { UserController.insertUser(newUser2) }
    val inMaquina2 = suspendedTransactionAsync(Dispatchers.IO) { MaquinaController.insertMaquina(newMaquina2) }
    val inProducto2 = suspendedTransactionAsync(Dispatchers.IO) { ProductoController.insertProducto(newProducto2) }
    awaitAll(inUser2,inMaquina2,inProducto2)
    println(inUser2)
    println(inMaquina2)
    println(inProducto2)
    println(suspendedTransactionAsync(Dispatchers.IO) { TareaController.insertTarea(newTarea2) }.await())
    println(suspendedTransactionAsync(Dispatchers.IO) { TurnoController.insertTurno(newTurno2) }.await())
    println(suspendedTransactionAsync(Dispatchers.IO) { PedidoController.insertPedido(newPedido2) }.await())

    println("*** FIND ALL DE TODO OTRA VEZ PARA OBSERVAR COMO EFECTIVAMENTE SE HAN SOBREESCRITO LOS CAMPOS ***")
    val users2 = suspendedTransactionAsync(Dispatchers.IO) { UserController.findAllUsers() }
    waitingText(users2)
    println(users2.await())
    val maquinas2 = suspendedTransactionAsync(Dispatchers.IO) { MaquinaController.findAllMaquinas() }
    waitingText(maquinas2)
    println(maquinas2.await())
    val productos2 = suspendedTransactionAsync(Dispatchers.IO) { ProductoController.findAllProductos() }
    waitingText(productos2)
    println(productos2.await())
    val tareas2 = suspendedTransactionAsync(Dispatchers.IO) { TareaController.findAllTareas() }
    waitingText(tareas2)
    println(tareas2.await())
    val turnos2 = suspendedTransactionAsync(Dispatchers.IO) { TurnoController.findAllTurnos() }
    waitingText(turnos2)
    println(turnos2.await())
    val pedidos2 = suspendedTransactionAsync(Dispatchers.IO) { PedidoController.findAllPedidos() }
    waitingText(pedidos2)
    println(pedidos2.await())

    println("*** DELETES ***")
    println(PedidoController.deletePedido(newPedido2))
    println(TurnoController.deleteTurno(newTurno2))
    println(TareaController.deleteTarea(newTarea2))
}